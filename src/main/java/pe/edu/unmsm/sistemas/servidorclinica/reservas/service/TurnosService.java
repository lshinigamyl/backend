/**
 * RQ- Mantenimiento de las Reservas de Cita
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.reservas.service;

import java.time.LocalDate;
import java.time.LocalTime;

import pe.edu.unmsm.sistemas.servidorclinica.reservas.domain.RespuestaTurnos;

public interface TurnosService {

    RespuestaTurnos getTurnosDisponibles(String idEspecialidad, String idMedico, LocalDate fecha, LocalTime inicio, LocalTime fin);
    RespuestaTurnos getTurnosDisponibles(String idMedico, LocalDate fecha, LocalTime inicio, LocalTime fin);
}