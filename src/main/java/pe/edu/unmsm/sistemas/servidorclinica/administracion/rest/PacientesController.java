/**
 * RQ- Mantenimiento de los Pacientes
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Paciente;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Usuario;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.service.PacientesService;
import pe.edu.unmsm.sistemas.servidorclinica.utils.Constantes;
import pe.edu.unmsm.sistemas.servidorclinica.utils.ResponseMessage;
import pe.edu.unmsm.sistemas.servidorclinica.utils.RespuestaSimple;

@RestController
@RequestMapping("/api/pacientes")
public class PacientesController {

    private static final Logger log = LoggerFactory.getLogger(PacientesController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PacientesService pacientesService;

    @GetMapping(value = "/{id_paciente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPacienteEndpoint(@PathVariable("id_paciente") String idPaciente) {
        log.info("Buscando paciente con id {}", idPaciente);
        Paciente paciente = pacientesService.getPaciente(idPaciente);
        if (paciente != null) {
            try {
                log.info("Paciente encontrado\n{}", objectMapper.writeValueAsString(paciente));
            } catch (JsonProcessingException e) {
                log.error("No se pudo serializar el paciente", e);
            }
            return new ResponseEntity<Paciente>(paciente, HttpStatus.OK);
        } else {
            log.info("No se encontro el paciente");
            RespuestaSimple res = new RespuestaSimple();
            res.setMensaje("No se encontro el paciente");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crearPacienteEndpoint(@RequestBody Paciente paciente) {

        //paciente.setIdPaciente(idPaciente);
        try {
            log.info("Paciente a crear\n{}", objectMapper.writeValueAsString(paciente));
        } catch (JsonProcessingException e) {
            log.error("No se pudo serializar el paciente", e);
        }

        String idPacienteRespuesta = pacientesService.crearPaciente(paciente);

        RespuestaSimple res = new RespuestaSimple();
        HttpStatus status = null;
        if (!idPacienteRespuesta.equals("existe") && !idPacienteRespuesta.equals("dni")) {
            log.info("Paciente creado");
            res.setMensaje("Paciente creado");
            res.setTipoObjeto(Constantes.TIPO_PACIENTE);
            res.setId(idPacienteRespuesta);
            status = HttpStatus.OK;
        } else if(idPacienteRespuesta.equals("existe")) {
            log.error("El paciente se encuentra registrado");
            res.setMensaje("El paciente se encuentra registrado");
            status = HttpStatus.BAD_REQUEST;
        } else if (idPacienteRespuesta.equals("dni")) {
        	log.error("Debe ingresar un dni con 8 digitos");
            res.setMensaje("Debe ingresar un dni con 8 digitos");
            status = HttpStatus.BAD_REQUEST;
		}else {
			res.setMensaje("No se pudo realizar el registro");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
        
        return new ResponseEntity<RespuestaSimple>(res, status);
    }
    
    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listarPacientesEnpoint() {
        log.trace("Entrando a listarPacientesEnpoint ...");

        try {
        	return new ResponseEntity<ResponseMessage>(new ResponseMessage(pacientesService.listarPacientes()), HttpStatus.OK);
		} catch (Exception e) {
			RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("Error al traer la data");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @PostMapping(value = "/actualizar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarPacienteEndpoint(@RequestBody Paciente paciente) {
        try {
        	pacientesService.actualizarPaciente(paciente);
        	RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("Paciente Actualizado correctamente");
			return new ResponseEntity<RespuestaSimple>(res, HttpStatus.OK);
		} catch (Exception e) {
			RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("Error al traer la data");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginPacienteEndpoint(@RequestBody Paciente paciente) {
        log.info("Logeando usuario: ", paciente.getIdPaciente());
        
        Paciente objPaciente = pacientesService.loginPaciente(paciente);
        
        if (objPaciente != null) {
            try {
                log.info("Paciente encontrado\n{}", objectMapper.writeValueAsString(objPaciente));
            } catch (JsonProcessingException e) {
                log.error("No se pudo serializar el paciente", e);
            }
            return new ResponseEntity<Paciente>(objPaciente, HttpStatus.OK);
        } else {
            log.info("Paciente no registrado");
            RespuestaSimple res = new RespuestaSimple();
            res.setMensaje("Paciente no registrado");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping(value = "/password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarPasswordEndpoint(@RequestBody Paciente paciente) {
    	try {
            log.info("Actualizar password\n{}", objectMapper.writeValueAsString(paciente));
            
            pacientesService.cambiarPassword(paciente);
            
            RespuestaSimple res = new RespuestaSimple();
            res.setMensaje("Password actualizada");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.OK);
        } catch (JsonProcessingException e) {
        	log.info("No se pudo cambiar la password");
            RespuestaSimple res = new RespuestaSimple();
            res.setMensaje("No se pudo cambiar la password");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}