package com.pfc.soriano.ws.test.util.enums;

public enum EMethod {

    FIND_BY_NOMBRE("findByNombre"),
    FIND_BY_NOMBRE_AND_CLAVE("findByNombreAndClave"),
    SEARCH_BY_NOMBRE("search/findByNombre"),
    SEARCH_BY_NOMBRE_AND_CLAVE("search/findByNombreAndClave"),
    LOGIN("login"),
    CHANGE_CLAVE("changeClave"),
    REGISTER("register"),
    FIND_BY_DESCRIPCION_LIKE("findByDescripcionLike"),
    FIND_BY_CATEGORIAID_AND_NOMBRE("findByCategoriaIdAndNombre"),
    FILTER("filter"),
    FIND_BY_TITULO("findByTitulo"),
    COUNT_FILTERED("countFiltered"),
    CREATE_TRUEQUE("createTrueque"),
    FIND_BY_ID("findById"),
    CREATE_VALORACION("createValoracion"),
    FIND_BY_USUARIO_DESTINO("findByUsuarioDestinoId"),
    FIND_BY_USUARIO_ORIGEN_AND_DESTINO("findByUsuarioOrigenIdAndUsuarioDestinoId"),
    PROPONER_TRUEQUE("proponer");

    private final String value;

    private EMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
