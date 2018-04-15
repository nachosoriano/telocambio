/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.dao;

import com.pfc.soriano.wsdbmodel.entity.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author NACHO
 */
@RepositoryRestResource(collectionResourceRel = "provincias", itemResourceRel = "provincias")
public interface ProvinciaDAO extends JpaRepository<Provincia, Long> {

}
