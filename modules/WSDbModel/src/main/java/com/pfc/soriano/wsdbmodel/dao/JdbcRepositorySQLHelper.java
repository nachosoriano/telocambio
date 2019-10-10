/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.dao;

import com.querydsl.sql.SQLQuery;

/**
 *
 * @author scastaneda
 */
class JdbcRepositorySQLHelper implements IJdbcRepositorySQLHelper {

    @Override
    public String generatefieldList(JdbcRowMapper.FieldsEnum fields[]) {
        StringBuilder seq = new StringBuilder();
        for (JdbcRowMapper.FieldsEnum f : fields) {
            seq.append(f.toString());
            seq.append(",");
        }
        if (seq.length() > 0) {
            seq.deleteCharAt(seq.length() - 1);
        }
        return seq.toString();
    }

    @Override
    public String generatefieldValueList(JdbcRowMapper.FieldsEnum fields[]) {
        StringBuilder seq = new StringBuilder();
        for (JdbcRowMapper.FieldsEnum f : fields) {
            seq.append(":");
            seq.append(f.toString());
            seq.append(",");
        }
        if (seq.length() > 0) {
            seq.deleteCharAt(seq.length() - 1);
        }
        return seq.toString();
    }

    @Override
    public String generateInsertValues(final String tableName, JdbcRowMapper.FieldsEnum fields[]) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String generateValuesUpdateAll(JdbcRowMapper.FieldsEnum fields[]) {
        StringBuilder seq = new StringBuilder();
        for (JdbcRowMapper.FieldsEnum f : fields) {
            if (!f.isAutoIncremented()) {
                seq.append(f.toString());
                seq.append("= :");
                seq.append(f.toString());
                seq.append(",");
            }
        }
        if (seq.length() > 0) {
            seq.deleteCharAt(seq.length() - 1);
        }
        return seq.toString();
    }

    @Override
    public String generateWhereByPrimaryKey(JdbcRowMapper.FieldsEnum fields[]) {
        StringBuilder seq = new StringBuilder();
        for (JdbcRowMapper.FieldsEnum f : fields) {
            if (f.isPrimaryKey()) {
                seq.append(f.toString());
                seq.append("= :");
                seq.append(f.toString());
                seq.append(" AND ");
            }
        }
        if (seq.length() > 0) {
            seq.delete(seq.length() - 5, seq.length());
        }
        return seq.toString();
    }

    @Override
    public SQLQuery getQuery() {
        SQLQuery query = new SQLQuery();
        query.setUseLiterals(true);
        return query;
    }

}
