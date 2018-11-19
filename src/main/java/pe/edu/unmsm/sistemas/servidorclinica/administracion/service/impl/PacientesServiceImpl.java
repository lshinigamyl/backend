/**
 * RQ- Mantenimiento de los Pacientes
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.PacientesRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Paciente;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.service.PacientesService;

@Service
public class PacientesServiceImpl implements PacientesService {

    @Autowired
    private PacientesRepository pacientesRepository;

    @Override
    public Paciente getPaciente(String idPaciente) {
        return pacientesRepository.getPaciente(idPaciente);
    }

    @Override
    public String crearPaciente(Paciente paciente) {
        Paciente check = pacientesRepository.getPacientePorDNI(paciente.getDni());
        if(check != null){
            return "existe";
        }
        
        if (paciente.getDni().length()!=8) {
        	return "dni";
		}
        
        return pacientesRepository.crearPaciente(paciente);
    }

	@Override
	public void actualizarPaciente(Paciente paciente) {
		// Validaciones de negocio.
		if (paciente.getIdPaciente().isEmpty()) {
			return;
		}
		
		pacientesRepository.actualizarPaciente(paciente);
	}

	@Override
	public void eliminarPaciente(String idpaciente) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Paciente> listarPacientes() {
		return pacientesRepository.listarPacientes();
	}

	@Override
	public Paciente loginPaciente(Paciente paciente) {
		if (paciente.getDni().isEmpty() || paciente.getPassword().isEmpty()) {
			return null;
		}
		
		return pacientesRepository.loginPaciente(paciente);
	}

	@Override
	public void cambiarPassword(Paciente paciente) {
		if (paciente.getPassword().isEmpty()) {
			return;
		}
		
		pacientesRepository.cambiarPassword(paciente);
	}
}