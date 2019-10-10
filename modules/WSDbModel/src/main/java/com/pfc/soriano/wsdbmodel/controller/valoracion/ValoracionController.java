package com.pfc.soriano.wsdbmodel.controller.valoracion;

import com.pfc.soriano.wsdbmodel.entity.Valoracion;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "valoracion")
@Api(tags = {"Valoracion"})
@RestController
public class ValoracionController {

//    @Autowired
//    ValoracionDAO valoracionDAO;
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Valoracion createValoracion(@RequestBody Valoracion valoracion) {
        return null;
//        Valoracion result = valoracionDAO.findByUsuarioOrigenIdAndUsuarioDestinoId(valoracion.getUsuarioOrigen().getId(), valoracion.getUsuarioDestino().getId());
//        if (result == null) {
//            return valoracionDAO.save(valoracion);
//        } else {
//            valoracion.setId(result.getId());
//            return valoracionDAO.save(valoracion);
//        }
    }

}
