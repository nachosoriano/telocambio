/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.dao.valoracion;

import com.pfc.soriano.wsdbmodel.entity.Valoracion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author NACHO
 */
public interface ValoracionDAO extends PagingAndSortingRepository<Valoracion, Long> {

    Valoracion findByUsuarioOrigenIdAndUsuarioDestinoId(@Param("usuarioOrigenId") Long usuarioOrigenId, @Param("usuarioDestinoId") Long usuarioDestinoId);

    Page<Valoracion> findByUsuarioDestinoId(@Param("usuarioDestinoId") Long usuarioDestinoId, Pageable page);
}
