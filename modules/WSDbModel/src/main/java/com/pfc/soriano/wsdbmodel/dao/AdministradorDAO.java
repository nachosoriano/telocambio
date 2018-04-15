/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.dao;

import com.pfc.soriano.wsdbmodel.entity.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author NACHO
 */
@RepositoryRestResource(collectionResourceRel = "administradores", itemResourceRel = "administradores")
public interface AdministradorDAO extends JpaRepository<Administrador, Long> {

    Administrador findByNombreAndClave(@Param("nombre") String nombre, @Param("clave") String clave);
}
