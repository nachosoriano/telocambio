/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.municipio;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author nacho
 */
@ApiModel(description = "Objeto con información de municipio.", value = "Objeto con información de municipio.")
class RSMunicipio {

    @ApiModelProperty(value = "Identificador de municipio", required = true)
    @NotNull
    private Long id;
    @ApiModelProperty(value = "Nombre de municipio", required = true)
    @NotNull
    @Size(min = 1, max = 100)
    private String nombre;
    @ApiModelProperty(value = "DC de municipio", required = true)
    @NotNull
    private Integer dc;
    @ApiModelProperty(value = "Codigo de municipio", required = true)
    @NotNull
    private Integer codigo;
    @ApiModelProperty(value = "Provincia de municipio", required = true)
    @NotNull
    private String provincia;

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

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

}
