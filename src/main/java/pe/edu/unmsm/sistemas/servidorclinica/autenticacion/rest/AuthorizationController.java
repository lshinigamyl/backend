/**
 * RQ- Autenticación de Usuario
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.autenticacion.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Usuario;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.rest.UsuarioController;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.service.PacientesService;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.service.UsuarioService;
import pe.edu.unmsm.sistemas.servidorclinica.utils.RespuestaSimple;

@RestController
@RequestMapping("/api/authorization")
public class AuthorizationController{
	
	private static final Logger log = LoggerFactory.getLogger(AuthorizationController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioService usuarioService;
    
    @PostMapping(value = "/password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginUsuarioEndpoint(@RequestBody Usuario usuario) {
        log.info("Logeando usuario: ", usuario.getDni());
        
        Usuario objUsuario = usuarioService.loginUsuario(usuario);
        
        if (objUsuario != null) {
            try {
                log.info("Usuario encontrado\n{}", objectMapper.writeValueAsString(usuario));
            } catch (JsonProcessingException e) {
                log.error("No se pudo serializar el usuario", e);
            }
            return new ResponseEntity<Usuario>(objUsuario, HttpStatus.OK);
        } else {
            log.info("No se encontro el usuario");
            RespuestaSimple res = new RespuestaSimple();
            res.setMensaje("No se encontro el usuario");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping(value = "/actualizar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarPasswordEndpoint(@RequestBody Usuario usuario) {
    	try {
            log.info("Actualizar password\n{}", objectMapper.writeValueAsString(usuario));
            
            usuarioService.cambiarPassword(usuario);
            
            RespuestaSimple res = new RespuestaSimple();
            res.setMensaje("Actualizar password");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.OK);
        } catch (JsonProcessingException e) {
        	log.info("No se pudo cambiar la password");
            RespuestaSimple res = new RespuestaSimple();
            res.setMensaje("No se pudo cambiar la password");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}