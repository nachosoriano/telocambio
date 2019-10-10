/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.dao.categoria;

import com.pfc.soriano.wsdbmodel.dao.JdbcCrudRepository;
import com.pfc.soriano.wsdbmodel.entity.Categoria;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nacho
 */
@Repository
class CategoriaRepository extends JdbcCrudRepository<Categoria, Long> implements CategoriaDAO {

}
