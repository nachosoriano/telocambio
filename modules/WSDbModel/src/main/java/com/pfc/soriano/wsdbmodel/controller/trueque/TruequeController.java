package com.pfc.soriano.wsdbmodel.controller.trueque;

import com.pfc.soriano.wsdbmodel.Consulta;
import com.pfc.soriano.wsdbmodel.Utils;
import com.pfc.soriano.wsdbmodel.dao.SubcategoriaDAO;
import com.pfc.soriano.wsdbmodel.dao.TruequeDAO;
import com.pfc.soriano.wsdbmodel.dao.UsuarioDAO;
import com.pfc.soriano.wsdbmodel.entity.Trueque;
import com.pfc.soriano.wsdbmodel.entity.Usuario;
import com.pfc.soriano.wsdbmodel.exception.DbModelException;
import io.swagger.annotations.Api;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "trueque")
@Api(tags = {"Trueque"})
@RestController
public class TruequeController {

    @Autowired
    TruequeDAO truequeDAO;
    @Autowired
    UsuarioDAO usuarioDAO;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    SubcategoriaDAO subcatDAO;

    @Autowired
    Converter<Trueque, RSTrueque> truequeConverter;
    @Autowired
    Converter<RSTruequeRequest, Trueque> rsTruequeConverter;

    @Autowired
    EntityManager em;

    @RequestMapping(method = RequestMethod.GET)
    @Valid
    public Collection<Trueque> filter(
            @Param("id") Optional<Long> id,
            @Param("titulo") Optional<String> titulo,
            @Param("descripcionOferta") Optional<String> descripcionOferta,
            @Param("categoriaOferta") Optional<Long> categoriaOferta,
            @Param("subcategoriaOferta") Optional<Long> subcategoriaOferta,
            @Param("descripcionDemanda") Optional<String> descripcionDemanda,
            @Param("categoriaDemanda") Optional<Long> categoriaDemanda,
            @Param("subcategoriaDemanda") Optional<Long> subcategoriaDemanda,
            @Param("usuarioId") Optional<Long> usuarioId,
            @Param("estado") Optional<Long> estado,
            @Param("pageNumber") Optional<Integer> pageNumber,
            @Param("pageSize") Optional<Integer> pageSize) {

        Consulta<Trueque> trqConsulta = generateFilter(id, titulo, descripcionOferta, categoriaOferta, subcategoriaOferta, descripcionDemanda, categoriaDemanda, subcategoriaDemanda, usuarioId, estado);
        trqConsulta.addOrderBy("id", Consulta.OrderByType.DESC);
        if (pageNumber.isPresent() && pageSize.isPresent()) {
            return trqConsulta.getResultList(pageNumber.get(), pageSize.get());
        } else {
            return trqConsulta.getResultList();
        }
    }

    private Consulta<Trueque> generateFilter(Optional<Long> id, Optional<String> titulo, Optional<String> descripcionOferta, Optional<Long> categoriaOferta,
                                             Optional<Long> subcategoriaOferta, Optional<String> descripcionDemanda, Optional<Long> categoriaDemanda, Optional<Long> subcategoriaDemanda,
                                             Optional<Long> usuarioId, Optional<Long> estado) {

        Consulta<Trueque> trqConsulta = new Consulta<>(em, Trueque.class);
        if (id.isPresent()) {
            trqConsulta.addCriteria(Consulta.CriteriaType.EQUAL, "id", id.get());
        } else {
            trqConsulta.addCriteria(Consulta.CriteriaType.EQUAL, "estado", estado.isPresent() ? estado.get() : 0);
            if (titulo.isPresent()) {
                trqConsulta.addCriteria(Consulta.CriteriaType.LIKE, "titulo", "%" + titulo.get() + "%");
            }
            if (descripcionOferta.isPresent()) {
                trqConsulta.addCriteria(Consulta.CriteriaType.LIKE, "descripcionOferta", "%" + descripcionOferta.get() + "%");
            }
            if (descripcionDemanda.isPresent()) {
                trqConsulta.addCriteria(Consulta.CriteriaType.LIKE, "descripcionDemanda", "%" + descripcionDemanda.get() + "%");
            }
            if (categoriaOferta.isPresent()) {
                if (subcategoriaOferta.isPresent()) {
                    trqConsulta.addCriteria(Consulta.CriteriaType.EQUAL, "subcatOferta", subcategoriaOferta.get());
                } else {
                    List<Long> lSubcatOferta = new ArrayList<>();
                    subcatDAO.findByCategoriaIdOrderByNombre(categoriaOferta.get()).forEach((subcat) -> {
                        lSubcatOferta.add(subcat.getId());
                    });
                    if (lSubcatOferta.isEmpty()) {
                        trqConsulta.addCriteria(Consulta.CriteriaType.EQUAL, "subcatOferta", -1);
                    } else {
                        trqConsulta.addCriteria(Consulta.CriteriaType.IN, "subcatOferta", lSubcatOferta);
                    }
                }
            }
            if (categoriaDemanda.isPresent()) {
                if (subcategoriaDemanda.isPresent()) {
                    trqConsulta.addCriteria(Consulta.CriteriaType.EQUAL, "subcatDemanda", subcategoriaDemanda.get());
                } else {
                    List<Long> lSubcatDemanda = new ArrayList<>();
                    subcatDAO.findByCategoriaIdOrderByNombre(categoriaDemanda.get()).forEach((subcat) -> {
                        lSubcatDemanda.add(subcat.getId());
                    });
                    if (lSubcatDemanda.isEmpty()) {
                        trqConsulta.addCriteria(Consulta.CriteriaType.EQUAL, "subcatDemanda", -1);
                    } else {
                        trqConsulta.addCriteria(Consulta.CriteriaType.IN, "subcatDemanda", lSubcatDemanda);
                    }
                }
            }
            if (usuarioId.isPresent()) {
                trqConsulta.addCriteria(Consulta.CriteriaType.EQUAL, "usuario", usuarioId.get());
            }
        }
        return trqConsulta;
    }

