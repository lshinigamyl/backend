/**
 * RQ- Mantenimiento de las Especialidades
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.EspecialidadesRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Especialidad;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.RespuestaEspecialidades;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.service.EspecialidadesService;

@Service
public class EspecialidadesServiceImpl implements EspecialidadesService {

    @Autowired
    private EspecialidadesRepository especialidadesRepository;

    @Override
    public RespuestaEspecialidades listarEspecialidades() {
        RespuestaEspecialidades res = new RespuestaEspecialidades();
        res.setData(especialidadesRepository.listarEspecialidades());
        return res;
    }

	@Override
	public Especialidad getEspecialidad(String idEspecialidad) {
		return especialidadesRepository.getEspecialidad(idEspecialidad);
	}
	
	@Override
	public List<Especialidad> buscarEspecialidadesxNombre(String nombre) {
		if (nombre==null) {
			return null;
		}
		
		return especialidadesRepository.buscarEspecialidadesxNombre(nombre);
	}

	@Override
	public String crearEspecialidad(Especialidad especialidad) {
		// Validaciones de negocio.
		if (especialidad.getNombre().isEmpty()) {
			return null;
		}
		
		return especialidadesRepository.crearEspecialidad(especialidad);
	}

	@Override
	public void actualizarEspecialidad(Especialidad especialidad) {
		// Validaciones de negocio.
		if (especialidad.getIdEspecialidad().isEmpty()) {
			return;
		}
		
		especialidadesRepository.actualizarEspecialidad(especialidad);
	}

	@Override
	public void eliminarEspecialidad(String idEspecialidad) {
		// Validaciones de negocio.
		if (idEspecialidad.isEmpty()) {
			return;
		}
		
		especialidadesRepository.eliminarEspecialidad(idEspecialidad);
	}

	@Override
	public void darAlta(String idEspecialidad) {
		// Validaciones de negocio.
		if (idEspecialidad.isEmpty()) {
			return;
		}
		
		especialidadesRepository.darAlta(idEspecialidad);
	}
}