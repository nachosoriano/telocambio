/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.dao.subcategoria;

import com.pfc.soriano.wsdbmodel.entity.Subcategoria;
import java.util.Collection;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author NACHO
 */
public interface SubcategoriaDAO extends PagingAndSortingRepository<Subcategoria, Long> {

    Collection<Subcategoria> findByCategoriaIdOrderByNombre(@Param("categoriaId") Long categoriaId);

    Collection<Subcategoria> findByCategoriaIdAndEstadoOrderByNombre(@Param("categoriaId") Long categoriaId, @Param("estado") Integer estado);

    Subcategoria findByCategoriaIdAndNombre(@Param("categoriaId") Long categoriaId, @Param("nombre") String nombre);
}
