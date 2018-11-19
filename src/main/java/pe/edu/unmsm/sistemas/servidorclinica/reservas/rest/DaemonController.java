/**
 * RQ- Mantenimiento de las Reservas de Cita
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.reservas.rest;

import com.fasterxml.jackson.databind.JsonNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.unmsm.sistemas.servidorclinica.reservas.negocio.RecordatoriosBusiness;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.negocio.ReservasBusiness;
import pe.edu.unmsm.sistemas.servidorclinica.utils.RespuestaSimple;

@RestController
@RequestMapping("/daemon")
public class DaemonController {

    private static final Logger log = LoggerFactory.getLogger(DaemonController.class);

    @Autowired
    private ReservasBusiness reservasBusiness;

    @Autowired
    private RecordatoriosBusiness recordatoriosBusiness;

    @GetMapping(value = "/cincomin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> daemon5MinEndpoint() {
        log.trace("Entrando a daemon5MinEndpoint ...");

        log.info("Iniciando envio de recordatorios del dia anterior ...");
        recordatoriosBusiness.enviarRecordatoriosDiaAnterior();

        log.info("Iniciando movida de reservas completadas ...");
        reservasBusiness.moverReservasCompletadas();
        try {
        	return new ResponseEntity<JsonNode>(HttpStatus.OK);
		} catch (Exception e) {
			RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("Error en el recordatorio");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @GetMapping(value = "/diariosieteam", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> daemonDiario7amEndpoint() {
        log.trace("Entrando a daemonDiario7amEndpoint ...");

        log.info("Iniciando envio de recordatorios del mismo dia ...");
        recordatoriosBusiness.enviarRecordatoriosMismoDia();
        try {
        	return new ResponseEntity<JsonNode>(HttpStatus.OK);
		} catch (Exception e) {
			RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("Error en el recordatorio");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
}