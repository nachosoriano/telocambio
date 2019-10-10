/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pfc.soriano.wsdbmodel.Utils;
import com.pfc.soriano.wsdbmodel.controller.usuario.UsuarioEstado;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import javax.validation.constraints.Pattern;

/**
 *
 * @author NACHO
 */
public class Usuario implements Serializable {

    private Long id;
    private String nombre;
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email")
    // if the field contains email address consider using this annotation to
    // enforce field validation
    private String email;
    private String clave;
    private UsuarioEstado estado;
    private Float puntuacion;
    private Collection<String> fotos;
    private Collection<Trueque> truequeCollection;
    private Collection<Valoracion> valoracionCollection;
    private Collection<Valoracion> valoracionCollection1;
    private Municipio municipio;

    public Long getId() {
        fotos = Utils.getImages("" + id, Utils.EEntityType.USUARIO);
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        fotos = Utils.getImages("" + id, Utils.EEntityType.USUARIO);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Collection<String> getFotos() {
        return fotos;
    }

    public void setFotos(Collection<String> fotos) {
        this.fotos = fotos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public UsuarioEstado getEstado() {
        return estado;
    }

    public void setEstado(UsuarioEstado estado) {
        this.estado = estado;
    }

    public Float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    @JsonProperty
    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    @JsonIgnore
    public Collection<Trueque> getTruequeCollection() {
        return truequeCollection;
    }

    public void setTruequeCollection(Collection<Trueque> truequeCollection) {
        this.truequeCollection = truequeCollection;
    }

    @JsonIgnore
    public Collection<Valoracion> getValoracionCollection() {
        return valoracionCollection;
    }

    public void setValoracionCollection(Collection<Valoracion> valoracionCollection) {
        this.valoracionCollection = valoracionCollection;
    }

    @JsonIgnore
    public Collection<Valoracion> getValoracionCollection1() {
        return valoracionCollection1;
    }

    public void setValoracionCollection1(Collection<Valoracion> valoracionCollection1) {
        this.valoracionCollection1 = valoracionCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.email);
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
        final Usuario other = (Usuario) obj;
        return Objects.equals(this.email, other.email);
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre=" + nombre + ", email=" + email + ", clave=" + clave + ", estado=" + estado + ", puntuacion=" + puntuacion + ", fotos=" + fotos + ", truequeCollection=" + truequeCollection + ", valoracionCollection=" + valoracionCollection + ", valoracionCollection1=" + valoracionCollection1 + ", municipio=" + municipio + '}';
    }

}
