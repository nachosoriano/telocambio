/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.dao;

import com.pfc.soriano.wsdbmodel.entity.Valoracion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author NACHO
 */
@RepositoryRestResource(collectionResourceRel = "valoraciones", itemResourceRel = "valoraciones")
public interface ValoracionDAO extends JpaRepository<Valoracion, Long> {

    Valoracion findByUsuarioOrigenIdAndUsuarioDestinoId(@Param("usuarioOrigenId") Long usuarioOrigenId, @Param("usuarioDestinoId") Long usuarioDestinoId);

    Page<Valoracion> findByUsuarioDestinoId(@Param("usuarioDestinoId") Long usuarioDestinoId, Pageable page);
}
