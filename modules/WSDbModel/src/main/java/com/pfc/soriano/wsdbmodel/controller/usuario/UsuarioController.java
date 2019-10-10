package com.pfc.soriano.wsdbmodel.controller.usuario;

import com.pfc.soriano.wsdbmodel.Utils;
import com.pfc.soriano.wsdbmodel.entity.Usuario;
import com.pfc.soriano.wsdbmodel.exception.DbModelException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "usuario")
@Api(tags = {"Usuario"})
@RestController
public class UsuarioController {

    @Autowired
    PasswordEncoder encoder;

//    @Autowired
//    UsuarioDAO usuarioDAO;
//
//    @Autowired
//    ProvinciaDAO provinciaDAO;
//    @Autowired
//    MunicipioDAO municipioDAO;
    @Autowired
    Converter<Usuario, RSUsuario> usuarioConverter;
    @Autowired
    Converter<RSUsuarioRequest, Usuario> rsUsuarioConverter;

    @RequestMapping(value = "findById", method = RequestMethod.POST)
    @ResponseBody
    public Optional<Usuario> findById(@Param("id") Long id) {
        return null; //usuarioDAO.findById(id);
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    @Valid
    public RSUsuario register(@RequestBody RSUsuarioRequest usuarioRequest) throws DbModelException {
        try {
            Usuario usuario = rsUsuarioConverter.convert(usuarioRequest);
            //usuario = usuarioDAO.save(usuario);
            return usuarioConverter.convert(usuario);
        } catch (Throwable ex) {
            if (ex instanceof NonTransientDataAccessException) {
                NonTransientDataAccessException aux = (NonTransientDataAccessException) ex;
                throw new DbModelException(aux.getMostSpecificCause().getMessage(), ex);
            }
            throw new DbModelException(ex.getLocalizedMessage(), ex);
        }
    }

    @ApiOperation(value = "Login de usuario.", nickname = "login")
    @RequestMapping(method = RequestMethod.POST)
    @Valid
    public RSUsuario login(@Param("email") String email, @Param("clave") String clave) throws DbModelException {
        Usuario usuario = null; //usuarioDAO.findByEmailAndClave(email, encoder.encode(clave));
        if (usuario != null) {
            if (UsuarioEstado.BORRADO.equals(usuario.getEstado())) {
                throw new DbModelException("El usuario está desactivado", null);
            } else {
                Authentication request = new UsernamePasswordAuthenticationToken(email, encoder.encode(clave));
                SecurityContextHolder.getContext().setAuthentication(request);
                return usuarioConverter.convert(usuario);
            }
        } else {
            throw new DbModelException("Datos de autenticación incorrectos", null);
        }
    }

    @RequestMapping(value = "changeClave", method = RequestMethod.PUT)
    @ResponseBody
    public Usuario changeClave(@Param("email") String email, @Param("clave") String clave) {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            Usuario usuario = null; //usuarioDAO.findByEmail(email);
            usuario.setClave(encoder.encode(clave));
            return null;//usuarioDAO.save(usuario);
        } else {
            return null;
        }
    }

    @RequestMapping(value = "addFoto", method = RequestMethod.POST)
    @ResponseBody
    public void addFoto(@Param("id") Long id, @Param("foto") byte[] foto, @Param("fotoNombre") String fotoNombre) throws DbModelException {
        Utils.createImage(id, fotoNombre, foto, Utils.EEntityType.USUARIO, true);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public RSUsuario delete(@Param("id") Long id) {
        RSUsuario result = null;
        Optional<Usuario> usuario = null; //usuarioDAO.findById(id);
        if (usuario.isPresent()) {
            usuario.get().setEstado(UsuarioEstado.BORRADO);
            Usuario usuarioDeleted = null; //usuarioDAO.save(usuario.get());
            result = usuarioConverter.convert(usuarioDeleted);
        }
        return result;
    }

}
