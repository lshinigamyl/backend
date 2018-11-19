/**
 * RQ- Mantenimiento de las Reservas de Cita
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.reservas.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.EspecialidadesRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.MedicosRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.PacientesRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.SedesRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Turno;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.dao.ReservasRepository;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.domain.Reserva;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.domain.RespuestaReservas;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.domain.RespuestaTurnos;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.service.EmailService;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.service.ReservasService;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.service.TurnosService;
import pe.edu.unmsm.sistemas.servidorclinica.utils.Constantes;
import pe.edu.unmsm.sistemas.servidorclinica.utils.FechaHoraUtils;

@Service
public class ReservasServiceImpl implements ReservasService {

    private static final Logger log = LoggerFactory.getLogger(ReservasServiceImpl.class);

    @Autowired
    private ReservasRepository reservasRepository;

    @Autowired
    private PacientesRepository pacientesRepository;

    @Autowired
    private EspecialidadesRepository especialidadesRepository;

    @Autowired
    private SedesRepository sedesRepository;

    @Autowired
    private MedicosRepository medicosRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TurnosService turnosService;

    @Override
    public String crearReserva(Reserva reserva) {
        LocalDate fecha = FechaHoraUtils.parseFecha(reserva.getFecha());
        LocalDate ahora = LocalDate.now(ZoneId.of("-05:00"));

        if (!fecha.isAfter(ahora)) {
            log.error("La fecha es anterior o igual al dia de hoy");
            return null;
        }

        reserva.setFechaObj(FechaHoraUtils.parseFecha(reserva.getFecha()));
        reserva.setInicioObj(FechaHoraUtils.parseHora(reserva.getInicio()));
        reserva.setFinObj(FechaHoraUtils.parseHora(reserva.getFin()));

        if (!validarFechaHoraReserva(reserva)) {
            return null;
        }

        reserva.setEstado(Constantes.ESTADO_RESERVADO);
        String idReserva = reservasRepository.crearReserva(reserva);

        if (idReserva != null) {
            reserva.setIdReserva(idReserva);
            reserva.setPaciente(pacientesRepository.getPaciente(reserva.getIdPaciente()));
            reserva.setEspecialidad(especialidadesRepository.getEspecialidad(reserva.getIdEspecialidad()));
            reserva.setSede(sedesRepository.getSede(reserva.getIdSede()));
            reserva.setMedico(medicosRepository.getMedico(reserva.getIdMedico()));
            emailService.enviarEmailConfirmacionCreacion(reserva);
        }

        return idReserva;
    }

    @Override
    public RespuestaReservas listarReservasxIdPaciente(String idPaciente, String estado) {
        List<Reserva> listaReservas = reservasRepository.listarReservasxIdPaciente(idPaciente, estado, null, null);
        for (Reserva reserva : listaReservas) {
            reserva.setPaciente(pacientesRepository.getPaciente(reserva.getIdPaciente()));
            reserva.setEspecialidad(especialidadesRepository.getEspecialidad(reserva.getIdEspecialidad()));
            reserva.setSede(sedesRepository.getSede(reserva.getIdSede()));
            reserva.setMedico(medicosRepository.getMedico(reserva.getIdMedico()));
        }
        RespuestaReservas res = new RespuestaReservas();
        res.setData(listaReservas);
        return res;
    }
    
    @Override
	public RespuestaReservas listarReservasxIdMedico(String idMedico, String estado) {
    	List<Reserva> listaReservas = reservasRepository.listarReservasxIdMedico(idMedico, estado, null, null);
        for (Reserva reserva : listaReservas) {
            reserva.setPaciente(pacientesRepository.getPaciente(reserva.getIdPaciente()));
            reserva.setEspecialidad(especialidadesRepository.getEspecialidad(reserva.getIdEspecialidad()));
            reserva.setSede(sedesRepository.getSede(reserva.getIdSede()));
            reserva.setMedico(medicosRepository.getMedico(reserva.getIdMedico()));
        }
        RespuestaReservas res = new RespuestaReservas();
        res.setData(listaReservas);
        return res;
	}

    @Override
    public boolean anularReserva(String idReserva) {
        return reservasRepository.anularReserva(idReserva);
    }

    @Override
    public boolean completarReserva(String idReserva) {
        return reservasRepository.completarReserva(idReserva);
    }

    @Override
    public boolean reprogramarReserva(Reserva reserva) {
        Reserva reservaBD = reservasRepository.getReserva(reserva.getIdReserva());
        if (reservaBD == null) {
            return false;
        }

        reserva.setIdMedico(reservaBD.getIdMedico());
        reserva.setFechaObj(reservaBD.getFechaObj());
        reserva.setInicioObj(reservaBD.getInicioObj());
        reserva.setFinObj(reservaBD.getFinObj());
        if (!validarFechaHoraReserva(reserva)) {
            return false;
        }

        return reservasRepository.reprogramarReserva(reserva);
    }

    private boolean validarFechaHoraReserva(Reserva reserva) {
        RespuestaTurnos respuestaTurnos = turnosService.getTurnosDisponibles(null, reserva.getIdMedico(),
                reserva.getFechaObj(), null, null);

        for (Turno turno : respuestaTurnos.getData().get(0).getTurnos()) {
            if (turno.getInicio().equals(reserva.getInicioObj()) && turno.getFin().equals(reserva.getFinObj())) {
                return true;
            }
        }
        return false;
    }
}