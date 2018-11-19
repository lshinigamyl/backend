/**
 * RQ- Mantenimiento de los Medicos
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.dao;

import java.util.List;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Medico;

public interface MedicosRepository {
    public Medico getMedico(String idMedico);
    List<Medico> listarMedicos(String idEspecialidad, String idSede);
    List<Medico> listarMedicos();
    List<Medico> listarMedicosVm();
    public String crearMedico(Medico medico);
}