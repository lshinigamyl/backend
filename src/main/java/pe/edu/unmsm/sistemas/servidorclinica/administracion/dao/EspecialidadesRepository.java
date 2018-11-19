/**
 * RQ- Mantenimiento de las Especialidades
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.dao;

import java.util.List;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Especialidad;

public interface EspecialidadesRepository {
    public List<Especialidad> listarEspecialidades();
    public Especialidad getEspecialidad(String idEspecialidad);
    public List<Especialidad> buscarEspecialidadesxNombre(String nombre);
    public String crearEspecialidad(Especialidad especialidad);
    public void actualizarEspecialidad(Especialidad especialidad);
    public void eliminarEspecialidad(String idEspecialidad);
    public void darAlta(String idEspecialidad);
}