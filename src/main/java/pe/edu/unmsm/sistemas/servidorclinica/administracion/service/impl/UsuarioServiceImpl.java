package pe.edu.unmsm.sistemas.servidorclinica.administracion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.PacientesRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.UsuarioRepository;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Usuario;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.service.UsuarioService;
import pe.edu.unmsm.sistemas.servidorclinica.validacion.UsuarioValidacion;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
    private UsuarioRepository usuarioRepository;
	
	@Override
	public List<Usuario> listarUsuarios() {
		// Metodo Listar usuarios.
		return usuarioRepository.listarUsuarios();
	}

	@Override
	public Usuario crearUsuario(Usuario usuario) {
		// Validaciones de Negocio.
		UsuarioValidacion val = new UsuarioValidacion();
		
		if (val.ValidarRegistrar(usuario)) {
			return null;
		}
		
		// Registramos nuevo ususario.
		return usuarioRepository.crearUsuario(usuario);
	}

	@Override
	public void actualizarUsuario(Usuario usuario) {
		// Validaciones de Negocio.
		UsuarioValidacion val = new UsuarioValidacion();
		
		if (!val.ValidarActualizar(usuario)) {
			// Actualizamos ususario.
			usuarioRepository.actualizarUsuario(usuario);
		}
	}

	@Override
	public Usuario obtenerUsuario(String idusuario) {
		// Validaciones de Negocio
		if (idusuario.isEmpty()) {
			return null;
		}
		
		// Obtenemos usuario x id.
		return usuarioRepository.obtenerUsuario(idusuario);
	}

	@Override
	public void eliminarUsuario(String idusuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Usuario loginUsuario(Usuario usuario) {
		// Validaciones de Negocio
		if (usuario.getDni().isEmpty()) {
			return null;
		}else if (usuario.getPassword().isEmpty()) {
			return null;
		}
		
		// Obtenemos usuario x id.
		return usuarioRepository.loginUsuario(usuario);
	}

	@Override
	public void cambiarPassword(Usuario usuario) {
		// Validaciones de Negocio.
		if (!usuario.getIdUsuario().isEmpty() && !usuario.getPassword().isEmpty()){
			// Actualizamos ususario.
			usuarioRepository.cambiarPassword(usuario);	
		}
	}
}
