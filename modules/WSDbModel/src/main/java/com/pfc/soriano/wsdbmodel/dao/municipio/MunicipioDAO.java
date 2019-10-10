/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.dao.municipio;

import com.pfc.soriano.wsdbmodel.entity.Municipio;
import com.pfc.soriano.wsdbmodel.entity.Provincia;
import java.util.Collection;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author NACHO
 */
public interface MunicipioDAO extends PagingAndSortingRepository<Municipio, Long> {

    Collection<Municipio> findByProvinciaOrderByNombre(@Param("provincia") Provincia provincia);

    Collection<Municipio> findByProvincia(@Param("provincia") Provincia provincia);

    Municipio findByProvinciaAndNombre(@Param("provincia") Provincia provincia, @Param("nombre") String nombre);
}
