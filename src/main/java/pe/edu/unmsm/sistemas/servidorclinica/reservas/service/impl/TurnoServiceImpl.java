/**
 * RQ- Mantenimiento de las Reservas de Cita
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.reservas.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.MedicosRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.NegocioRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.SedesRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Horario;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Medico;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Sede;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Turno;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.dao.ReservasRepository;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.domain.ParMedicoTurnos;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.domain.Reserva;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.domain.RespuestaTurnos;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.service.TurnosService;
import pe.edu.unmsm.sistemas.servidorclinica.utils.Constantes;
import pe.edu.unmsm.sistemas.servidorclinica.utils.FechaHoraUtils;

@Service
public class TurnoServiceImpl implements TurnosService {

    private static final Logger log = LoggerFactory.getLogger(TurnoServiceImpl.class);

    @Autowired
    private NegocioRepository negocioRepository;

    @Autowired
    private ReservasRepository reservasRepository;

    @Autowired
    private MedicosRepository medicosRepository;

    @Autowired
    private SedesRepository sedesRepository;

    @Override
    public RespuestaTurnos getTurnosDisponibles(String idEspecialidad, String idMedico, LocalDate fecha,
            LocalTime inicio, LocalTime fin) {
        RespuestaTurnos respuesta = new RespuestaTurnos();
        
        log.info("idEspecialidad: "+idEspecialidad);
        log.info("idMedico: "+idMedico);

        log.info("Leyendo la regla de negocio minutos-duracion-cita");
        int duracion = negocioRepository.getMinutosDuracionCita();

        respuesta.setDuracion(duracion);

        log.info("Leyendo reservas activas ...");
        List<Reserva> reservas = reservasRepository.listarReservasxIdPaciente(null, Constantes.ESTADO_RESERVADO, null, null);

        List<ParMedicoTurnos> listaPares = new ArrayList<>();

        if (idMedico!=null) {
        	Medico medico = medicosRepository.getMedico(idMedico);
            medico.setSede(sedesRepository.getSede(medico.getIdSede()));

            ParMedicoTurnos par = new ParMedicoTurnos();
            par.setIdMedico(medico.getIdMedico());
            par.setMedico(medico);
            par.setTurnos(getListaTurnosDisponiblesPorMedico(reservas, medico, fecha, inicio, fin, duracion));

            listaPares.add(par);
            respuesta.setData(listaPares);
            
            return respuesta;
		}else {
			if (idEspecialidad!=null) {
				log.info("Buscando horarios de medicos del dia {} ...", fecha);
	            List<Medico> medicos = medicosRepository.listarMedicos(idEspecialidad, null);

	            for (Medico med : medicos) {
	            	String idSede = med.getSede().getIdSede();
	            	
	                if (idSede!=null) {
	                	med.setSede(sedesRepository.getSede(idSede));
					}else {
						med.setSede(new Sede());
					}

	                List<Turno> listaTurnosDisponibles = getListaTurnosDisponiblesPorMedico(reservas, med, fecha, inicio,
	                        fin, duracion);

	                if (!listaTurnosDisponibles.isEmpty()) {
	                    ParMedicoTurnos par = new ParMedicoTurnos();
	                    par.setIdMedico(med.getIdMedico());
	                    par.setMedico(med);
	                    par.setTurnos(listaTurnosDisponibles);
	                    listaPares.add(par);
	                }
	            }
	            
	            respuesta.setData(listaPares);
	            return respuesta;
			}else {
				return new RespuestaTurnos();
			}
		}
    }
    
    @Override
	public RespuestaTurnos getTurnosDisponibles(String idMedico, LocalDate fecha, LocalTime inicio, LocalTime fin) {
    	RespuestaTurnos respuesta = new RespuestaTurnos();

        log.info("Leyendo la regla de negocio minutos-duracion-cita");
        int duracion = negocioRepository.getMinutosDuracionCita();

        respuesta.setDuracion(duracion);

        log.info("Leyendo reservas activas ...");
        List<Reserva> reservas = reservasRepository.listarReservasxIdPaciente(null, Constantes.ESTADO_RESERVADO, null, null);

        List<ParMedicoTurnos> listaPares = new ArrayList<>();

        if (idMedico != null) {
            Medico medico = medicosRepository.getMedico(idMedico);
            medico.setSede(sedesRepository.getSede(medico.getIdSede()));

            ParMedicoTurnos par = new ParMedicoTurnos();
            par.setIdMedico(medico.getIdMedico());
            par.setMedico(medico);
            par.setTurnos(getListaTurnosDisponiblesPorMedico(reservas, medico, fecha, inicio, fin, duracion));

            listaPares.add(par);
            respuesta.setData(listaPares);
        }

        log.info("Buscando horarios de medicos del dia {} ...", fecha);
        List<Medico> medicos = medicosRepository.listarMedicos();

        for (Medico med : medicos) {
            med.setSede(sedesRepository.getSede(med.getIdSede()));

            List<Turno> listaTurnosDisponibles = getListaTurnosDisponiblesPorMedico(reservas, med, fecha, inicio,
                    fin, duracion);

            if (!listaTurnosDisponibles.isEmpty()) {
                ParMedicoTurnos par = new ParMedicoTurnos();
                par.setIdMedico(med.getIdMedico());
                par.setMedico(med);
                par.setTurnos(listaTurnosDisponibles);
                listaPares.add(par);
            }
        }
        respuesta.setData(listaPares);
        
        return respuesta;
	}
    
    private List<Turno> getListaTurnosDisponiblesPorMedico(List<Reserva> listaReservas, Medico medico, LocalDate fecha,
            LocalTime inicio, LocalTime fin, int duracionCita) {
        List<Turno> listaTurnos = new ArrayList<>();

        Horario horario = medico.getHorario();
        Turno turno = null;

        // Calculo del dia de la semana
        switch (fecha.getDayOfWeek()) {
        case MONDAY:
            turno = horario.getLunes();
            break;

        case TUESDAY:
            turno = horario.getMartes();
            break;

        case WEDNESDAY:
            turno = horario.getMiercoles();
            break;

        case THURSDAY:
            turno = horario.getJueves();
            break;

        case FRIDAY:
            turno = horario.getViernes();
            break;

        case SATURDAY:
            turno = horario.getSabado();
            break;

        default:
            break;
        }

        if (turno == null) {
            return listaTurnos;
        }

        // acotamiento del turno de trabajo del medico segun el rango de horas
        if (inicio != null) {
            if (inicio.isAfter(turno.getFin()) || inicio.equals(turno.getFin())) {
                return listaTurnos;
            }
            if (inicio.isAfter(turno.getInicio()) && inicio.isBefore(turno.getFin())) {
                boolean turnoSeteado = false;
                for (LocalTime in = turno.getInicio().plusMinutes(duracionCita); in
                        .isBefore(turno.getFin()); in = in.plusMinutes(duracionCita)) {
                    if (in.isAfter(inicio) || in.equals(inicio)) {
                        turno.setInicio(in);
                        turnoSeteado = true;
                        break;
                    }
                }
                if (!turnoSeteado) {
                    return listaTurnos;
                }
            }
        }
        if (fin != null) {
            if (fin.isBefore(turno.getInicio()) || fin.equals(turno.getInicio())) {
                return listaTurnos;
            }
            if (fin.isBefore(turno.getFin()) && fin.isAfter(turno.getInicio())) {
                boolean turnoSeteado = false;
                for (LocalTime fi = turno.getFin().minusMinutes(duracionCita); fi
                        .isAfter(turno.getInicio()); fi = fi.minusMinutes(duracionCita)) {
                    if (fi.isBefore(fin) || fi.equals(fin)) {
                        turno.setFin(fi);
                        turnoSeteado = true;
                        break;
                    }
                }
                if (!turnoSeteado) {
                    return listaTurnos;
                }
            }
        }

        // Calculo de turnos que ya estan ocupados por el medico
        List<Turno> turnosOcupadosMedico = new ArrayList<>();
        for (Reserva re : listaReservas) {
            if (re.getIdMedico().equals(medico.getIdMedico()) && re.getFechaObj().equals(fecha)) {
                Turno t = new Turno();
                t.setInicio(re.getInicioObj());
                t.setFin(re.getFinObj());
                turnosOcupadosMedico.add(t);
            }
        }

        // llenado de turnos disponibles dentro del rango de horas
        for (LocalTime in = turno.getInicio(); in.isBefore(turno.getFin()); in = in.plusMinutes(duracionCita)) {
            Turno t = new Turno();
            t.setInicio(in);
            t.setFin(in.plusMinutes(duracionCita));

            boolean estaOcupado = false;
            for (Turno tOcupado : turnosOcupadosMedico) {
                if (turnosSeSolapan(t, tOcupado)) {
                    estaOcupado = true;
                    break;
                }
            }
            if (estaOcupado) {
                continue;
            }
            t.setInicioString(FechaHoraUtils.writeHora(t.getInicio()));
            t.setFinString(FechaHoraUtils.writeHora(t.getFin()));
            listaTurnos.add(t);
        }
        return listaTurnos;
    }

    private boolean turnosSeSolapan(Turno a, Turno b) {
        return a.getInicio().equals(b.getInicio())
                || (a.getInicio().isBefore(b.getInicio()) && a.getFin().isAfter(b.getInicio()))
                || (a.getInicio().isAfter(b.getInicio()) && a.getInicio().isBefore(b.getFin()));
    }
}