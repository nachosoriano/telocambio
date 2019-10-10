/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.dao;

import com.querydsl.core.QueryModifiers;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.sql.SQLExpressions;
import com.querydsl.sql.SQLQuery;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author nacho
 * @param <T>
 * @param <ID>
 */
public class JdbcCrudRepository<T extends Object, ID extends Serializable> implements IJdbcCrudRepository<T, ID> {

    protected NamedParameterJdbcTemplate jdbcTemplate;

    protected JdbcRowMapper<T, ID> rowMapper;
    @Autowired
    IJdbcRepositorySQLHelper helper;

    String insertSQL;
    String updateSQL;
    String findOneSQL;
    String existsSQL;
    String findAllIterableSQL;
    String deleteOneSQL;
    String deleteManySQL;

    @Autowired
    protected void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Autowired
    protected void setRowMapper(JdbcRowMapper<T, ID> rowMapper) {
        this.rowMapper = rowMapper;
        reloadSQL();
    }

    protected void reloadSQL() {
        String tableName = rowMapper.getTableName();

        JdbcRowMapper.FieldsEnum[] fields = rowMapper.getFields();
        insertSQL = "insert into " + tableName + " (" + helper.generatefieldList(fields) + ") values (" + helper.generateInsertValues(tableName, fields) + ")";
        updateSQL = "update " + tableName + " set " + helper.generateValuesUpdateAll(fields) + " WHERE " + helper.generateWhereByPrimaryKey(fields);
        findOneSQL = "select " + helper.generatefieldList(fields) + " FROM " + tableName + " WHERE " + helper.generateWhereByPrimaryKey(fields);
        existsSQL = "select count(*) FROM " + tableName + " WHERE " + helper.generateWhereByPrimaryKey(fields);
        //FIXME Esta query debería ser genérica
        findAllIterableSQL = "select * from " + tableName + " WHERE id in (:IDS)";
        deleteOneSQL = "delete from " + tableName + " where " + helper.generateWhereByPrimaryKey(fields);
        //FIXME Esta query debería ser genérica
        deleteManySQL = "delete from " + tableName + " where id in (:IDS)";
    }

    @Override
    public <S extends T> S save(final S entity) {
        S test;
        if (rowMapper.isANewObject(entity)) {
            test = (S) insert(entity);
        } else {
            test = (S) update(entity);
        }
        return test;
    }

    public T insert(final T entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(insertSQL, rowMapper.getParamSource(entity), keyHolder, rowMapper.getIDFields());
        if (rowMapper.getID(entity) == null) {
            rowMapper.populateKey(entity, keyHolder);
        }
        return entity;
    }

    public int[] insert(final Iterable<T> entities) {
        return batchExecute(insertSQL, entities);
    }

    protected int[] batchExecute(final String sql, final Iterable<T> entities) {
        final List<SqlParameterSource> lParameters = new ArrayList<>();
        final Iterator<T> iterator = entities.iterator();
        while (iterator.hasNext()) {
            T item = iterator.next();
            lParameters.add(rowMapper.getParamSource(item));
        }
        int[] rows = jdbcTemplate.batchUpdate(sql, lParameters.toArray(new SqlParameterSource[lParameters.size()]));
        return rows;
    }

    public T update(final T entity) {
        int rows = jdbcTemplate.update(updateSQL, rowMapper.getParamSource(entity));
        if (rows == 0) {
            return null;
        }
        return entity;
    }

    public int[] update(final Iterable<T> entities) {
        return batchExecute(updateSQL, entities);
    }

    @Override
    public <S extends T> Iterable<S> saveAll(final Iterable<S> entities) {
        final List<T> items = new ArrayList<>();
        final Iterator<S> iterator = entities.iterator();
        while (iterator.hasNext()) {
            T item = save(iterator.next());
            items.add(item);
        }
        return (Iterable<S>) items;
    }

    @Override
    public Optional<T> findById(final ID id) {
        SqlParameterSource paramSource = rowMapper.getParamSourceByID(id);
        T result = null;
        try {
            T value = jdbcTemplate.queryForObject(findOneSQL, paramSource, rowMapper);
            if (value != null) {
                result = value;
            }
        } catch (EmptyResultDataAccessException ex) {
            // noop
        }
        return Optional.ofNullable(result);
    }

