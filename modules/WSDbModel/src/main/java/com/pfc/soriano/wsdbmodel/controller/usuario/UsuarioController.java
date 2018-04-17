package com.pfc.soriano.wsdbmodel.controller.usuario;

import com.pfc.soriano.wsdbmodel.Consulta;
import com.pfc.soriano.wsdbmodel.Utils;
import com.pfc.soriano.wsdbmodel.dao.MunicipioDAO;
import com.pfc.soriano.wsdbmodel.dao.ProvinciaDAO;
import com.pfc.soriano.wsdbmodel.dao.UsuarioDAO;
import com.pfc.soriano.wsdbmodel.entity.Municipio;
import com.pfc.soriano.wsdbmodel.entity.Provincia;
import com.pfc.soriano.wsdbmodel.entity.Usuario;
import com.pfc.soriano.wsdbmodel.exception.DbModelException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    UsuarioDAO usuarioDAO;

    @Autowired
    ProvinciaDAO provinciaDAO;
    @Autowired
    MunicipioDAO municipioDAO;

    @Autowired
    Converter<Usuario, RSUsuario> usuarioConverter;
    @Autowired
    Converter<RSUsuarioRequest, Usuario> rsUsuarioConverter;

    @Autowired
    EntityManager em;

    @RequestMapping(value = "findById", method = RequestMethod.POST)
    @ResponseBody
    public Optional<Usuario> findById(@Param("id") Long id) {
        return usuarioDAO.findById(id);
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    @Valid
    public RSUsuario register(@RequestBody RSUsuarioRequest usuarioRequest) throws DbModelException {
        try {
            Usuario usuario = rsUsuarioConverter.convert(usuarioRequest);
            usuario = usuarioDAO.saveAndFlush(usuario);
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
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        Usuario usuario = usuarioDAO.findByEmailAndClave(email, encoder.encode(clave));
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
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            Usuario usuario = usuarioDAO.findByEmail(email);
            usuario.setClave(encoder.encode(clave));
            return usuarioDAO.saveAndFlush(usuario);
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
        Optional<Usuario> usuario = usuarioDAO.findById(id);
        if (usuario.isPresent()) {
            usuario.get().setEstado(UsuarioEstado.BORRADO);
            Usuario usuarioDeleted = usuarioDAO.saveAndFlush(usuario.get());
            result = usuarioConverter.convert(usuarioDeleted);
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Valid
    public Collection<Usuario> filter(
            @Param("id") Optional<Long> id,
            @Param("nombre") Optional<String> nombre,
            @Param("email") Optional<String> email,
            @Param("estado") Optional<Long> estado,
            @Param("provincia") Optional<Long> provincia,
            @Param("municipio") Optional<Long> municipio,
            @Param("pageNumber") Optional<Integer> pageNumber,
            @Param("pageSize") Optional<Integer> pageSize) {

        Consulta<Usuario> usrConsulta = generateFilter(id, nombre, email, estado, provincia, municipio);
        usrConsulta.addOrderBy("nombre", Consulta.OrderByType.ASC);
        if (pageNumber.isPresent() && pageSize.isPresent()) {
            return usrConsulta.getResultList(pageNumber.get(), pageSize.get());
        } else {
            return usrConsulta.getResultList();
        }
    }

    private Consulta<Usuario> generateFilter(Optional<Long> id, Optional<String> nombre, Optional<String> email, Optional<Long> estado, Optional<Long> provinciaId, Optional<Long> municipioId) {

        Consulta<Usuario> usrConsulta = new Consulta<>(em, Usuario.class);
        if (id.isPresent()) {
            usrConsulta.addCriteria(Consulta.CriteriaType.EQUAL, "id", id.get());
        }
        if (nombre.isPresent()) {
            usrConsulta.addCriteria(Consulta.CriteriaType.LIKE, "nombre", "%" + nombre.get() + "%");
        }
        if (email.isPresent()) {
            usrConsulta.addCriteria(Consulta.CriteriaType.LIKE, "email", "%" + email.get() + "%");
        }
        if (estado.isPresent()) {
            usrConsulta.addCriteria(Consulta.CriteriaType.EQUAL, "estado", estado.get());
        }
        if (provinciaId.isPresent()) {
            if (municipioId.isPresent()) {
                usrConsulta.addCriteria(Consulta.CriteriaType.EQUAL, "municipio", municipioId.get());
            } else {
                List<Long> lMunicipio = new ArrayList<>();
                Provincia provincia = provinciaDAO.getOne(provinciaId.get());
                for (Municipio municipio : municipioDAO.findByProvincia(provincia)) {
                    lMunicipio.add(municipio.getId());
                }
                if (lMunicipio.isEmpty()) {
                    usrConsulta.addCriteria(Consulta.CriteriaType.EQUAL, "municipio", -1);
                } else {
                    usrConsulta.addCriteria(Consulta.CriteriaType.IN, "municipio", lMunicipio);
                }
            }
        }
        return usrConsulta;
    }

    @RequestMapping(path = "/count", method = RequestMethod.GET)
    public Long countFiltered(
            @Param("id") Optional<Long> id,
            @Param("nombre") Optional<String> nombre,
            @Param("email") Optional<String> email,
            @Param("estado") Optional<Long> estado,
            @Param("provincia") Optional<Long> provincia,
            @Param("municipio") Optional<Long> municipio) {

        Consulta<Usuario> usrConsulta = generateFilter(id, nombre, email, estado, provincia, municipio);
        return usrConsulta.totalResults();
    }
}
