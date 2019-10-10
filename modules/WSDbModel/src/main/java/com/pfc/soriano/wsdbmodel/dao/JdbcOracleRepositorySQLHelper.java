/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.dao;

import com.querydsl.sql.OracleTemplates;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLTemplates;
import javax.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

/**
 *
 * @author micade
 */
@Component()
@ConditionalOnBean(JdbcCrudRepository.class)
@ConditionalOnExpression("'${spring.datasource.url}'.startsWith('jdbc:oracle')")
class JdbcOracleRepositorySQLHelper extends JdbcRepositorySQLHelper {

    private SQLTemplates template;

    @PostConstruct
    public void initialize() {
        template = new OracleTemplates();
    }

    @Override
    public String generateInsertValues(final String tableName, JdbcRowMapper.FieldsEnum fields[]) {
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
    public SQLQuery getQuery() {
        SQLQuery query = new SQLQuery(template);
        query.setUseLiterals(true);
        return query;
    }

}
