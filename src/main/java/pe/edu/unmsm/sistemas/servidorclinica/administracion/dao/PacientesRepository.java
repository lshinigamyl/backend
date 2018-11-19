/**
 * RQ- Mantenimiento de los Pacientes
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.dao;

import java.util.List;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Paciente;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Usuario;

public interface PacientesRepository {
    Paciente getPaciente(String idPaciente);
    String crearPaciente(Paciente paciente);
    Paciente getPacientePorDNI(String dni);
    public List<Paciente> listarPacientes();
    public void actualizarPaciente(Paciente paciente);
    public void eliminarPaciente(String idpaciente);
    public Paciente loginPaciente(Paciente paciente);
    public void cambiarPassword(Paciente paciente);
}