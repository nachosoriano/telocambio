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
@ApiModel(description = "Objecto con información de subcategoria.", value = "Objecto con información de subcategoria.")
class RSSubcategoria {

    @ApiModelProperty(value = "Identifidador de subcategoria")
    @NotNull
    private Long id;
    @ApiModelProperty(value = "Nombre de subcategoria")
    @NotNull
    @Size(min = 1, max = 45)
    private String nombre;
    @ApiModelProperty(value = "Estado de subcategoria")
    @NotNull
    private String estado;
    @ApiModelProperty(value = "Categoria a la que pertenece la subcategoria")
    @NotNull
    private String categoria;

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

}
