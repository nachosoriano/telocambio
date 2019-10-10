/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.dao;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

/**
 *
 * @author micade
 */
@Component()
@ConditionalOnBean(JdbcCrudRepository.class)
@ConditionalOnExpression("'${spring.datasource.url}'.startsWith('jdbc:postgresql')")
class JdbcPostgreRepositorySQLHelper extends JdbcRepositorySQLHelper {

    @Override
    public String generateInsertValues(final String tableName, JdbcRowMapper.FieldsEnum fields[]) {
        StringBuilder seq = new StringBuilder();
        for (JdbcRowMapper.FieldsEnum f : fields) {
            if (f.isAutoIncremented()) {
                seq.append("COALESCE(")
                        .append(":")
                        .append(f.toString())
                        .append(",")
                        .append("nextval(pg_get_serial_sequence('")
                        .append(tableName)
                        .append("', '")
                        .append(f.toString().toLowerCase())
                        .append("'))),");
            } else {
                seq.append(":");
                seq.append(f.toString());
                seq.append(",");
            }
        }
        if (seq.length() > 0) {
            seq.deleteCharAt(seq.length() - 1);
        }
        return seq.toString();
    }
}
