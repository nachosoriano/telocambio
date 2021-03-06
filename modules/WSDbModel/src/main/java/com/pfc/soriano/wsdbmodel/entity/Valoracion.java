/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 *
 * @author NACHO
 */
@Entity
@Table(name = "valoracion")
public class Valoracion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PUNTUACION")
    private int puntuacion;
    @Size(max = 300)
    @Column(name = "COMENTARIO")
    private String comentario;
    @JoinColumn(name = "USUARIO_DESTINO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    @RestResource(exported = false)
    private Usuario usuarioDestino;
    @JoinColumn(name = "USUARIO_ORIGEN", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    @RestResource(exported = false)
    private Usuario usuarioOrigen;

    public Valoracion() {
    }

    public Valoracion(Long id) {
        this();
        this.id = id;
    }

    public Valoracion(Long id, int puntuacion) {
        this(id);
        this.puntuacion = puntuacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getUsuarioDestino() {
        return usuarioDestino;
    }

    @JsonProperty
    public void setUsuarioDestino(Usuario usuarioDestino) {
        this.usuarioDestino = usuarioDestino;
    }

    public Usuario getUsuarioOrigen() {
        return usuarioOrigen;
    }

    @JsonProperty
    public void setUsuarioOrigen(Usuario usuarioOrigen) {
        this.usuarioOrigen = usuarioOrigen;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.usuarioDestino);
        hash = 43 * hash + Objects.hashCode(this.usuarioOrigen);
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
        final Valoracion other = (Valoracion) obj;
        if (!Objects.equals(this.usuarioDestino, other.usuarioDestino)) {
            return false;
        }
        if (!Objects.equals(this.usuarioOrigen, other.usuarioOrigen)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pfc.soriano.wsdbmodel.entity.Valoracion[ id=" + id + " ]";
    }

}
