/**
 * RQ- Mantenimiento de las Sedes
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.EspecialidadesRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.SedesRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Especialidad;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Sede;
import pe.edu.unmsm.sistemas.servidorclinica.utils.FirebaseUtils;

@Repository
public class SedesRepositoryImpl implements SedesRepository {

    private static final Logger log = LoggerFactory.getLogger(SedesRepositoryImpl.class);

    @Autowired
    private DatabaseReference databaseReference;
    
    @Autowired
    private EspecialidadesRepositoryImpl especialidadRepository;
    
    @Autowired
    private ObjectMapper objectMapper;

    private Sede instanciarSede(DataSnapshot sedeSnapshot) {
        Sede sede = new Sede();
        
    	sede.setIdSede(sedeSnapshot.getKey());
        sede.setNombre(FirebaseUtils.getString(sedeSnapshot, "nombre"));
        sede.setDireccion(FirebaseUtils.getString(sedeSnapshot, "direccion"));
        sede.setEstado(FirebaseUtils.getString(sedeSnapshot, "estado"));

        return sede;
    }
    
    private Sede instanciarSede_Especialidades(DataSnapshot sedeSnapshot) {
        Sede sede = new Sede();
        
    	sede.setIdSede(sedeSnapshot.getKey());
        sede.setNombre(FirebaseUtils.getString(sedeSnapshot, "nombre"));
        sede.setDireccion(FirebaseUtils.getString(sedeSnapshot, "direccion"));
        sede.setEstado(FirebaseUtils.getString(sedeSnapshot, "estado"));
        sede.setEspecialidades(instanciarListadoEspecialidades(sedeSnapshot));

        return sede;
    }
    
    private List<Especialidad> instanciarListadoEspecialidades(DataSnapshot sedeSnapshot){
    	DataSnapshot especialidadesSnapshot = sedeSnapshot.child("especialidades");
    	
    	List<Especialidad> listaEspecialidades = new ArrayList<Especialidad>();
    	
    	for (DataSnapshot obj : especialidadesSnapshot.getChildren()) {
    		Especialidad especialidad = new Especialidad();
    		especialidad.setIdEspecialidad(obj.getKey());
    		especialidad.setEstado(FirebaseUtils.getString(obj, "estado"));
    		
            listaEspecialidades.add(especialidad);
        }
    	
    	return listaEspecialidades;
    }

    @Override
    public Sede getSede(String idSede) {
        DataSnapshot sedeSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("sedes").child(idSede));
        if (sedeSnapshot.exists()) {
            return instanciarSede(sedeSnapshot);
        }
        return new Sede();
    }
    
    @Override
	public Sede getSede_Especialidades(String idSede) {
    	DataSnapshot sedeSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("sedes").child(idSede));
        if (sedeSnapshot.exists()) {
            return instanciarSede_Especialidades(sedeSnapshot);
        }
        return new Sede();
	}
    
    @Override
	public List<Sede> buscarSedesxNombre(String nombre) {
    	log.info("Leyendo la hoja sedes ...");
        DataSnapshot sedesSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("sedes"));

        List<Sede> listaSedes = new ArrayList<Sede>();
        
        if (!sedesSnapshot.exists()) {
            log.error("Error al leer la data de sedes");
            return listaSedes;
        }

        for (DataSnapshot objSede : sedesSnapshot.getChildren()) {
            Sede sede = instanciarSede(objSede);
            
            if (!sede.getEstado().equals("ACT")) {
				continue;
			}
            
            if (!sede.getNombre().isEmpty()  && !sede.getNombre().contains(nombre)) {
				continue;
			}
            
            listaSedes.add(sede);
        }

        return listaSedes;
	}

	@Override
	public List<Sede> buscarSedes_EspecialidadesxNombre(String nombre) {
		log.info("Leyendo la hoja sedes ...");
        DataSnapshot sedesSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("sedes"));

        List<Sede> listaSedes = new ArrayList<Sede>();
        
        if (!sedesSnapshot.exists()) {
            log.error("Error al leer la data de sedes");
            return listaSedes;
        }

        for (DataSnapshot objSede : sedesSnapshot.getChildren()) {
            Sede sede = instanciarSede_Especialidades(objSede);
            
            if (!sede.getEstado().equals("ACT")) {
				continue;
			}
            
            if (!sede.getNombre().isEmpty()  && !sede.getNombre().contains(nombre)) {
				continue;
			}
            
            listaSedes.add(sede);
        }

        return listaSedes;
	}	

    @Override
    public String crearSede(Sede sede) {
    	log.trace("Entrando a crearSede ...");
    	Object idSede=UUID.randomUUID();
    	
    	if (sede.getIdSede().isEmpty()) {
    		DatabaseReference ref = databaseReference.child("sedes").child(idSede.toString());
    		ref.child("nombre").setValueAsync(sede.getNombre());
            ref.child("direccion").setValueAsync(sede.getDireccion());
            ref.child("estado").setValueAsync("ACT");
            
            if (sede.getEspecialidades()!=null) {
            	for (Especialidad especialidad : sede.getEspecialidades()) {
            		DatabaseReference ref1 = ref.child("especialidades").child(especialidad.getIdEspecialidad());
            		ref1.child("estado").setValueAsync(especialidad.getEstado());
				}
			}else {
				ref.child("especialidades").setValueAsync("");
			}
            
            return idSede.toString();
		} else {
			DatabaseReference ref = databaseReference.child("sedes").child(sede.getIdSede());
			ref.child("nombre").setValueAsync(sede.getNombre());
            ref.child("direccion").setValueAsync(sede.getDireccion());
            ref.child("estado").setValueAsync("ACT");
            
            if (sede.getEspecialidades()!=null) {
            	for (Especialidad especialidad : sede.getEspecialidades()) {
            		DatabaseReference ref1 = ref.child("especialidades").child(especialidad.getIdEspecialidad());
            		ref1.child("estado").setValueAsync(especialidad.getEstado());
				}
			}else {
				ref.child("especialidades").setValueAsync("");
			}
            
            return sede.getIdSede();
		}
    }

	@Override
	public List<Sede> listarSedes() {
		log.info("Leyendo la hoja sedes ...");
        DataSnapshot sedesSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("sedes"));

        List<Sede> listaSedes = new ArrayList<Sede>();
        
        if (!sedesSnapshot.exists()) {
            log.error("Error al leer la data de sedes");
            return listaSedes;
        }

        for (DataSnapshot objSede : sedesSnapshot.getChildren()) {
            Sede sede = instanciarSede(objSede);
            
            if (sede.getEstado().equals("ACT")) {
            	listaSedes.add(sede);
			}
        }

        return listaSedes;
	}
	
	@Override
	public List<Sede> listarSedes_Especialidades() {
		log.info("Leyendo la hoja sedes ...");
        DataSnapshot sedesSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("sedes"));

        List<Sede> listaSedes = new ArrayList<Sede>();
        
        if (!sedesSnapshot.exists()) {
            log.error("Error al leer la data de sedes");
            return listaSedes;
        }

        for (DataSnapshot objSede : sedesSnapshot.getChildren()) {
            Sede sede = instanciarSede_Especialidades(objSede);
            
            if (sede.getNombre().equals("ACT")) {
            	listaSedes.add(sede);
			}
        }

        return listaSedes;
	}

	@Override
	public void actualizarSede(Sede sede) {
		log.trace("Entrando a actualizarSede ...");
		DatabaseReference ref = databaseReference.child("sedes").child(sede.getIdSede());
		
		Map<String, Object> mapeo = new HashMap<>();
		mapeo.put("nombre", sede.getNombre());
		mapeo.put("direccion", sede.getDireccion());
		
		// Actualizar listado de especialidades
		for (Especialidad especialidad : sede.getEspecialidades()) {
			DatabaseReference ref1=ref.child("especialidades").child(especialidad.getIdEspecialidad()); 
			Map<String, Object> mapeo1 = new HashMap<>();
			mapeo1.put("estado", especialidad.getEstado());
			
			ref1.updateChildrenAsync(mapeo1);
		}

		ref.updateChildrenAsync(mapeo);
	}

	@Override
	public void eliminarSede(Sede sede) {
		log.trace("Entrando a eliminarSede ...");
		DatabaseReference ref = databaseReference.child("sedes").child(sede.getIdSede());
		
		Map<String, Object> mapeo = new HashMap<>();
		mapeo.put("estado", "INA");

		ref.updateChildrenAsync(mapeo);
	}

	@Override
	public void darAlta(Sede sede) {
		log.trace("Entrando a eliminarSede ...");
		DatabaseReference ref = databaseReference.child("sedes").child(sede.getIdSede());
		
		Map<String, Object> mapeo = new HashMap<>();
		mapeo.put("estado", "ACT");

		ref.updateChildrenAsync(mapeo);
	}
}