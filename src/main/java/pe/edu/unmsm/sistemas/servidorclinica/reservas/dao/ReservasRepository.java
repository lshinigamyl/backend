/**
 * RQ- Mantenimiento de las Reservas de Cita
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.reservas.dao;

import java.time.LocalDateTime;
import java.util.List;

import pe.edu.unmsm.sistemas.servidorclinica.reservas.domain.Reserva;

public interface ReservasRepository {
    List<Reserva> listarReservasxIdPaciente(String idPaciente, String estado, LocalDateTime fechaHoraDesde,
            LocalDateTime fechaHoraHasta);
    List<Reserva> listarReservasxIdMedico(String idMedico, String estado, LocalDateTime fechaHoraDesde,
            LocalDateTime fechaHoraHasta);
    Reserva getReserva(String idReserva);
    String crearReserva(Reserva reserva);
    boolean anularReserva(String idReserva);
    boolean completarReserva(String idReserva);
    boolean reprogramarReserva(Reserva reserva);
    boolean marcarReservaComoRecordada(String idReserva);
}