package pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.UsuarioRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Especialidad;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Medico;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Paciente;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Usuario;
import pe.edu.unmsm.sistemas.servidorclinica.utils.FirebaseUtils;
//import scala.annotation.meta.setter;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {
	
	private static final Logger log = LoggerFactory.getLogger(UsuarioRepositoryImpl.class);
	
	@Autowired
    private DatabaseReference databaseReference;

    private Usuario instanciarUsuario(DataSnapshot usuarioSnapshot) {
        Usuario usuario = new Usuario();
        
        //usuario.setIdUsuario(usuarioSnapshot.getKey());
        usuario.setIdUsuario(FirebaseUtils.getString(usuarioSnapshot, "dni"));
        usuario.setNombre(FirebaseUtils.getString(usuarioSnapshot, "nombre"));
        usuario.setApe_pat(FirebaseUtils.getString(usuarioSnapshot, "ape_pat"));
        usuario.setApe_mat(FirebaseUtils.getString(usuarioSnapshot, "ape_mat"));
        usuario.setDni(FirebaseUtils.getString(usuarioSnapshot, "dni"));
        usuario.setDireccion(FirebaseUtils.getString(usuarioSnapshot, "direccion"));
        usuario.setFec_nac(FirebaseUtils.getString(usuarioSnapshot, "fec_nac"));
        usuario.setSexo(FirebaseUtils.getString(usuarioSnapshot, "sexo"));
        usuario.setTelefono(FirebaseUtils.getString(usuarioSnapshot, "telefono"));
        usuario.setNumero_emergencia(FirebaseUtils.getString(usuarioSnapshot, "numero_emergencia"));
        usuario.setContacto_emergencia(FirebaseUtils.getString(usuarioSnapshot, "contacto_emergencia"));
        usuario.setEmail(FirebaseUtils.getString(usuarioSnapshot, "email"));
        usuario.setPassword(FirebaseUtils.getString(usuarioSnapshot, "password"));
        usuario.setTipo_usuario(FirebaseUtils.getString(usuarioSnapshot, "tipo_usuario"));

        return usuario;
    }
	
	@Override
	public List<Usuario> listarUsuarios() {
		log.info("Leyendo la hoja usuario ...");
        DataSnapshot usuarioSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("usuario"));

        List<Usuario> listaUsuarios = new ArrayList<>();

        if (!usuarioSnapshot.exists()) {
            log.error("Error al leer la data de usuarios");
            return listaUsuarios;
        }

        for (DataSnapshot usuario : usuarioSnapshot.getChildren()) {
            Usuario nuevoUsuario = instanciarUsuario(usuario);

            listaUsuarios.add(nuevoUsuario);
        }

        return listaUsuarios;
	}

	@Override
	public Usuario crearUsuario(Usuario usuario) {
		log.trace("Entrando a crearUsuario ...");
        DatabaseReference ref = databaseReference.child("usuario").child(usuario.getIdUsuario());
        ref.child("nombre").setValueAsync(usuario.getNombre());
        ref.child("ape_pat").setValueAsync(usuario.getApe_pat());
        ref.child("ape_mat").setValueAsync(usuario.getApe_mat());
        ref.child("dni").setValueAsync(usuario.getDni());
        ref.child("direccion").setValueAsync(usuario.getDireccion());
        ref.child("fec_nac").setValueAsync(usuario.getFec_nac());
        ref.child("sexo").setValueAsync(usuario.getSexo());
        ref.child("telefono").setValueAsync(usuario.getTelefono());
        ref.child("numero_emergencia").setValueAsync(usuario.getNumero_emergencia());
        ref.child("contacto_emergencia").setValueAsync(usuario.getContacto_emergencia());
        ref.child("email").setValueAsync(usuario.getEmail());
        ref.child("password").setValueAsync(usuario.getPassword());
        ref.child("tipo_usuario").setValueAsync(usuario.getTipo_usuario());
        
        return usuario;
	}

	@Override
	public void actualizarUsuario(Usuario usuario) {
		log.trace("Entrando a actualizarUsuario ...");
		DatabaseReference ref = databaseReference.child("usuario").child(usuario.getIdUsuario());
		
		Map<String, Object> mapeo = new HashMap<>();
		mapeo.put("nombre", usuario.getNombre());
		mapeo.put("ape_pat", usuario.getApe_pat());
		mapeo.put("ape_mat", usuario.getApe_mat());
		mapeo.put("dni", usuario.getDni());
		mapeo.put("direccion", usuario.getDireccion());
		mapeo.put("fec_nac", usuario.getFec_nac());
		mapeo.put("sexo", usuario.getSexo());
		mapeo.put("telefono", usuario.getTelefono());
		mapeo.put("numero_emergencia", usuario.getNumero_emergencia());
		mapeo.put("contacto_emergencia", usuario.getContacto_emergencia());
		mapeo.put("email", usuario.getEmail());
		mapeo.put("tipo_usuario", usuario.getTipo_usuario());

		ref.updateChildrenAsync(mapeo);
	}

	@Override
	public Usuario obtenerUsuario(String idusuario) {
		DataSnapshot usuarioSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("usuario"));

        if (!usuarioSnapshot.exists()) {
            log.error("Error al leer la data de usuarios");
            return null;
        }

        for (DataSnapshot usuario : usuarioSnapshot.getChildren()) {
            Usuario nuevoUsuario = instanciarUsuario(usuario);

            if (nuevoUsuario.getIdUsuario().equals(idusuario)) {
                return nuevoUsuario;
            }
        }

        return null;
	}

	@Override
	public void eliminarUsuario(String idusuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Usuario loginUsuario(Usuario usuario) {
		DataSnapshot usuarioSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("usuario"));

        if (!usuarioSnapshot.exists()) {
            log.error("Error al leer la data de usuarios");
            return null;
        }

        for (DataSnapshot usu : usuarioSnapshot.getChildren()) {
            Usuario nuevoUsuario = instanciarUsuario(usu);

            if (nuevoUsuario.getDni().equals(usuario.getDni()) && 
            	nuevoUsuario.getPassword().equals(usuario.getPassword())) {
                return nuevoUsuario;
            }
        }

        return null;
	}

	@Override
	public void cambiarPassword(Usuario usuario) {
		log.trace("Entrando a cambiarPassword ...");
		DatabaseReference ref = databaseReference.child("usuario").child(usuario.getIdUsuario());
		
		Map<String, Object> mapeo = new HashMap<>();
		mapeo.put("password", usuario.getPassword());

		ref.updateChildrenAsync(mapeo);
	}
}
