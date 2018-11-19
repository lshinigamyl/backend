/**
 * RQ- Carga Masiva
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.rest;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.unmsm.sistemas.servidorclinica.utils.RespuestaSimple;

@RestController
@RequestMapping("/archivos")
public class ArchivosController {

    private static final Logger log = LoggerFactory.getLogger(ArchivosController.class);
    
    @Autowired
	private MessageSource msg;  
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cargaMasivaEnpoint() throws Exception{
        log.trace("Entrando a cargaMasivaEnpoint ...");
        String respuesta = "";
        
        try {
        	return new ResponseEntity<String>(respuesta, HttpStatus.OK);
		} catch(Exception e){
			RespuestaSimple res = new RespuestaSimple();
            res.setMensaje("No se pudo realizar la carga masiva");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
    }
}