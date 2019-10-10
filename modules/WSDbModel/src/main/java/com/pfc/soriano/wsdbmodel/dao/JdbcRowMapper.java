/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author nacho
 * @param <T>
 * @param <ID>
 */
@Component
public abstract class JdbcRowMapper<T extends Object, ID extends Serializable> implements RowMapper<T> {

    public interface FieldsEnum {

        public boolean isPrimaryKey();

        public boolean isAutoIncremented();
    }
    private FieldsEnum[] fields;
    private String tableName;

    protected void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return this.tableName;
    }

    protected void setFields(FieldsEnum[] fields) {
        this.fields = fields;
    }

    public FieldsEnum[] getFields() {
        return fields;
    }

    public Collection<String> getFieldsNames() {
        Collection<String> fieldNames = new ArrayList<>();
        for (JdbcRowMapper.FieldsEnum field : getFields()) {
            fieldNames.add(field.toString().toLowerCase());
        }
        return fieldNames;
    }

    public String[] getIDFields() {
        List<String> ids = new ArrayList<>();
        for (FieldsEnum f : fields) {
            if (f.isPrimaryKey()) {
                // Must be indicated in lower case in Progress
                ids.add(f.toString().toLowerCase());
            }
        }
        return ids.toArray(new String[0]);
    }

    public boolean isANewObject(T entity) {
        return (null == getID(entity));
    }

    public SqlParameterSource getParamSource(T entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SqlParameterSource getParamSourceByID(ID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ID getID(T entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void populateKey(T entity, KeyHolder keyHolder) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public T mapRow(ResultSet rs, int i) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
