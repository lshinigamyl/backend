/**
 * RQ- Mantenimiento de los Médicos
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.service;

import java.util.List;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Medico;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.RespuestaMedicos;

public interface MedicosService {
    public RespuestaMedicos listarMedicos(String idEspecialidad);
    List<Medico> listarMedicos();
    List<Medico> listarMedicosVm();
    public Medico getMedico(String idMedico);
}