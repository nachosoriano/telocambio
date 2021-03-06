/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.dao;

import com.pfc.soriano.wsdbmodel.entity.Municipio;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author NACHO
 */
@RepositoryRestResource(collectionResourceRel = "municipios", itemResourceRel = "municipios")
public interface MunicipioDAO extends JpaRepository<Municipio, Long> {

    Collection<Municipio> findByProvinciaIdOrderByNombre(@Param("provinciaId") Long provinciaId);

    Collection<Municipio> findByProvinciaId(@Param("provinciaId") Long provinciaId);
}
