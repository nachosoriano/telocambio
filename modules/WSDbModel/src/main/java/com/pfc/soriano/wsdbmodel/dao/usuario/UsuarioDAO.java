/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.dao.usuario;

import com.pfc.soriano.wsdbmodel.entity.Usuario;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author NACHO
 */
public interface UsuarioDAO extends PagingAndSortingRepository<Usuario, Long> {

    Usuario findByEmail(@Param("email") String email);

    Usuario findByEmailAndClave(@Param("email") String email, @Param("clave") String clave);
}