    @Override
    public boolean existsById(final ID id) {
        SqlParameterSource paramSource = rowMapper.getParamSourceByID(id);
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(existsSQL, paramSource);
        rowSet.next();
        return (rowSet.getInt(1) == 1);
    }

    @Override
    public Iterable<T> findAll() {
        SQLQuery query = helper.getQuery();
        query.from(Expressions.stringPath(rowMapper.getTableName()));
        query.select(SQLExpressions.all);
        List<T> result = jdbcTemplate.query(query.getSQL().getSQL(), rowMapper);
        return result;
    }

    protected <A> List<List<A>> splitList(final List<A> orig) {
        final int MAX_ITEMS = 500;
        final List<List<A>> splitted = new ArrayList<>();
        int iters = (int) Math.ceil((double) orig.size() / MAX_ITEMS);
        for (int i = 0; i < iters; ++i) {
            splitted.add(orig.subList(i * MAX_ITEMS, Math.min((i + 1) * MAX_ITEMS, orig.size())));
        }
        return splitted;
    }

    @Override
    public Iterable<T> findAllById(final Iterable<ID> ids) {
        final List<T> result = new ArrayList<>();
        if (ids.iterator().hasNext()) {
            // NOTE este método sólo permite ( por ahora ) identificadores simples
            final List<ID> listIds = new ArrayList<>();
            ids.forEach(listIds::add);
            final List<List<ID>> splittedIds = splitList(listIds);
            splittedIds
                    .stream()
                    .map((items) -> new MapSqlParameterSource("IDS", items))
                    .map((paramSource) -> jdbcTemplate.query(findAllIterableSQL, paramSource, rowMapper))
                    .forEachOrdered((partialResult) -> {
                        result.addAll(partialResult);
                    });
        }
        return result;
    }

