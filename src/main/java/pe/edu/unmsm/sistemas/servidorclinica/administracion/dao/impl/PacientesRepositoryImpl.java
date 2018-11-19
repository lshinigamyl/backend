/**
 * RQ- Mantenimiento de los Pacientes
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.impl;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.PacientesRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Paciente;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Usuario;
import pe.edu.unmsm.sistemas.servidorclinica.utils.FirebaseUtils;

@Repository
public class PacientesRepositoryImpl implements PacientesRepository {

    private static final Logger log = LoggerFactory.getLogger(PacientesRepositoryImpl.class);

    @Autowired
    private DatabaseReference databaseReference;

    private Paciente instanciarPaciente(DataSnapshot pacienteSnapshot) {
        Paciente paciente = new Paciente();

        paciente.setIdPaciente(pacienteSnapshot.getKey());
        paciente.setNombre(FirebaseUtils.getString(pacienteSnapshot, "nombre"));
        paciente.setApe_pat(FirebaseUtils.getString(pacienteSnapshot, "ape_pat"));
        paciente.setApe_mat(FirebaseUtils.getString(pacienteSnapshot, "ape_mat"));
        paciente.setDni(FirebaseUtils.getString(pacienteSnapshot, "dni"));
        paciente.setDireccion(FirebaseUtils.getString(pacienteSnapshot, "direccion"));
        paciente.setFec_nac(FirebaseUtils.getString(pacienteSnapshot, "fec_nac"));
        paciente.setSexo(FirebaseUtils.getString(pacienteSnapshot, "sexo"));
        paciente.setTelefono(FirebaseUtils.getString(pacienteSnapshot, "telefono"));
        paciente.setNumero_emergencia(FirebaseUtils.getString(pacienteSnapshot, "numero_emergencia"));
        paciente.setContacto_emergencia(FirebaseUtils.getString(pacienteSnapshot, "contacto_emergencia"));
        paciente.setEmail(FirebaseUtils.getString(pacienteSnapshot, "email"));
        paciente.setPassword(FirebaseUtils.getString(pacienteSnapshot, "password"));
        paciente.setFotoUrl(FirebaseUtils.getString(pacienteSnapshot, "fotoUrl"));

        return paciente;
    }

    @Override
    public Paciente getPaciente(String idPaciente) {
        log.trace("Entrando a getPaciente ...");
        DataSnapshot pacienteSnapshot = FirebaseUtils
                .getDataSnapshot(databaseReference.child("pacientes").child(idPaciente));
        if (pacienteSnapshot.exists()) {
            return instanciarPaciente(pacienteSnapshot);
        }
        return null;
    }

    @Override
    public String crearPaciente(Paciente paciente) {
        log.trace("Entrando a crearPaciente ...");
        Object idPaciente = UUID.randomUUID();
        
        DatabaseReference ref = databaseReference.child("pacientes").child(idPaciente.toString());
        ref.child("nombre").setValueAsync(paciente.getNombre());
        ref.child("ape_pat").setValueAsync(paciente.getApe_pat());
        ref.child("ape_mat").setValueAsync(paciente.getApe_mat());
        ref.child("dni").setValueAsync(paciente.getDni());
        ref.child("direccion").setValueAsync(paciente.getDireccion());
        ref.child("fec_nac").setValueAsync(paciente.getFec_nac());
        ref.child("sexo").setValueAsync(paciente.getSexo());
        ref.child("telefono").setValueAsync(paciente.getTelefono());
        ref.child("numero_emergencia").setValueAsync(paciente.getNumero_emergencia());
        ref.child("contacto_emergencia").setValueAsync(paciente.getContacto_emergencia());
        ref.child("email").setValueAsync(paciente.getEmail());
        ref.child("password").setValueAsync(paciente.getPassword());
        ref.child("fotoUrl").setValueAsync(paciente.getFotoUrl());
        return idPaciente.toString();
    }

    @Override
    public Paciente getPacientePorDNI(String dni) {
        DataSnapshot pacientesSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("pacientes"));

        if (!pacientesSnapshot.exists()) {
            log.error("Error al leer la data de medicos");
            return null;
        }

        for (DataSnapshot paciente : pacientesSnapshot.getChildren()) {
            Paciente nuevoPaciente = instanciarPaciente(paciente);

            if (nuevoPaciente.getDni().equals(dni)) {
                return nuevoPaciente;
            }
        }

        return null;
    }

	@Override
	public List<Paciente> listarPacientes() {
		log.info("Leyendo la hoja paciente ...");
        DataSnapshot pacienteSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("pacientes"));

        List<Paciente> listaPacientes = new ArrayList<>();

        if (!pacienteSnapshot.exists()) {
            log.error("Error al leer la data de paciente");
            return listaPacientes;
        }

        for (DataSnapshot paciente : pacienteSnapshot.getChildren()) {
            Paciente nuevoPaciente = instanciarPaciente(paciente);

            listaPacientes.add(nuevoPaciente);
        }

        return listaPacientes;
	}

	@Override
	public void actualizarPaciente(Paciente paciente) {
		log.trace("Entrando a actualizarPaciente ...");
		DatabaseReference ref = databaseReference.child("pacientes").child(paciente.getIdPaciente());
		
		Map<String, Object> mapeo = new HashMap<>();
		mapeo.put("nombre", paciente.getNombre());
		mapeo.put("ape_pat", paciente.getApe_pat());
		mapeo.put("ape_mat", paciente.getApe_mat());
		mapeo.put("dni", paciente.getDni());
		mapeo.put("direccion", paciente.getDni());
		mapeo.put("fec_nac", paciente.getDni());
		mapeo.put("sexo", paciente.getDni());
		mapeo.put("telefono", paciente.getDni());
		mapeo.put("numero_emergencia", paciente.getDni());
		mapeo.put("contacto_emergencia", paciente.getDni());
		mapeo.put("email", paciente.getEmail());
		mapeo.put("fotoUrl", paciente.getFotoUrl());

		ref.updateChildrenAsync(mapeo);
	}

	@Override
	public void eliminarPaciente(String idpaciente) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Paciente loginPaciente(Paciente paciente) {
		DataSnapshot pacientesSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("pacientes"));

        if (!pacientesSnapshot.exists()) {
            log.error("Error al leer la data de medicos");
            return null;
        }

        for (DataSnapshot objPaciente : pacientesSnapshot.getChildren()) {
            Paciente nuevoPaciente = instanciarPaciente(objPaciente);
            
            if (nuevoPaciente.getPassword()!=null) {
            	if (nuevoPaciente.getDni().equals(paciente.getDni()) && 
                	nuevoPaciente.getPassword().equals(paciente.getPassword())) {
                	
                    return nuevoPaciente;
                }
			}
        }

        return null;
	}

	@Override
	public void cambiarPassword(Paciente paciente) {
		log.trace("Entrando a cambiarPassword ...");
		DatabaseReference ref = databaseReference.child("pacientes").child(paciente.getIdPaciente());
		
		Map<String, Object> mapeo = new HashMap<>();
		mapeo.put("password", paciente.getPassword());

		ref.updateChildrenAsync(mapeo);
	}

}