package pe.edu.unmsm.sistemas.servidorclinica.administracion.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.SedesRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Especialidad;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.RespuestaEspecialidades;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Sede;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.service.EspecialidadesService;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.service.SedesService;
import pe.edu.unmsm.sistemas.servidorclinica.utils.ResponseMessage;
import pe.edu.unmsm.sistemas.servidorclinica.utils.RespuestaSimple;

@RestController
@RequestMapping("/api/sedes")
public class SedeController {
	
	private static final Logger log = LoggerFactory.getLogger(SedeController.class);
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private SedesService sedesService;
    
    @GetMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSedeEnpoint(@RequestParam(value = "idSede", required = true) String idSede) {
        log.trace("Entrando a getSedeEnpoint ...");

        Sede sede = sedesService.getSede_Especialidades(idSede);

        if (sede != null) {
            log.info("Buscando la sede con codigo {}...", idSede);
            return new ResponseEntity<Sede>(sede, HttpStatus.OK);
        } else {
            log.info("No se encontro la sede");
            RespuestaSimple res = new RespuestaSimple();
            res.setMensaje("No se encontro la sede");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping(value = "/buscarvm", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSedeVmEnpoint(@RequestParam(value = "idSede", required = true) String idSede) {
        log.trace("Entrando a getEspecialidadEnpoint ...");

        Sede sede = sedesService.getSede(idSede);

        if (sede != null) {
            log.info("Buscando la sede con codigo {}...", idSede);
            return new ResponseEntity<Sede>(sede, HttpStatus.OK);
        } else {
            log.info("No se encontro la sede");
            RespuestaSimple res = new RespuestaSimple();
            res.setMensaje("No se encontro la sede");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping(value = "/buscarxNombre", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSedexNombreEnpoint(@RequestParam(value = "nombre", required = true) String nombre) {
        log.trace("Entrando a getSedexNombreEnpoint ...");
        log.info("El parametro es: "+nombre);

        List<Sede> lstSedes = sedesService.buscarSedes_EspecialidadesxNombre(nombre);

        if (lstSedes != null) {
            log.info("Buscando la sede con codigo {}...", lstSedes);
            return new ResponseEntity<ResponseMessage>(new ResponseMessage(lstSedes), HttpStatus.OK);
        } else {
            log.info("No se encontro la sede");
            RespuestaSimple res = new RespuestaSimple();
            res.setMensaje("Error al traer la informacion");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping(value = "/buscarxNombrevm", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSedexNombreVmEnpoint(@RequestParam(value = "nombre", required = true) String nombre) {
    	 log.trace("Entrando a getSedexNombreVmEnpoint ...");
    	 log.info("El parametro es: "+nombre);

         List<Sede> lstSedes = sedesService.buscarSedesxNombre(nombre);

         if (lstSedes != null) {
             log.info("Buscando la sede con codigo {}...", lstSedes);
             return new ResponseEntity<ResponseMessage>(new ResponseMessage(lstSedes), HttpStatus.OK);
         } else {
             log.info("No se encontro la sede");
             RespuestaSimple res = new RespuestaSimple();
             res.setMensaje("Error al traer la informacion");
             return new ResponseEntity<RespuestaSimple>(res, HttpStatus.NOT_FOUND);
         }
    }
    
    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listarSedesEnpoint() {
        log.trace("Entrando a listarSedesEnpoint ...");

        try {
        	return new ResponseEntity<ResponseMessage>(new ResponseMessage(sedesService.listarSedes_Especialidades()), HttpStatus.OK);
		} catch (Exception e) {
			RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("Error al traer la data");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @GetMapping(value = "/listarvm", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listarSedesVmEnpoint() {
        log.trace("Entrando a listarSedesVmEnpoint ...");

        try {
        	return new ResponseEntity<ResponseMessage>(new ResponseMessage(sedesService.listarSedes()), HttpStatus.OK);
		} catch (Exception e) {
			RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("Error al traer la data");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @PostMapping(value = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crearSedeEndpoint(@RequestBody Sede sede) {

        try {
            log.info("Sede a crear\n{}", objectMapper.writeValueAsString(sede));
        } catch (JsonProcessingException e) {
            log.error("No se pudo serializar la sede", e);
        }

        String idSede = sedesService.crearSede(sede);

        RespuestaSimple res = new RespuestaSimple();
        HttpStatus status = null;
        if (idSede != null) {
            log.info("Sede creada");
            res.setMensaje("Sede creada");
            res.setId(idSede);
            status = HttpStatus.OK;
        } else {
            log.error("No se pudo crear la sede");
            res.setMensaje("No se pudo crear la sede");
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<RespuestaSimple>(res, status);
    }
    
    @PostMapping(value = "/actualizar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarSedeEndpoint(@RequestBody Sede sede) {
        try {
        	sedesService.actualizarSede(sede);
        	RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("Sede actualizada correctamente");
			return new ResponseEntity<RespuestaSimple>(res, HttpStatus.OK);
		} catch (Exception e) {
			RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("No se pudo actualizar la sede");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @PostMapping(value = "/eliminar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminarSedeEndpoint(@RequestBody Sede sede) {
        try {
        	sedesService.eliminarSede(sede);
        	RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("Sede eliminada correctamente");
			return new ResponseEntity<RespuestaSimple>(res, HttpStatus.OK);
		} catch (Exception e) {
			RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("No se pudo eliminar la sede");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @PostMapping(value = "/activar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activarSedeEndpoint(@RequestBody Sede sede) {
        try {
        	sedesService.darAlta(sede);
        	RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("Sede recuperada correctamente");
			return new ResponseEntity<RespuestaSimple>(res, HttpStatus.OK);
		} catch (Exception e) {
			RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("No se pudo recuperar la sede");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
}
