/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.subcategoria;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author nacho
 */
@ApiModel(description = "Objecto con información de subcategoria a crear.", value = "Objecto con información de subcategoria a crear.")
class RSSubcategoriaRequest {

    @ApiModelProperty(value = "Nombre de subcategoria")
    @NotNull
    @Size(min = 1, max = 45)
    private String nombre;
    @ApiModelProperty(value = "Categoria a la que pertenece la subcategoria")
    @NotNull
    private String categoria;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

}