    @RequestMapping(path = "/count", method = RequestMethod.GET)
    public Long countFiltered(
            @Param("id") Optional<Long> id,
            @Param("titulo") Optional<String> titulo,
            @Param("descripcionOferta") Optional<String> descripcionOferta,
            @Param("categoriaOferta") Optional<Long> categoriaOferta,
            @Param("subcategoriaOferta") Optional<Long> subcategoriaOferta,
            @Param("descripcionDemanda") Optional<String> descripcionDemanda,
            @Param("categoriaDemanda") Optional<Long> categoriaDemanda,
            @Param("subcategoriaDemanda") Optional<Long> subcategoriaDemanda,
            @Param("usuarioId") Optional<Long> usuarioId,
            @Param("estado") Optional<Long> estado) {

        Consulta<Trueque> trqConsulta = generateFilter(id, titulo, descripcionOferta, categoriaOferta, subcategoriaOferta, descripcionDemanda, categoriaDemanda, subcategoriaDemanda, usuarioId, estado);
        return trqConsulta.totalResults();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Trueque createTrueque(@RequestBody Trueque trueque) {
        return truequeDAO.saveAndFlush(trueque);
    }

    @RequestMapping(value = "/proponer", method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus proponer(@Param("truequeId") Long truequeId, @Param("emailFrom") String emailFrom, @Param("texto") String texto) throws DbModelException {
        try {
            //Obtener trueque
            Optional<Trueque> trueque = truequeDAO.findById(truequeId);
            if(trueque.isPresent()) {
                Usuario usuarioTo = trueque.get().getUsuario();

                MimeMessage message = Utils.createEmail(emailFrom, usuarioTo.getEmail(), "[TeLoCambio] - Propuesta para (" + trueque.get().getTitulo() + ")", generaTexto(trueque.get(), emailFrom, texto));
                mailSender.send(message);

                return HttpStatus.CREATED;
            }
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (MessagingException ex) {
            throw new DbModelException("Error enviando propuesta", ex);
        }
    }

    private String generaTexto(Trueque trueque, String emailFrom, String texto) {
        String result = "";
        Usuario usuarioFrom = usuarioDAO.findByEmail(emailFrom);
        result += "Datos del trueque:\n" + trueque.toString();
        result += "Datos del usuario que realiza la propuesta:\n" + usuarioFrom.toString();
        result += "Propuesta realizada:\n";
        result += "\t\"" + texto + "\"\n\n";
        result += "Reciba un cordial saludo,\nEl equipo de TeLoCambio";

        return result;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public void addFoto(@Param("id") Long id, @Param("foto") byte[] foto, @Param("fotoNombre") String fotoNombre) throws DbModelException {
        Utils.createImage(id, fotoNombre, foto, Utils.EEntityType.TRUEQUE, false);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@Param("id") Long id) {
        Optional<Trueque> trueque = truequeDAO.findById(id);
        if(trueque.isPresent()) {
            trueque.get().setEstado(TruequeEstado.BORRADO);
            truequeDAO.saveAndFlush(trueque.get());
        }
    }

}
