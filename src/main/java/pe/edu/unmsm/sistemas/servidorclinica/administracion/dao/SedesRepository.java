/**
 * RQ- Mantenimiento de las Sedes 
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.dao;

import java.util.List;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Sede;

public interface SedesRepository {
    public Sede getSede(String idSede);
    public Sede getSede_Especialidades(String idSede);
    public List<Sede> buscarSedesxNombre(String nombre);
    public List<Sede> buscarSedes_EspecialidadesxNombre(String nombre);
    public List<Sede> listarSedes();
    public List<Sede> listarSedes_Especialidades();
    public String crearSede(Sede sede);
    public void actualizarSede(Sede sede);
    public void eliminarSede(Sede sede);
    public void darAlta(Sede sede);
}