/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.trueque;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author NACHO
 */
@ApiModel(description = "Objecto con información de trueque a crear.", value = "Objecto con información de trueque a crear.")
class RSTruequeRequest implements Serializable {

    @ApiModelProperty(value = "Título de trueque")
    @NotNull
    @Size(min = 1, max = 100)
    private String titulo;
    @ApiModelProperty(value = "Descripción de oferta")
    @NotNull
    @Size(min = 1, max = 300)
    private String descripcionOferta;
    @ApiModelProperty(value = "Descripción de demanda")
    @NotNull
    @Size(min = 1, max = 300)
    private String descripcionDemanda;
    @ApiModelProperty(value = "Subcategoría de demanda")
    @NotNull
    private String subcatDemanda;
    @ApiModelProperty(value = "Subcategoría de oferta")
    @NotNull
    private String subcatOferta;
    @ApiModelProperty(value = "Usuario")
    @NotNull
    private String usuario;

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

    public String getSubcatDemanda() {
        return subcatDemanda;
    }

    public void setSubcatDemanda(String subcatDemanda) {
        this.subcatDemanda = subcatDemanda;
    }

    public String getSubcatOferta() {
        return subcatOferta;
    }

    public void setSubcatOferta(String subcatOferta) {
        this.subcatOferta = subcatOferta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
