/**
 * RQ- Mantenimiento de las Reservas de Cita
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.reservas.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.EspecialidadesRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.MedicosRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.PacientesRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.SedesRepository;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.dao.ReservasRepository;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.domain.Reserva;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.service.EmailService;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.service.RecordatoriosService;

@Service
public class RecordatoriosServiceImpl implements RecordatoriosService {

    private static final Logger log = LoggerFactory.getLogger(RecordatoriosServiceImpl.class);

    @Autowired
    private PacientesRepository pacientesRepository;

    @Autowired
    private EspecialidadesRepository especialidadesRepository;

    @Autowired
    private SedesRepository sedesRepository;

    @Autowired
    private MedicosRepository medicosRepository;

    @Autowired
    private ReservasRepository reservasRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public void enviarRecordatoriosDiaAnterior(Reserva reserva) {
        log.info("Enviando recordatorio de dia anterior de reserva {}", reserva.getIdReserva());
        reserva.setPaciente(pacientesRepository.getPaciente(reserva.getIdPaciente()));
        reserva.setEspecialidad(especialidadesRepository.getEspecialidad(reserva.getIdEspecialidad()));
        reserva.setSede(sedesRepository.getSede(reserva.getIdSede()));
        reserva.setMedico(medicosRepository.getMedico(reserva.getIdMedico()));
        if (emailService.enviarEmailRecordatorioDiaAnterior(reserva)) {
            reservasRepository.marcarReservaComoRecordada(reserva.getIdReserva());
        }
    }

    @Override
    public void enviarRecordatoriosMismoDia(Reserva reserva) {
        log.info("Enviando recordatorio de mismo dia de reserva {}", reserva.getIdReserva());
        reserva.setPaciente(pacientesRepository.getPaciente(reserva.getIdPaciente()));
        reserva.setEspecialidad(especialidadesRepository.getEspecialidad(reserva.getIdEspecialidad()));
        reserva.setSede(sedesRepository.getSede(reserva.getIdSede()));
        reserva.setMedico(medicosRepository.getMedico(reserva.getIdMedico()));
        emailService.enviarEmailRecordatorioMismoDia(reserva);
    }

}
