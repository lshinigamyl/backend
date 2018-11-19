/**
 * RQ- Mantenimiento de las Reservas de Cita
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.reservas.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.unmsm.sistemas.servidorclinica.reservas.domain.Reserva;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.domain.RespuestaReservas;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.service.ReservasService;
import pe.edu.unmsm.sistemas.servidorclinica.utils.Constantes;
import pe.edu.unmsm.sistemas.servidorclinica.utils.RespuestaSimple;

@RestController
@RequestMapping("/api/reservas")
public class ReservasController {

    private static final Logger log = LoggerFactory.getLogger(ReservasController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReservasService reservasService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crearReservaEnpoint(@RequestBody Reserva reserva) {
        log.trace("Entrando a crearReservaEnpoint ...");
        try {
            log.info("Reserva a crear\n{}", objectMapper.writeValueAsString(reserva));
        } catch (JsonProcessingException e) {
            log.error("No se pudo serializar la reserva", e);
        }

        String id = reservasService.crearReserva(reserva);
        RespuestaSimple respuesta = new RespuestaSimple();
        HttpStatus status = null;
        if (id != null) {
            log.info("Reserva creada");
            respuesta.setMensaje("Reserva creada con exito");
            respuesta.setTipoObjeto(Constantes.TIPO_RESERVA);
            respuesta.setId(id);
            status = HttpStatus.CREATED;
        } else {
            log.error("No se creo la reserva");
            respuesta.setMensaje("No se creo la reserva");
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<RespuestaSimple>(respuesta, status);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> reprogramarReservaEnpoint(@RequestBody Reserva reserva) {
        log.trace("Entrando a reprogramarReservaEnpoint ...");

        log.info("Reserva a reprogramar\n{}", reserva.getIdReserva());

        // verificar datos necesarios
        if (reserva.getIdReserva() == null || reserva.getFecha() == null || reserva.getInicio() == null
                || reserva.getFin() == null) {
            log.error("Faltan datos");
            RespuestaSimple respuesta = new RespuestaSimple();
            respuesta.setMensaje("No se puede reprogramar una reserva sin saber su ID o la nueva fecha y hora");
            return new ResponseEntity<RespuestaSimple>(respuesta, HttpStatus.BAD_REQUEST);
        }

        RespuestaSimple respuesta = new RespuestaSimple();
        HttpStatus status = null;
        if (reservasService.reprogramarReserva(reserva)) {
            log.info("Reserva reprogramada");
            respuesta.setMensaje("Reserva reprogramada con exito");
            respuesta.setTipoObjeto(Constantes.TIPO_RESERVA);
            respuesta.setId(reserva.getIdReserva());
            status = HttpStatus.OK;
        } else {
            log.error("No se reprogramo la reserva");
            respuesta.setMensaje("No se reprogramo la reserva");
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<RespuestaSimple>(respuesta, status);
    }
    
    
    // estado => Reservadas y Completadas
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listarReservasxIdPacienteEnpoint(@RequestParam("paciente") String idPaciente,
            @RequestParam(value = "estado", required = false) String estado) {
        log.trace("Entrando a listarReservasEnpoint ...");
        log.info("Buscando reservas del paciente con id {}", idPaciente);

        try {
        	return new ResponseEntity<RespuestaReservas>(reservasService.listarReservasxIdPaciente(idPaciente, estado), HttpStatus.OK);
		} catch (Exception e) {
			RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("Error al traer la data");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    // estado => Reservadas y Completadas
    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listarReservasxIdMedicoEnpoint(@RequestParam("idMedico") String idMedico,
            @RequestParam(value = "estado", required = false) String estado) {
        log.trace("Entrando a listarReservasEnpoint ...");
        log.info("Buscando reservas del paciente con id {}", idMedico);

        try {
        	return new ResponseEntity<RespuestaReservas>(reservasService.listarReservasxIdMedico(idMedico, estado), HttpStatus.OK);
		} catch (Exception e) {
			RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("Error al traer la data");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @DeleteMapping(value = "/{idreserva}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> anularReservaEnpoint(@PathVariable("idreserva") String idReserva) {
        RespuestaSimple respuesta = new RespuestaSimple();
        HttpStatus status = null;
        if (reservasService.anularReserva(idReserva)) {
            log.info("Reserva anulada");
            respuesta.setMensaje("Reserva anulada con exito");
            respuesta.setTipoObjeto(Constantes.TIPO_RESERVA);
            respuesta.setId(idReserva);
            status = HttpStatus.OK;
        } else {
            log.error("No se anulo la reserva");
            respuesta.setMensaje("No se anulo la reserva");
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<RespuestaSimple>(respuesta, status);
    }
}