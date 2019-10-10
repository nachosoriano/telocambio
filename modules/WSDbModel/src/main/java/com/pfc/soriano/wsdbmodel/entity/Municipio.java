/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author nacho
 */
public class Municipio implements Serializable {

    private Long id;
    private String nombre;
    private int dc;
    private int codigo;
    private Provincia provincia;

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

    public int getDc() {
        return dc;
    }

    public void setDc(int dc) {
        this.dc = dc;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.nombre);
        hash = 97 * hash + Objects.hashCode(this.provincia);
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
        final Municipio other = (Municipio) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return Objects.equals(this.provincia, other.provincia);
    }

    @Override
    public String toString() {
        return "Municipio{" + "id=" + id + ", nombre=" + nombre + ", dc=" + dc + ", codigo=" + codigo + ", provincia=" + provincia + '}';
    }

}
