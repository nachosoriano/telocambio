package com.pfc.soriano.wsdbmodel.controller.administrador;

import com.pfc.soriano.wsdbmodel.entity.Administrador;
import com.pfc.soriano.wsdbmodel.exception.DbModelException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "administrador")
@Api(tags = {"Administrador"})
@RestController
class AdministradorController {

    @Autowired
    PasswordEncoder encoder;
//    @Autowired
//    AdministradorDAO administradorDAO;

    @Autowired
    Converter<RSAdministradorRequest, Administrador> rsAdministradorRequestConverter;
    @Autowired
    Converter<Administrador, RSAdministrador> administradorConverter;

    @ApiOperation(value = "Registro de administrador.", nickname = "register")
    @RequestMapping(method = RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED)
    @Valid
    public RSAdministrador register(@Valid @RequestBody RSAdministradorRequest administradorRequest) throws DbModelException {
        try {
            Administrador administrador = rsAdministradorRequestConverter.convert(administradorRequest);
            administrador = null; //administradorDAO.save(administrador);
            return administradorConverter.convert(administrador);
        } catch (Throwable ex) {
            if (ex instanceof NonTransientDataAccessException) {
                NonTransientDataAccessException aux = (NonTransientDataAccessException) ex;
                throw new DbModelException(aux.getMostSpecificCause().getMessage(), ex);
            }
            throw new DbModelException(ex.getLocalizedMessage(), ex);
        }
    }

    @ApiOperation(value = "Login de administrador.", nickname = "login")
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED)
    @Valid
    public RSAdministrador login(@Param("nombre") String nombre, @Param("clave") String clave) throws DbModelException {
        Administrador administrador = null; //administradorDAO.findAll(PageRequest.of(0, 1)).getContent().get(0);
        if (administrador != null) {
            Authentication request = new UsernamePasswordAuthenticationToken(nombre, encoder.encode(clave));
            SecurityContextHolder.getContext().setAuthentication(request);
            return administradorConverter.convert(administrador);
        } else {
            throw new DbModelException("Datos de login incorrectos", null);
        }
    }
}
