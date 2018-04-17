/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  nacho
 * Created: 15-apr-2018
 */

-- DROP TABLE administrador;
-- DROP TABLE categoria;
-- DROP TABLE municipio;
-- DROP TABLE provincia;
-- DROP TABLE subcategoria;
-- DROP TABLE trueque;
-- DROP TABLE usuario;
-- DROP TABLE valoracion;

CREATE TABLE administrador (
                id NUMBER auto_increment, 
                nombre VARCHAR2(200),
                clave VARCHAR2(200),
                CONSTRAINT ADMINISTRADOR_PK PRIMARY KEY (id),
                CONSTRAINT ADMINISTRADOR_UK UNIQUE (nombre)
                );

CREATE TABLE categoria (
                id NUMBER auto_increment,
                nombre VARCHAR2(200),
                estado NUMBER(1),
                CONSTRAINT CATEGORIA_PK PRIMARY KEY (id),
                CONSTRAINT CATEGORIA_UK UNIQUE (nombre)
                );

CREATE TABLE municipio (
                id NUMBER auto_increment, 
                nombre VARCHAR2(200),
                dc NUMBER,
                codigo NUMBER,
                provincia NUMBER,
                CONSTRAINT MUNICIPIO_PK PRIMARY KEY (id),
                CONSTRAINT MUNICIPIO_UK UNIQUE(codigo)
                );

CREATE TABLE provincia (
                id NUMBER auto_increment,
                nombre VARCHAR2(200),
                CONSTRAINT PROVINCIA_PK PRIMARY KEY (id),
                CONSTRAINT PROVINCIA_UK UNIQUE (nombre)
                );

CREATE TABLE subcategoria (
                id NUMBER auto_increment,
                nombre VARCHAR2(200),
                estado NUMBER,
                categoria NUMBER,
                CONSTRAINT SUBCATEGORIA_PK PRIMARY KEY (id),
                CONSTRAINT SUBCATEGORIA_UK UNIQUE (categoria, nombre)
                );

CREATE TABLE trueque (
                id NUMBER auto_increment, 
                titulo VARCHAR2(200),
                descripcion_oferta VARCHAR2(4000),
                descripcion_demanda VARCHAR2(4000),
                estado NUMBER,
                fecha_creacion TIMESTAMP,
                fecha_actualizacion TIMESTAMP,
                subcat_oferta NUMBER,
                subcat_demanda NUMBER,
                usuario NUMBER,
                CONSTRAINT TRUEQUE_PK PRIMARY KEY (id)
                );

CREATE TABLE usuario (
                id NUMBER auto_increment,
                nombre VARCHAR2(200),
                email VARCHAR2(200),
                clave VARCHAR2(50),
                estado NUMBER,
                puntuacion NUMBER,
                municipio NUMBER,
                CONSTRAINT USUARIO_PK PRIMARY KEY (id),
                CONSTRAINT USUARIO_UK UNIQUE (email)
                );

CREATE TABLE valoracion (
                id NUMBER auto_increment,
                puntuacion NUMBER,
                comentario VARCHAR2(4000),
                usuario_origen NUMBER,
                usuario_destino NUMBER,
                CONSTRAINT VALORACION_PK PRIMARY KEY (id),
                CONSTRAINT VALORACION_UK UNIQUE (usuario_origen, usuario_destino)
                );
