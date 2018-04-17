/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.usuario;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author nacho
 */
@ApiModel(description = "Objecto con información de usuario.", value = "Objecto con información de usuario.")
class RSUsuario {

    @ApiModelProperty(value = "Identificador de usuario")
    @NotNull
    private Long id;
    @ApiModelProperty(value = "Nombre de usuario")
    @NotNull
    @Size(min = 1, max = 45)
    private String nombre;
    @ApiModelProperty(value = "Email de usuario")
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email")
    // if the field contains email address consider using this annotation to
    // enforce field validation
    @NotNull
    @Size(min = 1, max = 100)
    private String email;
    @ApiModelProperty(value = "Estado de usuario")
    @NotNull
    private String estado;
    @ApiModelProperty(value = "Puntuación de usuario")
    @NotNull
    private Float puntuacion;
    @ApiModelProperty(value = "Provincia de usuario")
    @NotNull
    private String provincia;
    @ApiModelProperty(value = "Municipio de usuario")
    @NotNull
    private String municipio;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

}
