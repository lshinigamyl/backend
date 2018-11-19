/**
 * RQ- Mantenimiento de los Médicos
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.rest;

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

import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Medico;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.RespuestaMedicos;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.service.MedicosService;
import pe.edu.unmsm.sistemas.servidorclinica.utils.ResponseMessage;
import pe.edu.unmsm.sistemas.servidorclinica.utils.RespuestaSimple;

@RestController
@RequestMapping("/api/medicos")
public class MedicosController {

    private static final Logger log = LoggerFactory.getLogger(MedicosController.class);

    @Autowired
    private MedicosService medicosService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listarMedicosEnpoint(
            @RequestParam(value = "especialidad", required = false) String idEspecialidad) {
        log.trace("Entrando a listarMedicosEnpoint ...");
        log.info("Buscando medicos con especialidad {}", idEspecialidad);

        try {
        	return new ResponseEntity<RespuestaMedicos>(medicosService.listarMedicos(idEspecialidad), HttpStatus.OK);
		} catch (Exception e) {
			RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("Error al traer la data");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listarMedicosEnpoint() {
        log.trace("Entrando a listarMedicosEnpoint ...");

        try {
        	return new ResponseEntity<ResponseMessage>(new ResponseMessage(medicosService.listarMedicos()), HttpStatus.OK);
		} catch (Exception e) {
			RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("Error al traer la data");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @GetMapping(value = "/listarvm", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listarMedicosVmEnpoint() {
        log.trace("Entrando a listarMedicosEnpoint ...");

        try {
        	return new ResponseEntity<ResponseMessage>(new ResponseMessage(medicosService.listarMedicosVm()), HttpStatus.OK);
		} catch (Exception e) {
			RespuestaSimple res = new RespuestaSimple();
			res.setMensaje("Error al traer la data");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @GetMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarMedicoEnpoint(@RequestParam(value = "idMedico", required = true) String idMedico) {
        log.trace("Entrando a buscarMedicoEnpoint ...");

        try {
        	Medico medico = medicosService.getMedico(idMedico);
                	
            return new ResponseEntity<Medico>(medico, HttpStatus.OK);
		} catch (Exception e) {
			log.info("No se encontro el medico");
            RespuestaSimple res = new RespuestaSimple();
            res.setMensaje("No se encontro el medico");
            return new ResponseEntity<RespuestaSimple>(res, HttpStatus.NOT_FOUND);
		}
    }
}