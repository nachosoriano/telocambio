/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.dao;

import com.pfc.soriano.wsdbmodel.entity.Subcategoria;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author NACHO
 */
@RepositoryRestResource(collectionResourceRel = "subcategorias", itemResourceRel = "subcategorias")
public interface SubcategoriaDAO extends JpaRepository<Subcategoria, Long> {

    Collection<Subcategoria> findByCategoriaIdOrderByNombre(@Param("categoriaId") Long categoriaId);

    Collection<Subcategoria> findByCategoriaIdAndEstadoOrderByNombre(@Param("categoriaId") Long categoriaId, @Param("estado") Integer estado);

    Subcategoria findByCategoriaIdAndNombre(@Param("categoriaId") Long categoriaId, @Param("nombre") String nombre);
}
