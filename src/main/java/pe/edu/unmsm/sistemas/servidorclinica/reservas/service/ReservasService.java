/**
 * RQ- Mantenimiento de las Reservas de Cita
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.reservas.service;

import pe.edu.unmsm.sistemas.servidorclinica.reservas.domain.Reserva;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.domain.RespuestaReservas;

public interface ReservasService {
    String crearReserva(Reserva reserva);
    RespuestaReservas listarReservasxIdPaciente(String idPaciente, String estado);
    RespuestaReservas listarReservasxIdMedico(String idMedico, String estado);
    boolean anularReserva(String idReserva);
    boolean completarReserva(String idReserva);
    boolean reprogramarReserva(Reserva reserva);
}