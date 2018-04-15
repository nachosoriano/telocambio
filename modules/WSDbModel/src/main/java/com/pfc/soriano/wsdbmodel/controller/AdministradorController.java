package com.pfc.soriano.wsdbmodel.controller;

import com.pfc.soriano.wsdbmodel.dao.AdministradorDAO;
import com.pfc.soriano.wsdbmodel.entity.Administrador;
import com.pfc.soriano.wsdbmodel.exception.DbModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "WSDbModel/administrador")
public class AdministradorController {

    @Autowired
    AdministradorDAO administradorDAO;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public Administrador register(@RequestBody Administrador administrador) throws DbModelException {
        try {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            administrador.setClave(encoder.encode(administrador.getClave()));
            return administradorDAO.saveAndFlush(administrador);
        } catch (Throwable ex) {
            if (ex instanceof NonTransientDataAccessException) {
                NonTransientDataAccessException aux = (NonTransientDataAccessException) ex;
                throw new DbModelException(aux.getMostSpecificCause().getMessage(), ex);
            }
            throw new DbModelException(ex.getLocalizedMessage(), ex);
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public Administrador login(@Param("nombre") String nombre, @Param("clave") String clave) throws DbModelException {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        Administrador administrador = administradorDAO.findByNombreAndClave(nombre, encoder.encode(clave));
        if (administrador != null) {
            Authentication request = new UsernamePasswordAuthenticationToken(nombre, encoder.encode(clave));
            SecurityContextHolder.getContext().setAuthentication(request);
            return administrador;
        } else {
            throw new DbModelException("Datos de login incorrectos", null);
        }
    }
}
