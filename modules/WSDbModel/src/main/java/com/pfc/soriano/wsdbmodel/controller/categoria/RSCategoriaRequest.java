/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.categoria;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author nacho
 */
@ApiModel(value = "Objecto con información de categoria a crear", description = "Objecto con información de categoria a crear")
class RSCategoriaRequest {

    @ApiModelProperty(value = "Nombre de categoria", required = true)
    @NotNull
    @Size(min = 1, max = 45)
    private String nombre;
    @ApiModelProperty(value = "Estado de categoria", required = true)
    @NotNull
    private String estado;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
