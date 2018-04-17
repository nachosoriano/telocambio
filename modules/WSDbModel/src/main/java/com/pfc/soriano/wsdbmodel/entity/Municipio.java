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
@Table(name = "municipio")
public class Municipio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DC")
    private int dc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private int codigo;
    @JoinColumn(name = "PROVINCIA", referencedColumnName = "ID")
    @RestResource(exported = false)
    @ManyToOne(optional = false)
    private Provincia provincia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "municipio")
    private Collection<Usuario> usuarioCollection;

    public Municipio() {
    }

    public Municipio(Long id) {
        this();
        this.id = id;
    }

    public Municipio(Long id, String nombre, int dc, int codigo) {
        this(id);
        this.nombre = nombre;
        this.dc = dc;
        this.codigo = codigo;
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

    @JsonProperty
    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    @JsonIgnore
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
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
        if (!Objects.equals(this.provincia, other.provincia)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pfc.soriano.wsdbmodel.entity.Municipio[ id=" + id + " ]";
    }

}
