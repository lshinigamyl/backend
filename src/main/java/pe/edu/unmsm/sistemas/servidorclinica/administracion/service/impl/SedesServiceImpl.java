package pe.edu.unmsm.sistemas.servidorclinica.administracion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.EspecialidadesRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.SedesRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Sede;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.service.SedesService;

@Service
public class SedesServiceImpl implements SedesService {
	
	@Autowired
    private SedesRepository sedesRepository;
	
	@Override
	public Sede getSede(String idSede) {
		if (idSede.isEmpty()) {
			return null;
		}
		
		return sedesRepository.getSede(idSede);
	}

	@Override
	public Sede getSede_Especialidades(String idSede) {
		if (idSede.isEmpty()) {
			return null;
		}
		
		return sedesRepository.getSede_Especialidades(idSede);
	}
	
	@Override
	public List<Sede> buscarSedesxNombre(String nombre) {
		if (nombre==null) {
			return null;
		}
		
		return sedesRepository.buscarSedesxNombre(nombre);
	}

	@Override
	public List<Sede> buscarSedes_EspecialidadesxNombre(String nombre) {
		if (nombre==null) {
			return null;
		}
		
		return sedesRepository.buscarSedes_EspecialidadesxNombre(nombre);
	}

	@Override
	public List<Sede> listarSedes() {
		return sedesRepository.listarSedes();
	}

	@Override
	public List<Sede> listarSedes_Especialidades() {
		return sedesRepository.listarSedes_Especialidades();
	}

	@Override
	public String crearSede(Sede sede) {
		if (sede.getNombre().isEmpty()) {
			return null;
		}
		
		return sedesRepository.crearSede(sede);
	}

	@Override
	public void actualizarSede(Sede sede) {
		if (sede.getIdSede().isEmpty()) {
			return;
		}
		
		sedesRepository.actualizarSede(sede);
	}

	@Override
	public void eliminarSede(Sede sede) {
		if (sede.getIdSede().isEmpty()) {
			return;
		}
		
		sedesRepository.eliminarSede(sede);
	}

	@Override
	public void darAlta(Sede sede) {
		if (sede.getIdSede().isEmpty()) {
			return;
		}
		
		sedesRepository.darAlta(sede);
	}	
}
