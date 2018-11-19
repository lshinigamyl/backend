/**
 * RQ- Mantenimiento de las Especialidades
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Especialidad;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Paciente;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.RespuestaEspecialidades;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.service.EspecialidadesService;
import pe.edu.unmsm.sistemas.servidorclinica.utils.Constantes;
import pe.edu.unmsm.sistemas.servidorclinica.utils.ResponseMessage;
import pe.edu.unmsm.sistemas.servidorclinica.utils.RespuestaSimple;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadesController {

    private static final Logger log = LoggerFactory.getLogger(EspecialidadesController.class);
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private EspecialidadesService especialidadesService;
    
    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listarEspecialidadesEnpoint() {
        log.trace("Entrando a listarEspecialidadesEnpoint ...");

        try {
        	return new ResponseEntity<RespuestaEspecialidades>(especialidadesService.listarEspecialidades(), HttpStatus.OK);
		} catch (Exception e) {
			RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("Error al traer la data");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @GetMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEspecialidadEnpoint(@RequestParam(value = "idEspecialidad", required = true) String idEspecialidad) {
        log.trace("Entrando a getEspecialidadEnpoint ...");

        Especialidad especialidad = especialidadesService.getEspecialidad(idEspecialidad);

        if (especialidad != null) {
            log.info("Buscando la especialidad con codigo {}...", idEspecialidad);
            return new ResponseEntity<Especialidad>(especialidad, HttpStatus.OK);
        } else {
            log.info("No se encontro la especialidad");
            RespuestaSimple res = new RespuestaSimple();
            res.setMensaje("No se encontro la especialidad");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.NOT_FOUND);
        }

    }
    
    @GetMapping(value = "/buscarxNombre", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEspecialidadesxNombreEnpoint(@RequestParam(value = "nombre", required = true) String nombre) {
        log.trace("Entrando a getEspecialidadEnpoint ...");
        log.info("El parametro es: "+nombre);

        List<Especialidad> lstEspecialidades = especialidadesService.buscarEspecialidadesxNombre(nombre);

        if (nombre != null) {
            log.info("Buscando la especialidades x Nombre {}...", nombre);
            return new ResponseEntity<ResponseMessage>(new ResponseMessage(lstEspecialidades), HttpStatus.OK);
        } else {
            log.info("Error al traer la informacion");
            RespuestaSimple res = new RespuestaSimple();
            res.setMensaje("Error al traer la informacion");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping(value = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crearEspecialidadEndpoint(@RequestBody Especialidad especialidad) {
    	
        try {
            log.info("Especialidad a crear\n{}", objectMapper.writeValueAsString(especialidad));
        } catch (JsonProcessingException e) {
            log.error("No se pudo serializar la especialidad", e);
        }

        String idEspecialidadRespuesta = especialidadesService.crearEspecialidad(especialidad);

        RespuestaSimple res = new RespuestaSimple();
        HttpStatus status = null;
        if (idEspecialidadRespuesta != null) {
            log.info("Especialidad creada");
            res.setMensaje("Especialidad creada");
            res.setId(idEspecialidadRespuesta);
            status = HttpStatus.OK;
        } else {
            log.error("No se pudo crear la especialidad");
            res.setMensaje("No se pudo crear la especialidad");
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<RespuestaSimple>(res, status);
    }
    
    @PostMapping(value = "/actualizar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarEspecialidadEndpoint(@RequestBody Especialidad especialidad) {
        try {
        	especialidadesService.actualizarEspecialidad(especialidad);
        	RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("Especialidad actualizada correctamente");
			return new ResponseEntity<RespuestaSimple>(res, HttpStatus.OK);
		} catch (Exception e) {
			RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("No se pudo actualizar la especialidad");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @PostMapping(value = "/eliminar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminarEspecialidadEndpoint(@RequestBody Especialidad especialidad) {
        try {
        	especialidadesService.eliminarEspecialidad(especialidad.getIdEspecialidad());
        	RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("Especialidad eliminada correctamente");
			return new ResponseEntity<RespuestaSimple>(res, HttpStatus.OK);
		} catch (Exception e) {
			RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("No se pudo eliminar la especialidad");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @PostMapping(value = "/activar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activarEspecialidadEndpoint(@RequestBody Especialidad especialidad) {
        try {
        	especialidadesService.darAlta(especialidad.getIdEspecialidad());
        	RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("Especialidad recuperada correctamente");
			return new ResponseEntity<RespuestaSimple>(res, HttpStatus.OK);
		} catch (Exception e) {
			RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("No se pudo recuperar la especialidad");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
}