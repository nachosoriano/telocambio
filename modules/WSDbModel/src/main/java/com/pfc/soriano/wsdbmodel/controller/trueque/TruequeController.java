package com.pfc.soriano.wsdbmodel.controller.trueque;

import com.pfc.soriano.wsdbmodel.Utils;
import com.pfc.soriano.wsdbmodel.entity.Trueque;
import com.pfc.soriano.wsdbmodel.entity.Usuario;
import com.pfc.soriano.wsdbmodel.exception.DbModelException;
import io.swagger.annotations.Api;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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

//    @Autowired
//    TruequeDAO truequeDAO;
//    @Autowired
//    UsuarioDAO usuarioDAO;
    @Autowired
    JavaMailSender mailSender;
//    @Autowired
//    SubcategoriaDAO subcatDAO;

    @Autowired
    Converter<Trueque, RSTrueque> truequeConverter;
    @Autowired
    Converter<RSTruequeRequest, Trueque> rsTruequeConverter;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Trueque createTrueque(@RequestBody Trueque trueque) {
        return null; //return truequeDAO.save(trueque);
    }

    @RequestMapping(value = "/proponer", method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus proponer(@Param("truequeId") Long truequeId, @Param("emailFrom") String emailFrom, @Param("texto") String texto) throws DbModelException {
        try {
            //Obtener trueque
            Optional<Trueque> trueque = null; //truequeDAO.findById(truequeId);
            if (trueque.isPresent()) {
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
        Usuario usuarioFrom = null; //usuarioDAO.findByEmail(emailFrom);
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
        Optional<Trueque> trueque = null; //truequeDAO.findById(id);
        if (trueque.isPresent()) {
            trueque.get().setEstado(TruequeEstado.BORRADO);
            //truequeDAO.save(trueque.get());
        }
    }

}
