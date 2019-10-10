/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.dao.categoria;

import com.pfc.soriano.wsdbmodel.controller.categoria.CategoriaEstado;
import com.pfc.soriano.wsdbmodel.dao.JdbcRowMapper;
import com.pfc.soriano.wsdbmodel.entity.Categoria;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author nacho
 */
@Component
public class CategoriaRowMapper extends JdbcRowMapper<Categoria, Long> {

    public static final String TABLE_NAME = "device";

    public enum Fields implements JdbcRowMapper.FieldsEnum {
        ID(true, true),
        NOMBRE,
        ESTADO;

        final private boolean primaryKey;
        final private boolean autoIncrement;

        private Fields() {
            this.primaryKey = false;
            this.autoIncrement = false;
        }

        private Fields(boolean primaryKey, boolean autoIncrement) {
            this.primaryKey = primaryKey;
            this.autoIncrement = autoIncrement;
        }

        @Override
        public boolean isPrimaryKey() {
            return primaryKey;
        }

        @Override
        public boolean isAutoIncremented() {
            return autoIncrement;
        }
    }

    CategoriaRowMapper() {
        super();
        this.setFields(Fields.values());
        this.setTableName(TABLE_NAME);
    }

    @Override
    public SqlParameterSource getParamSource(Categoria entity) {
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put(Fields.ID.toString(), entity.getId());
        namedParameters.put(Fields.NOMBRE.toString(), entity.getNombre());
        namedParameters.put(Fields.ESTADO.toString(), entity.getEstado().intValue());
        SqlParameterSource paramSource = new MapSqlParameterSource(namedParameters);
        return paramSource;
    }

    @Override
    public void populateKey(Categoria entity, KeyHolder keyHolder) {
        entity.setId(keyHolder.getKey().longValue());
    }

    @Override
    public Categoria mapRow(ResultSet rs, int i) throws SQLException {
        Categoria item = new Categoria();
        item.setId(rs.getLong(Fields.ID.toString()));
        item.setNombre(rs.getString(Fields.NOMBRE.toString()));
        item.setEstado(CategoriaEstado.fromIntValue(rs.getInt(Fields.ESTADO.toString())));
        return item;
    }

    @Override
    public SqlParameterSource getParamSourceByID(Long id) {
        return new MapSqlParameterSource(Fields.ID.toString(), id);
    }

    @Override
    public Long getID(Categoria entity) {
        return entity.getId();
    }

}
