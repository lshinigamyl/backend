/**
 * RQ- Mantenimiento de los Médicos
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.EspecialidadesRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.MedicosRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.SedesRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Especialidad;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Medico;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.RespuestaMedicos;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Sede;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.service.MedicosService;

@Service
public class MedicosServiceImpl implements MedicosService {

    @Autowired
    private MedicosRepository medicosRepository;

    @Override
    public RespuestaMedicos listarMedicos(String idEspecialidad) {
        RespuestaMedicos res = new RespuestaMedicos();
        res.setData(medicosRepository.listarMedicos(idEspecialidad, null));
        return res;
    }

	@Override
	public List<Medico> listarMedicos() {
		return medicosRepository.listarMedicos();
	}

	@Override
	public List<Medico> listarMedicosVm() {
		return medicosRepository.listarMedicosVm();
	}

	@Override
	public Medico getMedico(String idMedico) {
		if(idMedico.isEmpty()) {
			return new Medico();
		}
		
		return medicosRepository.getMedico(idMedico);
	}
}