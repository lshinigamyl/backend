/**
 * RQ- Mantenimiento de los Médicos 
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.EspecialidadesRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.MedicosRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.SedesRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Especialidad;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Horario;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Medico;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Sede;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Turno;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.rest.EspecialidadesController;
import pe.edu.unmsm.sistemas.servidorclinica.utils.FirebaseUtils;

@Repository
public class MedicosRepositoryImpl implements MedicosRepository {

    private static final Logger log = LoggerFactory.getLogger(MedicosRepositoryImpl.class);

    @Autowired
    private DatabaseReference databaseReference;
    
    @Autowired
    private EspecialidadesRepository especialidadesRepository;
    
    @Autowired
    private SedesRepository sedesRepository;
    
    private Medico instanciarMedico(DataSnapshot medicoSnapshot) {
        Medico medico = new Medico();

        medico.setIdMedico(medicoSnapshot.getKey());
        medico.setNombre(FirebaseUtils.getString(medicoSnapshot, "nombre"));
        medico.setApellidos(FirebaseUtils.getString(medicoSnapshot, "apellidos"));
        medico.setCpm(FirebaseUtils.getString(medicoSnapshot, "cpm"));
        medico.setIdEspecialidad(FirebaseUtils.getString(medicoSnapshot, "idEspecialidad"));
        medico.setIdSede(FirebaseUtils.getString(medicoSnapshot, "idSede"));
        medico.setDetalle(FirebaseUtils.getString(medicoSnapshot, "detalle"));
        medico.setUrlFoto(FirebaseUtils.getString(medicoSnapshot, "urlFoto"));
        medico.setHorario(instanciarHorario(medicoSnapshot.child("horario")));

        return medico;
    }

    private Horario instanciarHorario(DataSnapshot horarioSnapshot) {
        Horario horario = new Horario();

        horario.setLunes(horarioSnapshot.hasChild("lunes") ? instanciarTurno(horarioSnapshot.child("lunes")) : null);
        horario.setMartes(horarioSnapshot.hasChild("martes") ? instanciarTurno(horarioSnapshot.child("martes")) : null);
        horario.setMiercoles(
                horarioSnapshot.hasChild("miercoles") ? instanciarTurno(horarioSnapshot.child("miercoles")) : null);
        horario.setJueves(horarioSnapshot.hasChild("jueves") ? instanciarTurno(horarioSnapshot.child("jueves")) : null);
        horario.setViernes(
                horarioSnapshot.hasChild("viernes") ? instanciarTurno(horarioSnapshot.child("viernes")) : null);
        horario.setSabado(horarioSnapshot.hasChild("sabado") ? instanciarTurno(horarioSnapshot.child("sabado")) : null);

        return horario;
    }

    private Turno instanciarTurno(DataSnapshot turnoSnapshot) {
        Turno turno = new Turno();

        turno.setInicioString(FirebaseUtils.getString(turnoSnapshot, "inicio"));
        turno.setInicio(FirebaseUtils.getHora(turnoSnapshot, "inicio"));
        turno.setFinString(FirebaseUtils.getString(turnoSnapshot, "fin"));
        turno.setFin(FirebaseUtils.getHora(turnoSnapshot, "fin"));

        return turno;
    }

    @Override
    public Medico getMedico(String idMedico) {
        log.trace("Entrando a getMedico ...");
        DataSnapshot medicoSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("medicos").child(idMedico));
        if (medicoSnapshot.exists()) {
        	Medico medico=instanciarMedico(medicoSnapshot);
        	medico.setEspecialidad(especialidadesRepository.getEspecialidad(medico.getIdEspecialidad()));
        	medico.setSede(sedesRepository.getSede(medico.getIdSede()));
        	medico.setIdEspecialidad(null);
        	medico.setIdSede(null);
        	
        	return medico;
        }
        return null;
    }

    @Override
    public List<Medico> listarMedicos(String idEspecialidad, String idSede) {
        log.info("Leyendo la hoja medicos ...");
        DataSnapshot medicosSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("medicos"));

        List<Medico> listaMedicos = new ArrayList<>();

        if (!medicosSnapshot.exists()) {
            log.error("Error al leer la data de medicos");
            return listaMedicos;
        }

        for (DataSnapshot medico : medicosSnapshot.getChildren()) {
            Medico nuevoMedico = instanciarMedico(medico);
            Especialidad objEspecialidad=especialidadesRepository.getEspecialidad(nuevoMedico.getIdEspecialidad());
            Sede objSede=sedesRepository.getSede(nuevoMedico.getIdSede());

            if (idEspecialidad != null && !idEspecialidad.equals(nuevoMedico.getIdEspecialidad())) {
                continue;
            }
            if (idSede != null && !idSede.equals(nuevoMedico.getIdSede())) {
                continue;
            }
            
            
            nuevoMedico.setEspecialidad(objEspecialidad);
            nuevoMedico.setIdEspecialidad(null);
            nuevoMedico.setSede(objSede);
            nuevoMedico.setIdSede(null);

            listaMedicos.add(nuevoMedico);
        }

        return listaMedicos;
    }

    @Override
    public String crearMedico(Medico sede) {
        return null;
    }

	@Override
	public List<Medico> listarMedicos() {
        DataSnapshot medicosSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("medicos"));

        List<Medico> listaMedicos = new ArrayList<>();

        if (!medicosSnapshot.exists()) {
            return listaMedicos;
        }

        for (DataSnapshot medico : medicosSnapshot.getChildren()) {
            Medico objMedico = instanciarMedico(medico);
            Especialidad objEspecialidad=especialidadesRepository.getEspecialidad(objMedico.getIdEspecialidad());
            Sede objSede=sedesRepository.getSede(objMedico.getIdSede());
            
            objMedico.setEspecialidad(objEspecialidad);
            objMedico.setSede(objSede);
            
            // Campos no reequeridos
            //objMedico.setCpm(null);
            //objMedico.setDetalle(null);
            //objMedico.setHorario(null);
            objMedico.setIdEspecialidad(null);
            objMedico.setIdSede(null);
            
            listaMedicos.add(objMedico);
        }

        return listaMedicos;
	}

	@Override
	public List<Medico> listarMedicosVm() {
		DataSnapshot medicosSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("medicos"));

        List<Medico> listaMedicos = new ArrayList<>();

        if (!medicosSnapshot.exists()) {
            return listaMedicos;
        }

        for (DataSnapshot medico : medicosSnapshot.getChildren()) {
            Medico objMedico = instanciarMedico(medico);
            
            // Campos no reequeridos
            objMedico.setCpm(null);
            objMedico.setDetalle(null);
            objMedico.setHorario(null);
            objMedico.setIdEspecialidad(null);
            objMedico.setIdSede(null);
            
            listaMedicos.add(objMedico);
        }

        return listaMedicos;
	}

}