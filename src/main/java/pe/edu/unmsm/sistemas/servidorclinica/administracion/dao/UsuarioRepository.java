package pe.edu.unmsm.sistemas.servidorclinica.administracion.dao;

import java.util.List;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Usuario;

public interface UsuarioRepository {
	public List<Usuario> listarUsuarios();
	public Usuario crearUsuario(Usuario usuario);
	public void actualizarUsuario(Usuario usuario);
	public Usuario obtenerUsuario(String idusuario);
	public void eliminarUsuario(String idusuario);
	
	// Login
	public Usuario loginUsuario(Usuario usuario);
	public void cambiarPassword(Usuario usuario);
}
