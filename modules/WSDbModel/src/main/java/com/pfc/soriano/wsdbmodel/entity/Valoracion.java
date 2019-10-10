/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author nacho
 */
public class Valoracion implements Serializable {

    private Long id;
    private int puntuacion;
    private String comentario;
    private Usuario usuarioDestino;
    private Usuario usuarioOrigen;

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
        return Objects.equals(this.usuarioOrigen, other.usuarioOrigen);
    }

    @Override
    public String toString() {
        return "Valoracion{" + "id=" + id + ", puntuacion=" + puntuacion + ", comentario=" + comentario + ", usuarioDestino=" + usuarioDestino + ", usuarioOrigen=" + usuarioOrigen + '}';
    }

}
