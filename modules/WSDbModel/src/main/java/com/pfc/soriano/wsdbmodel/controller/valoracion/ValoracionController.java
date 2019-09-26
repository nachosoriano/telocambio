package com.pfc.soriano.wsdbmodel.controller.valoracion;

import com.pfc.soriano.wsdbmodel.Consulta;
import com.pfc.soriano.wsdbmodel.dao.ValoracionDAO;
import com.pfc.soriano.wsdbmodel.entity.Valoracion;
import io.swagger.annotations.Api;
import java.util.Collection;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "valoracion")
@Api(tags = {"Valoracion"})
@RestController
public class ValoracionController {

    @Autowired
    ValoracionDAO valoracionDAO;

    @Autowired
    EntityManager em;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Valoracion createValoracion(@RequestBody Valoracion valoracion) {
        Valoracion result = valoracionDAO.findByUsuarioOrigenIdAndUsuarioDestinoId(valoracion.getUsuarioOrigen().getId(), valoracion.getUsuarioDestino().getId());
        if (result == null) {
            return valoracionDAO.saveAndFlush(valoracion);
        } else {
            valoracion.setId(result.getId());
            return valoracionDAO.saveAndFlush(valoracion);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @Valid
    public Collection<Valoracion> filter(
            @Param("usuarioOrigenId") Optional<Long> usuarioOrigenId,
            @Param("usuarioDestinoId") Optional<Long> usuarioDestinoId,
            @Param("pageNumber") Optional<Integer> pageNumber,
            @Param("pageSize") Optional<Integer> pageSize) {

        Consulta<Valoracion> valConsulta = generateFilter(usuarioOrigenId, usuarioDestinoId);
        valConsulta.addOrderBy("id", Consulta.OrderByType.DESC);
        return valConsulta.getResultList(pageNumber.orElse(0), pageSize.orElse(10));
    }

    private Consulta<Valoracion> generateFilter(Optional<Long> usuarioOrigenId, Optional<Long> usuarioDestinoId) {

        Consulta<Valoracion> valConsulta = new Consulta<>(em, Valoracion.class);
        if (usuarioOrigenId.isPresent()) {
            valConsulta.addCriteria(Consulta.CriteriaType.EQUAL, "usuarioOrigen", usuarioOrigenId.get());
        }
        if (usuarioDestinoId.isPresent()) {
            valConsulta.addCriteria(Consulta.CriteriaType.EQUAL, "usuarioDestino", usuarioDestinoId.get());
        }
        return valConsulta;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@Param("id") Long id) {
        valoracionDAO.deleteById(id);
    }
}
