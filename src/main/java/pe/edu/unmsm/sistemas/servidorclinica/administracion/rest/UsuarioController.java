package pe.edu.unmsm.sistemas.servidorclinica.administracion.rest;

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

import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Paciente;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.RespuestaEspecialidades;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Usuario;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.service.PacientesService;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.service.UsuarioService;
import pe.edu.unmsm.sistemas.servidorclinica.utils.Constantes;
import pe.edu.unmsm.sistemas.servidorclinica.utils.ResponseMessage;
import pe.edu.unmsm.sistemas.servidorclinica.utils.RespuestaSimple;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
	
	private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listarUsuariosEnpoint() {
        log.trace("Entrando a listarUsuariosEnpoint ...");

        try {
        	return new ResponseEntity<ResponseMessage>(new ResponseMessage(usuarioService.listarUsuarios()), HttpStatus.OK);
		} catch (Exception e) {
			RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("Error al traer la data");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
    @PostMapping(value = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crearUsuarioEndpoint(@RequestBody Usuario usuario) {

    	usuario.setIdUsuario(usuario.getDni());
    	
        try {
            log.info("Usuario a crear\n{}", objectMapper.writeValueAsString(usuario));
        } catch (JsonProcessingException e) {
            log.error("No se pudo serializar al usuario", e);
        }

        String idUsuarioRespuesta = usuarioService.crearUsuario(usuario).getIdUsuario();

        RespuestaSimple res = new RespuestaSimple();
        HttpStatus status = null;
        if (idUsuarioRespuesta != null) {
            log.info("Paciente creado");
            res.setMensaje("Paciente creado");
            res.setTipoObjeto(Constantes.TIPO_PACIENTE);
            res.setId(idUsuarioRespuesta);
            status = HttpStatus.CREATED;
        } else {
            log.error("No se creo el usuario");
            res.setMensaje("No se creo el usuario");
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<RespuestaSimple>(res, status);
    }
    
    @PostMapping(value = "/actualizar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarUsuarioEndpoint(@RequestBody Usuario usuario) {
        try {
            log.info("Usuario a actualizar\n{}", objectMapper.writeValueAsString(usuario));
            
            usuarioService.actualizarUsuario(usuario);
            
            RespuestaSimple res = new RespuestaSimple();
            res.setMensaje("Usuario actualizado");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.OK);
        } catch (JsonProcessingException e) {
        	log.info("No se pudo actualizar al usuario");
            RespuestaSimple res = new RespuestaSimple();
            res.setMensaje("No se pudo actualizar al usuario");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/buscar/{idUsuario}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsuarioEndpoint(@PathVariable("idUsuario") String idUsuario) {
        log.info("Buscando usuario con id {}", idUsuario);
        
        Usuario usuario = usuarioService.obtenerUsuario(idUsuario);
        
        if (usuario != null) {
            try {
                log.info("Usuario encontrado\n{}", objectMapper.writeValueAsString(usuario));
            } catch (JsonProcessingException e) {
                log.error("No se pudo serializar el usuario", e);
            }
            return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
        } else {
            log.info("No se encontro el usuario");
            RespuestaSimple res = new RespuestaSimple();
            res.setMensaje("No se encontro el usuario");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.NOT_FOUND);
        }
    }
}
