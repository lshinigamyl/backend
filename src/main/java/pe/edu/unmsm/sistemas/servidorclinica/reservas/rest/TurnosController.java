/**
 * RQ- Mantenimiento de las Reservas de Cita
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.reservas.rest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.unmsm.sistemas.servidorclinica.reservas.domain.RespuestaTurnos;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.service.TurnosService;
import pe.edu.unmsm.sistemas.servidorclinica.utils.FechaHoraUtils;
import pe.edu.unmsm.sistemas.servidorclinica.utils.RespuestaSimple;

@RestController
@RequestMapping("/api/turnos")
public class TurnosController {

    private static final Logger log = LoggerFactory.getLogger(TurnosController.class);

    @Autowired
    private TurnosService turnosService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTurnosEndpoint(@RequestParam("fecha") String fechaString,
            @RequestParam(value = "especialidad", required = false) String idEspecialidad,
            @RequestParam(value = "medico", required = false) String idMedico,
            @RequestParam(value = "horainicio", required = false) String inicioString,
            @RequestParam(value = "horafin", required = false) String finString) {

        if (idEspecialidad == null && idMedico == null) {
            RespuestaSimple respuesta = new RespuestaSimple();
            respuesta.setMensaje("Debe enviar al menos una especialidad o medico");
            return new ResponseEntity<RespuestaSimple>(respuesta, HttpStatus.BAD_REQUEST);
        }

        if (idMedico != null) {
            idEspecialidad = null;
        }

        log.info("Buscando turnos disponible\nidEspecialidad: {} - idMedico: {} - fecha: {}", idEspecialidad, idMedico,
                fechaString);

        LocalDate fecha = FechaHoraUtils.parseFecha(fechaString);
        LocalDate ahora = LocalDate.now(ZoneId.of("-05:00"));

        if (!fecha.isAfter(ahora)) {
            log.error("La fecha es anterior o igual al dia de hoy");
            RespuestaSimple respuesta = new RespuestaSimple();
            respuesta.setMensaje("No se creo la reserva");
            return new ResponseEntity<RespuestaSimple>(respuesta, HttpStatus.BAD_REQUEST);
        }

        LocalTime inicio = inicioString == null ? null : FechaHoraUtils.parseHora(inicioString);
        LocalTime fin = finString == null ? null : FechaHoraUtils.parseHora(finString);

        return new ResponseEntity<RespuestaTurnos>(
                turnosService.getTurnosDisponibles(idEspecialidad, idMedico, fecha, inicio, fin), HttpStatus.OK);
    }
    
    @GetMapping(value="/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTurnosEndpoint(@RequestParam("fecha") String fechaString,
            @RequestParam(value = "medico", required = false) String idMedico,
            @RequestParam(value = "horainicio", required = false) String inicioString,
            @RequestParam(value = "horafin", required = false) String finString) {

        if (idMedico == null) {
            RespuestaSimple respuesta = new RespuestaSimple();
            respuesta.setMensaje("Debe enviar al menos un medico");
            return new ResponseEntity<RespuestaSimple>(respuesta, HttpStatus.BAD_REQUEST);
        }

        log.info("Buscando turnos disponible\nidMedico: {} - fecha: {}", idMedico,
                fechaString);

        LocalDate fecha = FechaHoraUtils.parseFecha(fechaString);
        LocalDate ahora = LocalDate.now(ZoneId.of("-05:00"));

        if (!fecha.isAfter(ahora)) {
            log.error("La fecha es anterior o igual al dia de hoy");
            RespuestaSimple respuesta = new RespuestaSimple();
            respuesta.setMensaje("No se creo la reserva");
            return new ResponseEntity<RespuestaSimple>(respuesta, HttpStatus.BAD_REQUEST);
        }

        LocalTime inicio = inicioString == null ? null : FechaHoraUtils.parseHora(inicioString);
        LocalTime fin = finString == null ? null : FechaHoraUtils.parseHora(finString);

        return new ResponseEntity<RespuestaTurnos>(
                turnosService.getTurnosDisponibles(idMedico, fecha, inicio, fin), HttpStatus.OK);
    }
}