/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 *
 * @author NACHO
 */
@Entity
@Table(name = "subcategoria")
public class Subcategoria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "NOMBRE")
    private String nombre;
    @NotNull
    @Column(name = "estado")
    private Integer estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subcatDemanda")
    private Collection<Trueque> truequeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subcatOferta")
    private Collection<Trueque> truequeCollection1;
    @JoinColumn(name = "CATEGORIA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    @RestResource(exported = false)
    private Categoria categoria;

    public Subcategoria() {
        this.estado = 0;
    }

    public Subcategoria(String nombre) {
        this();
        this.nombre = nombre;
    }

    public Subcategoria(String nombre, Integer estado) {
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

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @JsonIgnore
    public Collection<Trueque> getTruequeCollection() {
        return truequeCollection;
    }

    public void setTruequeCollection(Collection<Trueque> truequeCollection) {
        this.truequeCollection = truequeCollection;
    }

    @JsonIgnore
    public Collection<Trueque> getTruequeCollection1() {
        return truequeCollection1;
    }

    public void setTruequeCollection1(Collection<Trueque> truequeCollection1) {
        this.truequeCollection1 = truequeCollection1;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    @JsonProperty
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.nombre);
        hash = 29 * hash + Objects.hashCode(this.categoria);
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
        final Subcategoria other = (Subcategoria) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.categoria, other.categoria)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(categoria.getNombre()).append(" - ").append(nombre);
        return sb.toString();
    }

}
