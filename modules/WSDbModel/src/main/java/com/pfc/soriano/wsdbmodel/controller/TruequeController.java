package com.pfc.soriano.wsdbmodel.controller;

import com.pfc.soriano.wsdbmodel.Consulta;
import com.pfc.soriano.wsdbmodel.Utils;
import com.pfc.soriano.wsdbmodel.dao.SubcategoriaDAO;
import com.pfc.soriano.wsdbmodel.dao.TruequeDAO;
import com.pfc.soriano.wsdbmodel.dao.UsuarioDAO;
import com.pfc.soriano.wsdbmodel.entity.Subcategoria;
import com.pfc.soriano.wsdbmodel.entity.Trueque;
import com.pfc.soriano.wsdbmodel.entity.Usuario;
import com.pfc.soriano.wsdbmodel.exception.DbModelException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "WSDbModel/trueque")
public class TruequeController {

    @Autowired
    TruequeDAO truequeDAO;
    @Autowired
    UsuarioDAO usuarioDAO;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    SubcategoriaDAO subcatDAO;

    @PersistenceContext
    EntityManager em;

    @RequestMapping(value = "filter", method = RequestMethod.POST)
    @ResponseBody
    public Collection<Trueque> filter(
            @Param("id") Long id,
            @Param("titulo") String titulo,
            @Param("descripcionOferta") String descripcionOferta,
            @Param("categoriaOferta") Long categoriaOferta,
            @Param("subcategoriaOferta") Long subcategoriaOferta,
            @Param("descripcionDemanda") String descripcionDemanda,
            @Param("categoriaDemanda") Long categoriaDemanda,
            @Param("subcategoriaDemanda") Long subcategoriaDemanda,
            @Param("usuarioId") Long usuarioId,
            @Param("estado") Long estado,
            @Param("pageNumber") Integer pageNumber,
            @Param("pageSize") Integer pageSize) {

        Consulta<Trueque> trqConsulta = generateFilter(id, titulo, descripcionOferta, categoriaOferta, subcategoriaOferta, descripcionDemanda, categoriaDemanda, subcategoriaDemanda, usuarioId, estado);
        trqConsulta.addOrderBy("id", Consulta.OrderByType.DESC);
        return trqConsulta.getResultList(pageNumber, pageSize);
    }

    private Consulta<Trueque> generateFilter(Long id, String titulo, String descripcionOferta, Long categoriaOferta,
            Long subcategoriaOferta, String descripcionDemanda, Long categoriaDemanda, Long subcategoriaDemanda,
            Long usuarioId, Long estado) {

        Consulta<Trueque> trqConsulta = new Consulta<>(em, Trueque.class);
        if (id != null) {
            trqConsulta.addCriteria(Consulta.CriteriaType.EQUAL, "id", id);
        } else {
            trqConsulta.addCriteria(Consulta.CriteriaType.EQUAL, "estado", estado == null ? 0 : estado);
            trqConsulta.addCriteria(Consulta.CriteriaType.LIKE, "titulo", "%" + titulo + "%");
            trqConsulta.addCriteria(Consulta.CriteriaType.LIKE, "descripcionOferta", "%" + descripcionOferta + "%");
            trqConsulta.addCriteria(Consulta.CriteriaType.LIKE, "descripcionDemanda", "%" + descripcionDemanda + "%");
            if (categoriaOferta != -1) {
                if (subcategoriaOferta != -1) {
                    trqConsulta.addCriteria(Consulta.CriteriaType.EQUAL, "subcatOferta", subcategoriaOferta);
                } else {
                    List<Long> lSubcatOferta = new ArrayList<>();
                    for (Subcategoria subcat : subcatDAO.findByCategoriaIdOrderByNombre(categoriaOferta)) {
                        lSubcatOferta.add(subcat.getId());
                    }
                    if (lSubcatOferta.isEmpty()) {
                        trqConsulta.addCriteria(Consulta.CriteriaType.EQUAL, "subcatOferta", -1);
                    } else {
                        trqConsulta.addCriteria(Consulta.CriteriaType.IN, "subcatOferta", lSubcatOferta);
                    }
                }
            }
            if (categoriaDemanda != -1) {
                if (subcategoriaDemanda != -1) {
                    trqConsulta.addCriteria(Consulta.CriteriaType.EQUAL, "subcatDemanda", subcategoriaDemanda);
                } else {
                    List<Long> lSubcatDemanda = new ArrayList<>();
                    for (Subcategoria subcat : subcatDAO.findByCategoriaIdOrderByNombre(categoriaDemanda)) {
                        lSubcatDemanda.add(subcat.getId());
                    }
                    if (lSubcatDemanda.isEmpty()) {
                        trqConsulta.addCriteria(Consulta.CriteriaType.EQUAL, "subcatDemanda", -1);
                    } else {
                        trqConsulta.addCriteria(Consulta.CriteriaType.IN, "subcatDemanda", lSubcatDemanda);
                    }
                }
            }
            if (usuarioId != null && usuarioId != -1) {
                trqConsulta.addCriteria(Consulta.CriteriaType.EQUAL, "usuario", usuarioId);
            }
        }
        return trqConsulta;
    }

    @RequestMapping(value = "countFiltered", method = RequestMethod.POST)
    @ResponseBody
    public Long countFiltered(
            @Param("id") Long id,
            @Param("titulo") String titulo,
            @Param("descripcionOferta") String descripcionOferta,
            @Param("categoriaOferta") Long categoriaOferta,
            @Param("subcategoriaOferta") Long subcategoriaOferta,
            @Param("descripcionDemanda") String descripcionDemanda,
            @Param("categoriaDemanda") Long categoriaDemanda,
            @Param("subcategoriaDemanda") Long subcategoriaDemanda,
            @Param("usuarioId") Long usuarioId,
            @Param("estado") Long estado) {

        Consulta<Trueque> trqConsulta = generateFilter(id, titulo, descripcionOferta, categoriaOferta, subcategoriaOferta, descripcionDemanda, categoriaDemanda, subcategoriaDemanda, usuarioId, estado);
        return trqConsulta.totalResults();
    }

    @RequestMapping(value = "findById", method = RequestMethod.POST)
    @ResponseBody
    public Trueque findById(@Param("id") Long id) {
        return truequeDAO.findByIdAndEstado(id, 0L);
    }

    @RequestMapping(value = "createTrueque", method = RequestMethod.POST)
    @ResponseBody
    public Trueque createTrueque(@RequestBody Trueque trueque) {
        return truequeDAO.saveAndFlush(trueque);
    }

    @RequestMapping(value = "proponer", method = RequestMethod.POST)
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

    @RequestMapping(value = "addFoto", method = RequestMethod.POST)
    @ResponseBody
    public void addFoto(@Param("id") Long id, @Param("foto") byte[] foto, @Param("fotoNombre") String fotoNombre) throws DbModelException {
        Utils.createImage(id, fotoNombre, foto, Utils.EEntityType.TRUEQUE, false);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public void delete(@Param("id") Long id) {
        Optional<Trueque> trueque = truequeDAO.findById(id);
        if(trueque.isPresent()) {
            trueque.get().setEstado((trueque.get().getEstado() + 1L) * -1L);
            truequeDAO.saveAndFlush(trueque.get());
        }
    }

    @RequestMapping(value = "findByTitulo", method = RequestMethod.POST)
    @ResponseBody
    public Collection<Trueque> findByTitulo(@Param("titulo") String titulo) throws DbModelException {
        return truequeDAO.findByTitulo(titulo);
    }
}
