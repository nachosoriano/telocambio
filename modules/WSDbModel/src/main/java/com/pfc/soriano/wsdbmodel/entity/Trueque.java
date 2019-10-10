/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pfc.soriano.wsdbmodel.Utils;
import com.pfc.soriano.wsdbmodel.controller.trueque.TruequeEstado;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author nacho
 */
public class Trueque implements Serializable {

    private Long id;
    private String titulo;
    private String descripcionOferta;
    private String descripcionDemanda;
    private TruequeEstado estado;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private Collection<String> fotos;
    private Subcategoria subcatDemanda;
    private Subcategoria subcatOferta;
    private Usuario usuario;

    public Long getId() {
        fotos = Utils.getImages("" + id, Utils.EEntityType.TRUEQUE);
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        fotos = Utils.getImages("" + id, Utils.EEntityType.TRUEQUE);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcionOferta() {
        return descripcionOferta;
    }

    public void setDescripcionOferta(String descripcionOferta) {
        this.descripcionOferta = descripcionOferta;
    }

    public String getDescripcionDemanda() {
        return descripcionDemanda;
    }

    public void setDescripcionDemanda(String descripcionDemanda) {
        this.descripcionDemanda = descripcionDemanda;
    }

    public TruequeEstado getEstado() {
        return estado;
    }

    public void setEstado(TruequeEstado estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Collection<String> getFotos() {
        return fotos;
    }

    public void setFotos(Collection<String> fotos) {
        this.fotos = fotos;
    }

    public Subcategoria getSubcatDemanda() {
        return subcatDemanda;
    }

    @JsonProperty
    public void setSubcatDemanda(Subcategoria subcatDemanda) {
        this.subcatDemanda = subcatDemanda;
    }

    public Subcategoria getSubcatOferta() {
        return subcatOferta;
    }

    @JsonProperty
    public void setSubcatOferta(Subcategoria subcatOferta) {
        this.subcatOferta = subcatOferta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    @JsonProperty
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.titulo);
        hash = 53 * hash + Objects.hashCode(this.descripcionOferta);
        hash = 53 * hash + Objects.hashCode(this.descripcionDemanda);
        hash = 53 * hash + Objects.hashCode(this.estado);
        hash = 53 * hash + Objects.hashCode(this.subcatDemanda);
        hash = 53 * hash + Objects.hashCode(this.subcatOferta);
        hash = 53 * hash + Objects.hashCode(this.usuario);
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
        final Trueque other = (Trueque) obj;
        if (!Objects.equals(this.titulo, other.titulo)) {
            return false;
        }
        if (!Objects.equals(this.descripcionOferta, other.descripcionOferta)) {
            return false;
        }
        if (!Objects.equals(this.descripcionDemanda, other.descripcionDemanda)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.subcatDemanda, other.subcatDemanda)) {
            return false;
        }
        if (!Objects.equals(this.subcatOferta, other.subcatOferta)) {
            return false;
        }
        return Objects.equals(this.usuario, other.usuario);
    }

    @Override
    public String toString() {
        return "Trueque{" + "id=" + id + ", titulo=" + titulo + ", descripcionOferta=" + descripcionOferta + ", descripcionDemanda=" + descripcionDemanda + ", estado=" + estado + ", fechaCreacion=" + fechaCreacion + ", fechaActualizacion=" + fechaActualizacion + ", fotos=" + fotos + ", subcatDemanda=" + subcatDemanda + ", subcatOferta=" + subcatOferta + ", usuario=" + usuario + '}';
    }

}
