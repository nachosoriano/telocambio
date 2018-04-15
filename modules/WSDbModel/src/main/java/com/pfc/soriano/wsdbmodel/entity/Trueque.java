/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pfc.soriano.wsdbmodel.Utils;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 *
 * @author NACHO
 */
@Entity
@Table(name = "trueque")
public class Trueque implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TITULO")
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "DESCRIPCION_OFERTA")
    private String descripcionOferta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "DESCRIPCION_DEMANDA")
    private String descripcionDemanda;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private Long estado;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "FECHA_ACTUALIZACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;
    @Transient
    private Collection<String> fotos;
    @JoinColumn(name = "SUBCAT_DEMANDA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    @RestResource(exported = false)
    private Subcategoria subcatDemanda;
    @JoinColumn(name = "SUBCAT_OFERTA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    @RestResource(exported = false)
    private Subcategoria subcatOferta;
    @JoinColumn(name = "USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    @RestResource(exported = false)
    private Usuario usuario;

    public Trueque() {
        estado = 0L;
        fechaCreacion = new java.sql.Date(System.currentTimeMillis());
    }

    public Trueque(Long id) {
        this.id = id;
        estado = 0L;
        fechaCreacion = new java.sql.Date(System.currentTimeMillis());
    }

    public Trueque(Long id, String descripcionOferta, String descripcionDemanda) {
        this.id = id;
        fechaCreacion = new java.sql.Date(System.currentTimeMillis());
        estado = 0L;
        this.descripcionOferta = descripcionOferta;
        this.descripcionDemanda = descripcionDemanda;
    }

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

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
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
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trueque)) {
            return false;
        }
        Trueque other = (Trueque) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id:\t").append(id).append("\n");
        sb.append("Título:\t").append(titulo).append("\n");
        sb.append("Fecha de creación:\t").append(fechaCreacion).append("\n");
        sb.append("Oferta:\n");
        sb.append("\tSubcategoría: ").append(subcatOferta).append("\n");
        sb.append("\tDescripción: ").append(descripcionOferta).append("\n");
        sb.append("Demanda:\n");
        sb.append("\tSubcategoría: ").append(subcatDemanda).append("\n");
        sb.append("\tDescripción: ").append(descripcionDemanda).append("\n");
        return sb.toString();
    }

}
