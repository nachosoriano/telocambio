/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.entity;

import com.pfc.soriano.wsdbmodel.controller.categoria.CategoriaEstado;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author nacho
 */
public class Categoria implements Serializable {

    private Long id;
    private String nombre;
    private CategoriaEstado estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CategoriaEstado getEstado() {
        return estado;
    }

    public void setEstado(CategoriaEstado estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Categoria other = (Categoria) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Categoria{" + "id=" + id + ", nombre=" + nombre + ", estado=" + estado + '}';
    }

}
