/**
 * RQ- Mantenimiento de los Pacientes
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.service;

import java.util.List;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Paciente;

public interface PacientesService{
    public Paciente getPaciente(String idPaciente);
    public String crearPaciente(Paciente paciente);
    public List<Paciente> listarPacientes();
    public void actualizarPaciente(Paciente paciente);
    public void eliminarPaciente(String idpaciente);
    public Paciente loginPaciente(Paciente paciente);
    public void cambiarPassword(Paciente paciente);
}