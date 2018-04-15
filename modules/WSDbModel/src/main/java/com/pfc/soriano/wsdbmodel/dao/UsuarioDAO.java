/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.dao;

import com.pfc.soriano.wsdbmodel.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author NACHO
 */
@RepositoryRestResource(collectionResourceRel = "usuarios", itemResourceRel = "usuarios")
public interface UsuarioDAO extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(@Param("email") String email);

    Usuario findByEmailAndClave(@Param("email") String email, @Param("clave") String clave);
}
