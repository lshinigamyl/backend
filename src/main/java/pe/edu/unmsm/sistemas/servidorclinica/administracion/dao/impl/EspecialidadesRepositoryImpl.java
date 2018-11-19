/**
 * RQ- Mantenimiento de las Especialidades 
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.EspecialidadesRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Especialidad;
import pe.edu.unmsm.sistemas.servidorclinica.utils.FirebaseUtils;

@Repository
public class EspecialidadesRepositoryImpl implements EspecialidadesRepository {

    private static final Logger log = LoggerFactory.getLogger(EspecialidadesRepositoryImpl.class);

    @Autowired
    private DatabaseReference databaseReference;
    
    @Autowired
    private ObjectMapper objectMapper;

    private Especialidad instanciarEspecialidad(DataSnapshot especialidadSnapshot) {
        Especialidad especialidad = new Especialidad();

        especialidad.setIdEspecialidad(especialidadSnapshot.getKey());
        especialidad.setNombre(FirebaseUtils.getString(especialidadSnapshot, "nombre"));
        especialidad.setDescripcion(FirebaseUtils.getString(especialidadSnapshot, "descripcion"));
        especialidad.setEstado(FirebaseUtils.getString(especialidadSnapshot, "estado"));

        return especialidad;
    }

    @Override
    public Especialidad getEspecialidad(String idEspecialidad) {
        DataSnapshot especialidadesSnapshot = FirebaseUtils
                .getDataSnapshot(databaseReference.child("especialidad").child(idEspecialidad));
        if (especialidadesSnapshot.exists()) {
            return instanciarEspecialidad(especialidadesSnapshot);
        }
        return null;
    }
    
    @Override
	public List<Especialidad> buscarEspecialidadesxNombre(String nombre) {
    	log.info("Leyendo la hoja especialidades ...");
        DataSnapshot especialidadesSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("especialidad"));

        List<Especialidad> listaEspecialidades = new ArrayList<>();

        if (!especialidadesSnapshot.exists()) {
            log.error("Error al leer la data de especialidades");
            return listaEspecialidades;
        }

        for (DataSnapshot especialidad : especialidadesSnapshot.getChildren()) {
            Especialidad objEspecialidad = instanciarEspecialidad(especialidad);
            
            if (!objEspecialidad.getEstado().equals("ACT")) {
            	continue;
			}
            
            if (!objEspecialidad.getNombre().isEmpty() && !objEspecialidad.getNombre().contains(nombre)) {
				continue;
			}
            
            listaEspecialidades.add(objEspecialidad);
        }

        return listaEspecialidades;
	}

    @Override
    public String crearEspecialidad(Especialidad especialidad) {
    	log.trace("Entrando a crearEspecialidad ...");
    	Object idEspecialidad=UUID.randomUUID();
    	
    	if (especialidad.getIdEspecialidad().isEmpty()) {
    		DatabaseReference ref = databaseReference.child("especialidad").child(idEspecialidad.toString());
    		ref.child("nombre").setValueAsync(especialidad.getNombre());
            ref.child("descripcion").setValueAsync(especialidad.getDescripcion());
            ref.child("estado").setValueAsync("ACT");
            
            return idEspecialidad.toString();
		}else {
			DataSnapshot especialidadesSnapshot = FirebaseUtils
	                .getDataSnapshot(databaseReference.child("especialidad").child(especialidad.getIdEspecialidad()));
	        if (!especialidadesSnapshot.exists()) {
	        	DatabaseReference ref = databaseReference.child("especialidad").child(especialidad.getIdEspecialidad());
				ref.child("nombre").setValueAsync(especialidad.getNombre());
		        ref.child("descripcion").setValueAsync(especialidad.getDescripcion());
		        ref.child("estado").setValueAsync("ACT");
		        
		        return especialidad.getIdEspecialidad();
	        }
	        return null;
		}
    }

	@Override
	public List<Especialidad> listarEspecialidades() {
		log.info("Leyendo la hoja especialidades ...");
        DataSnapshot especialidadesSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("especialidad"));

        List<Especialidad> listaEspecialidades = new ArrayList<>();

        if (!especialidadesSnapshot.exists()) {
            log.error("Error al leer la data de especialidades");
            return listaEspecialidades;
        }

        for (DataSnapshot especialidad : especialidadesSnapshot.getChildren()) {
            Especialidad nuevaEspecialidad = instanciarEspecialidad(especialidad);
            
            if (nuevaEspecialidad.getEstado().equals("ACT")) {
            	listaEspecialidades.add(nuevaEspecialidad);
			}
        }

        return listaEspecialidades;
	}

	@Override
	public void actualizarEspecialidad(Especialidad especialidad) {
		log.trace("Entrando a actualizarEspecialidad ...");
		DatabaseReference ref = databaseReference.child("especialidad").child(especialidad.getIdEspecialidad());
		
		Map<String, Object> mapeo = new HashMap<>();
		mapeo.put("nombre", especialidad.getNombre());
		mapeo.put("descripcion", especialidad.getDescripcion());

		ref.updateChildrenAsync(mapeo);
	}
	
	public void actualizarEspecialidadxSede(Especialidad especialidad) {
		log.trace("Entrando a actualizarEspecialidad ...");
		DatabaseReference ref = databaseReference.child("especialidades").child(especialidad.getIdEspecialidad());
		
		Map<String, Object> mapeo = new HashMap<>();
		mapeo.put("estado", especialidad.getEstado());

		ref.updateChildrenAsync(mapeo);
	}

	@Override
	public void eliminarEspecialidad(String idEspecialidad) {
		log.trace("Entrando a eliminarEspecialidad ...");
		DatabaseReference ref = databaseReference.child("especialidad").child(idEspecialidad);
		
		Map<String, Object> mapeo = new HashMap<>();
		mapeo.put("estado", "INA");

		ref.updateChildrenAsync(mapeo);
	}

	@Override
	public void darAlta(String idEspecialidad) {
		log.trace("Entrando a darAlta ...");
		DatabaseReference ref = databaseReference.child("especialidad").child(idEspecialidad);
		
		Map<String, Object> mapeo = new HashMap<>();
		mapeo.put("estado", "ACT");

		ref.updateChildrenAsync(mapeo);
	}
}