    private long count(String query) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query, new HashMap<>());
        if (rowSet.next()) {
            return rowSet.getLong(1);
        } else {
            return 0;
        }
    }

    @Override
    public long count() {
        SQLQuery query = helper.getQuery();
        query.select(SQLExpressions.countAll);
        query.from(Expressions.stringPath(rowMapper.getTableName()));
        return count(query.getSQL().getSQL());
    }

    @Override
    public void deleteById(final ID id) {
        SqlParameterSource paramSource = rowMapper.getParamSourceByID(id);
        jdbcTemplate.update(deleteOneSQL, paramSource);
    }

    @Override
    public void delete(final T entity) {
        ID id = rowMapper.getID(entity);
        deleteById(id);
    }

    @Override
    public void deleteAll(final Iterable<? extends T> entities) {
        if (entities.iterator().hasNext()) {
            final List<ID> idsToDelete = new ArrayList<>();
            final Iterator iterator = entities.iterator();
            if (rowMapper.getIDFields().length == 1) {
                // Si tiene clave primaria compuesta no puede ejecutarse la misma query
                while (iterator.hasNext()) {
                    ID id = rowMapper.getID((T) iterator.next());
                    idsToDelete.add(id);
                }
                SqlParameterSource paramSource = new MapSqlParameterSource("IDS", idsToDelete);
                jdbcTemplate.update(deleteManySQL, paramSource);
            } else {
                while (iterator.hasNext()) {
                    delete((T) iterator.next());
                }
            }
        }
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("You don't really want this...");
    }

    @Override
    public NamedParameterJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    protected String generateSimpleInsertSQL(final String tablename, final JdbcRowMapper.FieldsEnum fields[]) {

        String tableFields = helper.generatefieldList(fields);
        String tableValueFields = helper.generatefieldValueList(fields);

        String sql = MessageFormat.format("insert into {0} ({1}) values ({2})", tablename, tableFields, tableValueFields);
        return sql;
    }

    @Override
    public Iterable<T> findAll(final Sort sort) {
        SQLQuery query = helper.getQuery();
        query.select(SQLExpressions.all);
        query.from(Expressions.stringPath(rowMapper.getTableName()));
        setOrder(query, sort);
        return jdbcTemplate.query(query.getSQL().getSQL(), this.rowMapper);
    }

    @Override
    public Page<T> findAll(final Pageable pageInfo) {
        SQLQuery query = helper.getQuery();
        query.select(SQLExpressions.countAll);
        query.from(Expressions.stringPath(rowMapper.getTableName()));
        long count = count(query.getSQL().getSQL());
        query.select(SQLExpressions.all);
        setPagination(query, pageInfo);
        Sort appliedSort = setOrder(query, pageInfo.getSort());
        Pageable effectivePageInfo = PageRequest.of(pageInfo.getPageNumber(), pageInfo.getPageSize(), appliedSort);
        Page<T> result = new PageImpl<>(jdbcTemplate.query(query.getSQL().getSQL(), this.rowMapper), effectivePageInfo, (int) count);
        return result;
    }

    public Page<T> filter(final Predicate predicate, final Pageable pageInfo) {
        return filter(predicate, pageInfo, rowMapper);
    }

    public <A> Page<A> filter(final Predicate predicate, final Pageable pageInfo, final JdbcRowMapper specificRowMapper) {
        return filter(Arrays.asList(SQLExpressions.all), predicate, pageInfo, specificRowMapper);
    }

    public <A> Page<A> filter(final Collection<Expression> selectExpressions, final Predicate predicate, final Pageable pageInfo, final JdbcRowMapper specificRowMapper) {
        SQLQuery query = helper.getQuery();
        query.from(Expressions.stringPath(specificRowMapper.getTableName()));
        query.where(predicate);

        query.select(SQLExpressions.countAll);
        long totalCount = count(query.getSQL().getSQL());

        query.select((Expression[]) selectExpressions.toArray(new Expression[1]));
        Pageable effectivePageInfo = null;
        if (pageInfo != null) {
            setPagination(query, pageInfo);
            Sort appliedSort = setOrder(query, pageInfo.getSort(), specificRowMapper);
            effectivePageInfo = PageRequest.of(pageInfo.getPageNumber(), pageInfo.getPageSize(), appliedSort);
        } else {
            setOrder(query, null, specificRowMapper);
        }
        Page<A> result = new PageImpl<>(jdbcTemplate.query(query.getSQL().getSQL(), specificRowMapper), effectivePageInfo, totalCount);
        return result;
    }

    public Collection<T> filterAll(final Predicate predicate, final Sort sort) {
        SQLQuery query = helper.getQuery();
        query.select(SQLExpressions.all);
        query.from(Expressions.stringPath(rowMapper.getTableName()));
        if (predicate != null) {
            query.where(predicate);
        }
        setOrder(query, sort);
        Collection<T> result = jdbcTemplate.query(query.getSQL().getSQL(), rowMapper);
        return result;
    }

    private Sort setOrder(final SQLQuery query, Sort sort) {
        return setOrder(query, sort, rowMapper);
    }

    private Sort setOrder(final SQLQuery query, Sort sort, JdbcRowMapper mapper) {
        Collection<String> fieldNames = mapper.getFieldsNames();
        Collection<String> sortFieldNames = new HashSet<>();
        List<Sort.Order> lAppliedOrder = new ArrayList<>();
        //Añade campos de ordenación específicos
        if (sort != null) {
            sort.forEach(o -> {
                if (fieldNames.contains(o.getProperty().toLowerCase())) {
                    sortFieldNames.add(o.getProperty().toLowerCase());
                    query.orderBy(new OrderSpecifier(Order.valueOf(o.getDirection().toString()), Expressions.stringPath(o.getProperty().toLowerCase())));
                    lAppliedOrder.add(new Sort.Order(o.getDirection(), o.getProperty()).ignoreCase());
                }

            });
        }
        if (sortFieldNames.isEmpty()) {
            //Añade campos de ordenación mínimos
            for (String idField : mapper.getIDFields()) {
                if (!sortFieldNames.contains(idField)) {
                    query.orderBy(new OrderSpecifier(Order.ASC, Expressions.stringPath(idField)));
                    lAppliedOrder.add(new Sort.Order(Sort.Direction.ASC, idField).ignoreCase());
                }
            }
        }
        if (!lAppliedOrder.isEmpty()) {
            return Sort.by(lAppliedOrder);
        }
        return null;
    }

    private void setPagination(final SQLQuery query, final Pageable pageInfo) {
        QueryModifiers modifiers = new QueryModifiers(Integer.toUnsignedLong(pageInfo.getPageSize()), pageInfo.getOffset());
        query.restrict(modifiers);
    }

}
