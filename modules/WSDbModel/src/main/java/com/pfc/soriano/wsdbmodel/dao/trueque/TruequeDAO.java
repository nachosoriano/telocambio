/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.dao.trueque;

import com.pfc.soriano.wsdbmodel.entity.Trueque;
import java.util.Collection;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author NACHO
 */
public interface TruequeDAO extends PagingAndSortingRepository<Trueque, Long> {

    Trueque findByIdAndEstado(@Param("id") Long id, @Param("estado") Long estado);

    Collection<Trueque> findByTitulo(@Param("titulo") String titulo);
}
