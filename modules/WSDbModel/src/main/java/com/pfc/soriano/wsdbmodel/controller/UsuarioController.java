package com.pfc.soriano.wsdbmodel.controller;

import com.pfc.soriano.wsdbmodel.Consulta;
import com.pfc.soriano.wsdbmodel.Utils;
import com.pfc.soriano.wsdbmodel.dao.MunicipioDAO;
import com.pfc.soriano.wsdbmodel.dao.UsuarioDAO;
import com.pfc.soriano.wsdbmodel.entity.Municipio;
import com.pfc.soriano.wsdbmodel.entity.Usuario;
import com.pfc.soriano.wsdbmodel.exception.DbModelException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "WSDbModel/usuario")
public class UsuarioController {

    @Autowired
    UsuarioDAO usuarioDAO;

    @Autowired
    MunicipioDAO municipioDAO;

    @Autowired
    EntityManager em;

    @RequestMapping(value = "findByEmailAndClave", method = RequestMethod.POST)
    @ResponseBody
    public Usuario findByEmailAndClave(@Param("email") String email, @Param("clave") String clave) {
        return usuarioDAO.findByEmailAndClave(email, clave);
    }

    @RequestMapping(value = "findById", method = RequestMethod.POST)
    @ResponseBody
    public Optional<Usuario> findById(@Param("id") Long id) {
        return usuarioDAO.findById(id);
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public Usuario register(@RequestBody Usuario usuario) throws DbModelException {
        try {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            usuario.setClave(encoder.encode(usuario.getClave()));
            return usuarioDAO.saveAndFlush(usuario);
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
    public Usuario login(@Param("email") String email, @Param("clave") String clave) throws DbModelException {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        Usuario usuario = usuarioDAO.findByEmailAndClave(email, encoder.encode(clave));
        if (usuario != null) {
            if (usuario.getEstado() == -1L) {
                throw new DbModelException("El usuario está desactivado", null);
            } else {
                Authentication request = new UsernamePasswordAuthenticationToken(email, encoder.encode(clave));
                SecurityContextHolder.getContext().setAuthentication(request);
                return usuario;
            }
        } else {
            throw new DbModelException("Datos de autenticación incorrectos", null);
        }
    }

    @RequestMapping(value = "changeClave", method = RequestMethod.POST)
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

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public void delete(@Param("id") Long id) {
        Optional<Usuario> usuario = usuarioDAO.findById(id);
        if(usuario.isPresent()){
            usuario.get().setEstado(0);
            usuarioDAO.saveAndFlush(usuario.get());
        }
    }

    @RequestMapping(value = "filter", method = RequestMethod.POST)
    @ResponseBody
    public Collection<Usuario> filter(
            @Param("id") Long id,
            @Param("nombre") String nombre,
            @Param("email") String email,
            @Param("estado") Long estado,
            @Param("provincia") Long provincia,
            @Param("municipio") Long municipio,
            @Param("pageNumber") Integer pageNumber,
            @Param("pageSize") Integer pageSize) {

        Consulta<Usuario> usrConsulta = generateFilter(id, nombre, email, estado, provincia, municipio);
        usrConsulta.addOrderBy("nombre", Consulta.OrderByType.ASC);
        return usrConsulta.getResultList(pageNumber, pageSize);
    }

    private Consulta<Usuario> generateFilter(Long id, String nombre, String email, Long estado, Long provinciaId, Long municipioId) {

        Consulta<Usuario> usrConsulta = new Consulta<>(em, Usuario.class);
        if (id != null) {
            usrConsulta.addCriteria(Consulta.CriteriaType.EQUAL, "id", id);
        }
        if (!nombre.isEmpty()) {
            usrConsulta.addCriteria(Consulta.CriteriaType.LIKE, "nombre", "%" + nombre + "%");
        }
        if (!email.isEmpty()) {
            usrConsulta.addCriteria(Consulta.CriteriaType.LIKE, "email", "%" + email + "%");
        }
        usrConsulta.addCriteria(Consulta.CriteriaType.EQUAL, "estado", estado);
        if (provinciaId != -1) {
            if (municipioId != -1) {
                usrConsulta.addCriteria(Consulta.CriteriaType.EQUAL, "municipio", municipioId);
            } else {
                List<Long> lMunicipio = new ArrayList<>();
                for (Municipio municipio : municipioDAO.findByProvinciaId(provinciaId)) {
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

    @RequestMapping(value = "countFiltered", method = RequestMethod.POST)
    @ResponseBody
    public Long countFiltered(
            @Param("id") Long id,
            @Param("nombre") String nombre,
            @Param("email") String email,
            @Param("estado") Long estado,
            @Param("provincia") Long provincia,
            @Param("municipio") Long municipio) {

        Consulta<Usuario> usrConsulta = generateFilter(id, nombre, email, estado, provincia, municipio);
        return usrConsulta.totalResults();
    }
}
