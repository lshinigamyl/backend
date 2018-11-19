/**
 * RQ- Mantenimiento de las Reservas de Cita
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.reservas.service;

import pe.edu.unmsm.sistemas.servidorclinica.reservas.domain.Reserva;

public interface EmailService {
	
	boolean enviarEmailConfirmacionCreacion(Reserva reserva);
	boolean enviarEmailRecordatorioDiaAnterior(Reserva reserva);
	boolean enviarEmailRecordatorioMismoDia(Reserva reserva);

}