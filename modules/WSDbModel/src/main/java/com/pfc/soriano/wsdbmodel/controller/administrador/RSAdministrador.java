/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.administrador;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author nacho
 */
@ApiModel(value = "Objecto con información de administrador", description = "Objecto con información de administrador")
class RSAdministrador {

    @ApiModelProperty(value = "Identificador de administrador", required = true)
    @NotNull
    private Long id;
    @ApiModelProperty(value = "Nombre de administrador", required = true)
    @NotNull
    @Size(min = 1, max = 45)
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
