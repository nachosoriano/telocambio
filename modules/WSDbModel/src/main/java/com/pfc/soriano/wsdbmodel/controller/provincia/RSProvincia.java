/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.provincia;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author nacho
 */
@ApiModel(description = "Objecto con información de provincia.", value = "Objecto con información de provincia.")
class RSProvincia {

    @ApiModelProperty(value = "Identificador de provincia")
    @NotNull
    private Long id;
    @ApiModelProperty(value = "Nombre de provincia")
    @NotNull
    @Size(min = 1, max = 100)
    private String nombre;

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

}
