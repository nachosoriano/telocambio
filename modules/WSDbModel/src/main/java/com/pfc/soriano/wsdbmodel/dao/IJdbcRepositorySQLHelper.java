/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.dao;

import com.querydsl.sql.SQLQuery;

/**
 *
 * @author micade
 */
public interface IJdbcRepositorySQLHelper {

    /**
     * Returns a string with all the fields stored on the rowMapper
     *
     * @param fields
     * @return
     */
    String generatefieldList(JdbcRowMapper.FieldsEnum fields[]);

    /**
     * Returns a string with all the fields stored on the rowMapper. Each field will be included the character ":" as prefix.
     *
     * @param fields
     * @return
     */
    String generatefieldValueList(JdbcRowMapper.FieldsEnum fields[]);

    /**
     * Returns a string with all the fields values stored on the rowMapper. The tableName will be used if it's needed to read the sequence
     * associated with an autoincrement column (Only in postgre).
     *
     * @param tableName
     * @param fields
     * @return
     */
    String generateInsertValues(final String tableName, JdbcRowMapper.FieldsEnum fields[]);

    /**
     * Returns a string with all the fields stored on the rowMapper, ignoring autoincrement columns.
     *
     * @param fields
     * @return
     */
    String generateValuesUpdateAll(JdbcRowMapper.FieldsEnum fields[]);

    /**
     * Returns a string with the input fields marked as primary keys, separated by the logical operator "AND".
     *
     * @param fields
     * @return
     */
    String generateWhereByPrimaryKey(JdbcRowMapper.FieldsEnum fields[]);

    /**
     * Method to generate one SQLQuery.
     *
     * @return SQLQuery generated.
     */
    SQLQuery getQuery();

}
