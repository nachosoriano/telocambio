/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pfc.soriano.wsdbmodel.controller.categoria.CategoriaEstado;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author NACHO
 */
@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "ESTADO")
    private CategoriaEstado estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoria")
    private Collection<Subcategoria> subcategoriaCollection;

    public Categoria() {
        this.estado = CategoriaEstado.BORRADO;
    }

    public Categoria(String nombre) {
        this();
        this.nombre = nombre;
    }

    public Categoria(String nombre, CategoriaEstado estado) {
        this(nombre);
        this.estado = estado;
    }

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

    @JsonIgnore
    public Collection<Subcategoria> getSubcategoriaCollection() {
        return subcategoriaCollection;
    }

    public void setSubcategoriaCollection(Collection<Subcategoria> subcategoriaCollection) {
        this.subcategoriaCollection = subcategoriaCollection;
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
        StringBuilder sb = new StringBuilder();
        sb.append("Id:\t").append(id).append("\n");
        sb.append("Nombre:\t").append(nombre).append("\n");
        sb.append("Activo:\t").append(estado).append("\n");
        return sb.toString();
    }

}
