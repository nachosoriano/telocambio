/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.dao.provincia;

import com.pfc.soriano.wsdbmodel.entity.Provincia;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author NACHO
 */
public interface ProvinciaDAO extends PagingAndSortingRepository<Provincia, Long> {

    public Provincia findByNombre(String nombre);
}
