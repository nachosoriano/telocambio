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

    /**
     *
     */
    private static final long serialVersionUID = 1L;
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
    @Column(name = "ACTIVO")
    private Boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subcatDemanda")
    private Collection<Trueque> truequeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subcatOferta")
    private Collection<Trueque> truequeCollection1;
    @JoinColumn(name = "CATEGORIA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    @RestResource(exported = false)
    private Categoria categoria;

    public Subcategoria() {
        this.activo = false;
    }

    public Subcategoria(String nombre) {
        this.nombre = nombre;
        this.activo = false;
    }

    public Subcategoria(String nombre, Boolean activo) {
        this.nombre = nombre;
        this.activo = activo;
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

    public Boolean isActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are
        // not set
        if (!(object instanceof Subcategoria)) {
            return false;
        }
        Subcategoria other = (Subcategoria) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(categoria.getNombre()).append(" - ").append(nombre);
        return sb.toString();
    }

}
