/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.dao;

import java.io.Serializable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 *
 * @author nacho
 * @param <T>
 * @param <ID>
 */
public interface IJdbcCrudRepository<T extends Object, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {

    NamedParameterJdbcTemplate getJdbcTemplate();
}
