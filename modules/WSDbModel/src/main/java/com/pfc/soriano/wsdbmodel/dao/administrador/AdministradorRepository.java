/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.dao.administrador;

import com.pfc.soriano.wsdbmodel.dao.IJdbcRepositorySQLHelper;
import com.pfc.soriano.wsdbmodel.dao.JdbcCrudRepository;
import com.pfc.soriano.wsdbmodel.entity.Administrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author nacho
 */
@Component
class AdministradorRepository extends JdbcCrudRepository<Administrador, Long> implements AdministradorDAO {

    @Autowired
    IJdbcRepositorySQLHelper helper;
}
