/**
 * RQ- Mantenimiento de las Reservas de Cita
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.reservas.dao.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.edu.unmsm.sistemas.servidorclinica.reservas.dao.ReservasRepository;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.domain.Reserva;
import pe.edu.unmsm.sistemas.servidorclinica.utils.Constantes;
import pe.edu.unmsm.sistemas.servidorclinica.utils.FirebaseUtils;

@Repository
public class ReservasRepositoryImpl implements ReservasRepository {

    private static final Logger log = LoggerFactory.getLogger(ReservasRepositoryImpl.class);

    @Autowired
    private DatabaseReference databaseReference;

    @Override
    public List<Reserva> listarReservasxIdPaciente(String idPaciente, String estado, LocalDateTime fechaHoraDesde,
            LocalDateTime fechaHoraHasta) {

        List<String> estados = new ArrayList<>();
        if (estado != null) {
            String[] estadosArray = estado.split(",");
            estados = Arrays.asList(estadosArray);
        }

        List<Reserva> listaReservas = new ArrayList<>();

        if (estados.isEmpty()) {
            log.info("Leyendo la hoja reservas ...");
            DataSnapshot reservasSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("reservas"));

            if (reservasSnapshot == null) {
                log.error("Error al leer la data de reservas");
                return listaReservas;
            }

            listaReservas.addAll(leerReservas(reservasSnapshot, idPaciente, null, estados, fechaHoraDesde, fechaHoraHasta));
            log.info("Leyendo la hoja reservas-concluidas ...");
            reservasSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("reservas-concluidas"));

            if (reservasSnapshot == null) {
                log.error("Error al leer la data de reservas-concluidas");
                return listaReservas;
            }

            listaReservas.addAll(leerReservas(reservasSnapshot, idPaciente, null, estados, fechaHoraDesde, fechaHoraHasta));
        } else {
            if (estados.contains(Constantes.ESTADO_RESERVADO)) {
                log.info("Leyendo la hoja reservas ...");
                DataSnapshot reservasSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("reservas"));

                if (reservasSnapshot == null) {
                    log.error("Error al leer la data de reservas");
                    return listaReservas;
                }

                listaReservas
                        .addAll(leerReservas(reservasSnapshot, idPaciente, null, estados, fechaHoraDesde, fechaHoraHasta));
            }
            if (estados.contains(Constantes.ESTADO_COMPLETADO) || estados.contains(Constantes.ESTADO_ANULADO)) {
                log.info("Leyendo la hoja reservas-concluidas ...");
                DataSnapshot reservasSnapshot = FirebaseUtils
                        .getDataSnapshot(databaseReference.child("reservas-concluidas"));

                if (reservasSnapshot == null) {
                    log.error("Error al leer la data de reservas-concluidas");
                    return listaReservas;
                }

                listaReservas
                        .addAll(leerReservas(reservasSnapshot, idPaciente, null, estados, fechaHoraDesde, fechaHoraHasta));
            }
        }

        return listaReservas;
    }
    
    @Override
	public List<Reserva> listarReservasxIdMedico(String idMedico, String estado, LocalDateTime fechaInicial,
			LocalDateTime fechaFinal) {
    	
    	List<String> lstEstados = new ArrayList<>();
        if (estado != null) {
            String[] estadosArray = estado.split(",");
            lstEstados = Arrays.asList(estadosArray);
        }

        List<Reserva> lstReservas = new ArrayList<>();

        if (lstEstados.isEmpty()) {
            log.info("Leyendo la hoja reservas ...");
            DataSnapshot reservasSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("reservas"));

            if (reservasSnapshot == null) {
                log.error("Error al leer la data de reservas");
                return lstReservas;
            }

            lstReservas.addAll(leerReservas(reservasSnapshot, null, idMedico, lstEstados, fechaInicial, fechaFinal));
            log.info("Leyendo la hoja reservas-concluidas ...");
            reservasSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("reservas-concluidas"));

            if (reservasSnapshot == null) {
                log.error("Error al leer la data de reservas-concluidas");
                return lstReservas;
            }

            lstReservas.addAll(leerReservas(reservasSnapshot, null, idMedico, lstEstados, fechaInicial, fechaFinal));
        } else {
            if (lstEstados.contains(Constantes.ESTADO_RESERVADO)) {
                log.info("Leyendo la hoja reservas ...");
                DataSnapshot reservasSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("reservas"));

                if (reservasSnapshot == null) {
                    log.error("Error al leer la data de reservas");
                    return lstReservas;
                }

                lstReservas.addAll(leerReservas(reservasSnapshot, null, idMedico, lstEstados, fechaInicial, fechaFinal));
            }
            if (lstEstados.contains(Constantes.ESTADO_COMPLETADO) || lstEstados.contains(Constantes.ESTADO_ANULADO)) {
                log.info("Leyendo la hoja reservas-concluidas ...");
                DataSnapshot reservasSnapshot = FirebaseUtils
                        .getDataSnapshot(databaseReference.child("reservas-concluidas"));

                if (reservasSnapshot == null) {
                    log.error("Error al leer la data de reservas-concluidas");
                    return lstReservas;
                }

                lstReservas.addAll(leerReservas(reservasSnapshot, null, idMedico, lstEstados, fechaInicial, fechaFinal));
            }
        }

        return lstReservas;
	}

    private List<Reserva> leerReservas(DataSnapshot reservasSnapshot, String idPaciente, String idMedico, List<String> lstEstados,
            LocalDateTime fechaInicial, LocalDateTime fechaFinal) {
        List<Reserva> listaReservas = new ArrayList<>();
        
        if (idPaciente!=null && idMedico==null) {
        	for (DataSnapshot reserva : reservasSnapshot.getChildren()) {
                Reserva nuevaReserva = instanciarReserva(reserva);

                if (idPaciente != null && !idPaciente.equals(nuevaReserva.getIdPaciente())) {
                    continue;
                }
                if (!lstEstados.isEmpty() && !lstEstados.contains(nuevaReserva.getEstado())) {
                    continue;
                }
                LocalDateTime fechaHoraCita = LocalDateTime.of(nuevaReserva.getFechaObj(), nuevaReserva.getInicioObj());
                if (fechaInicial != null && fechaInicial.isAfter(fechaHoraCita)) {
                    continue;
                }
                if (fechaFinal != null && fechaFinal.isBefore(fechaHoraCita)) {
                    continue;
                }

                listaReservas.add(nuevaReserva);
            }
            return listaReservas;
		}
        
        if (idPaciente==null && idMedico!=null) {
        	for (DataSnapshot reserva : reservasSnapshot.getChildren()) {
                Reserva nuevaReserva = instanciarReserva(reserva);

                if (idMedico != null && !idMedico.equals(nuevaReserva.getIdMedico())) {
                    continue;
                }
                if (!lstEstados.isEmpty() && !lstEstados.contains(nuevaReserva.getEstado())) {
                    continue;
                }
                LocalDateTime fechaHoraCita = LocalDateTime.of(nuevaReserva.getFechaObj(), nuevaReserva.getInicioObj());
                if (fechaInicial != null && fechaInicial.isAfter(fechaHoraCita)) {
                    continue;
                }
                if (fechaFinal != null && fechaFinal.isBefore(fechaHoraCita)) {
                    continue;
                }

                listaReservas.add(nuevaReserva);
            }
            return listaReservas;
		}
        
        for (DataSnapshot reserva : reservasSnapshot.getChildren()) {
            Reserva nuevaReserva = instanciarReserva(reserva);
            	
            if (!lstEstados.isEmpty() && !lstEstados.contains(nuevaReserva.getEstado())) {
                continue;
            }
            LocalDateTime fechaHoraCita = LocalDateTime.of(nuevaReserva.getFechaObj(), nuevaReserva.getInicioObj());
            if (fechaInicial != null && fechaInicial.isAfter(fechaHoraCita)) {
                continue;
            }
            if (fechaFinal != null && fechaFinal.isBefore(fechaHoraCita)) {
                continue;
            }

            listaReservas.add(nuevaReserva);
        }
        return listaReservas;
    }

    @Override
    public Reserva getReserva(String idReserva) {
        DataSnapshot reservaSnapshot = FirebaseUtils
                .getDataSnapshot(databaseReference.child("reservas").child(idReserva));
        if (reservaSnapshot.exists()) {
            return instanciarReserva(reservaSnapshot);
        }
        return null;
    }

    private Reserva instanciarReserva(DataSnapshot reservaSnapshot) {
        Reserva nuevaReserva = new Reserva();

        nuevaReserva.setIdReserva(reservaSnapshot.getKey());
        nuevaReserva.setIdEspecialidad(FirebaseUtils.getString(reservaSnapshot, "idEspecialidad"));
        nuevaReserva.setIdSede(FirebaseUtils.getString(reservaSnapshot, "idSede"));
        nuevaReserva.setIdPaciente(FirebaseUtils.getString(reservaSnapshot, "idPaciente"));
        nuevaReserva.setIdMedico(FirebaseUtils.getString(reservaSnapshot, "idMedico"));
        nuevaReserva.setFecha(FirebaseUtils.getString(reservaSnapshot, "fecha"));
        nuevaReserva.setFechaObj(FirebaseUtils.getFecha(reservaSnapshot, "fecha"));
        nuevaReserva.setInicio(FirebaseUtils.getString(reservaSnapshot, "inicio"));
        nuevaReserva.setInicioObj(FirebaseUtils.getHora(reservaSnapshot, "inicio"));
        nuevaReserva.setFin(FirebaseUtils.getString(reservaSnapshot, "fin"));
        nuevaReserva.setFinObj(FirebaseUtils.getHora(reservaSnapshot, "fin"));
        nuevaReserva.setEstado(FirebaseUtils.getString(reservaSnapshot, "estado"));
        Boolean rec = FirebaseUtils.getBoolean(reservaSnapshot, "recordado");
        nuevaReserva.setRecordado(rec == null ? false : rec);

        return nuevaReserva;
    }

    @Override
    public String crearReserva(Reserva reserva) {
        log.trace("Entrando a crearReserva ...");
        DatabaseReference ref = databaseReference.child("reservas").push();
        ref.child("idEspecialidad").setValueAsync(reserva.getIdEspecialidad());
        ref.child("idSede").setValueAsync(reserva.getIdSede());
        ref.child("idPaciente").setValueAsync(reserva.getIdPaciente());
        ref.child("idMedico").setValueAsync(reserva.getIdMedico());
        ref.child("fecha").setValueAsync(reserva.getFecha());
        ref.child("inicio").setValueAsync(reserva.getInicio());
        ref.child("fin").setValueAsync(reserva.getFin());
        ref.child("estado").setValueAsync(reserva.getEstado());
        ref.child("recordado").setValueAsync(false);
        return ref.getKey();
    }

    @Override
    public boolean anularReserva(String idReserva) {
        log.trace("Entrando a anularReserva ...");
        DataSnapshot reservaSnapshot = FirebaseUtils
                .getDataSnapshot(databaseReference.child("reservas").child(idReserva));

        if (!reservaSnapshot.exists()) {
            return false;
        }

        databaseReference.child("reservas").child(idReserva).removeValueAsync();
        Reserva res = instanciarReserva(reservaSnapshot);
        res.setIdReserva(null);
        res.setEspecialidad(null);
        res.setMedico(null);
        res.setSede(null);
        res.setPaciente(null);
        res.setFechaObj(null);
        res.setInicioObj(null);
        res.setFinObj(null);
        res.setEstado(Constantes.ESTADO_ANULADO);
        databaseReference.child("reservas-concluidas").child(idReserva).setValueAsync(res);

        return true;
    }

    @Override
    public boolean completarReserva(String idReserva) {
        log.trace("Entrando a completarReserva ...");
        DataSnapshot reservaSnapshot = FirebaseUtils
                .getDataSnapshot(databaseReference.child("reservas").child(idReserva));

        if (!reservaSnapshot.exists()) {
            return false;
        }

        databaseReference.child("reservas").child(idReserva).removeValueAsync();
        Reserva res = instanciarReserva(reservaSnapshot);
        res.setIdReserva(null);
        res.setEspecialidad(null);
        res.setMedico(null);
        res.setSede(null);
        res.setPaciente(null);
        res.setFechaObj(null);
        res.setInicioObj(null);
        res.setFinObj(null);
        res.setEstado(Constantes.ESTADO_COMPLETADO);
        databaseReference.child("reservas-concluidas").child(idReserva).setValueAsync(res);

        return true;
    }

    @Override
    public boolean reprogramarReserva(Reserva reserva) {
        log.trace("Entrando a reprogramarReserva ...");
        DataSnapshot reservaSnapshot = FirebaseUtils
                .getDataSnapshot(databaseReference.child("reservas").child(reserva.getIdReserva()));

        if (!reservaSnapshot.exists()) {
            return false;
        }

        databaseReference.child("reservas").child(reserva.getIdReserva()).child("fecha")
                .setValueAsync(reserva.getFecha());
        databaseReference.child("reservas").child(reserva.getIdReserva()).child("inicio")
                .setValueAsync(reserva.getInicio());
        databaseReference.child("reservas").child(reserva.getIdReserva()).child("fin").setValueAsync(reserva.getFin());
        databaseReference.child("reservas").child(reserva.getIdReserva()).child("recordado").setValueAsync(false);

        return true;
    }

    @Override
    public boolean marcarReservaComoRecordada(String idReserva) {
        log.trace("Entrando a marcarReservaComoRecordada ...");
        DataSnapshot reservaSnapshot = FirebaseUtils
                .getDataSnapshot(databaseReference.child("reservas").child(idReserva));

        if (!reservaSnapshot.exists()) {
            return false;
        }

        databaseReference.child("reservas").child(idReserva).child("recordado").setValueAsync(true);

        return true;
    }
}
