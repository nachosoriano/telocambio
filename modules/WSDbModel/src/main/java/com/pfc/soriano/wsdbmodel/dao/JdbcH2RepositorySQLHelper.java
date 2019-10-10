package com.pfc.soriano.wsdbmodel.dao;

import com.querydsl.sql.H2Templates;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLTemplates;
import javax.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

/**
 *
 * @author scastaneda
 */
@Component
@ConditionalOnBean(JdbcCrudRepository.class)
@ConditionalOnExpression("'${spring.datasource.url}'.startsWith('jdbc:h2')")
class JdbcH2RepositorySQLHelper extends JdbcRepositorySQLHelper {

    private SQLTemplates template;

    @PostConstruct
    public void initialize() {
        template = new H2Templates();
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
