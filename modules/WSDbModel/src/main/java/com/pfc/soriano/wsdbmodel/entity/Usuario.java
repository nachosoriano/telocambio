/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pfc.soriano.wsdbmodel.Utils;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 *
 * @author NACHO
 */
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

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
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email")
    // if the field contains email address consider using this annotation to
    // enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "CLAVE")
    private String clave;
    @Column(name = "ESTADO")
    private Integer estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PUNTUACION")
    private Float puntuacion;
    @Transient
    private Collection<String> fotos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<Trueque> truequeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioDestino")
    private Collection<Valoracion> valoracionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioOrigen")
    private Collection<Valoracion> valoracionCollection1;
    @JoinColumn(name = "MUNICIPIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    @RestResource(exported = false)
    private Municipio municipio;

    public Usuario() {
        this.estado = 0;
        this.puntuacion = 0f;
    }

    public Usuario(Long id) {
        this();
        this.id = id;
    }

    public Usuario(Long id, String nombre, String email, String clave) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        this(id);
        this.nombre = nombre;
        this.email = email;
        this.clave = clave;
    }

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

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
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
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id:\t").append(id).append("\n");
        sb.append("Nombre:\t").append(nombre).append("\n");
        sb.append("Email:\t").append(email).append("\n");
        sb.append("Provincia:\t").append(municipio.getProvincia().getNombre()).append("\n");
        sb.append("Municipio:\t").append(municipio.getNombre()).append("\n");
        return sb.toString();
    }

}
