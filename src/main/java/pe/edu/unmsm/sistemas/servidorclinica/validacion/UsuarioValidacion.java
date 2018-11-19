package pe.edu.unmsm.sistemas.servidorclinica.validacion;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Usuario;

public class UsuarioValidacion {
	public boolean ValidarRegistrar(Usuario usuario){
		if(usuario.getNombre().isEmpty()){
			return true;
		}else if (usuario.getApe_pat().isEmpty()) {
			return true;
		}else if (usuario.getDni().isEmpty()) {
			return true;
		}else if(usuario.getDireccion().isEmpty()){
			return true;
		}else if (usuario.getTelefono().isEmpty()) {
			return true;
		}else if (usuario.getPassword().isEmpty()) {
			return true;
		}else if (usuario.getNumero_emergencia().isEmpty()) {
			return true;
		}else if (usuario.getContacto_emergencia().isEmpty()) {
			return true;
		}
		
		return false;
	}
	
	public boolean ValidarActualizar(Usuario usuario){
		if(usuario.getNombre().isEmpty()){
			return true;
		}else if (usuario.getApe_pat().isEmpty()) {
			return true;
		}else if (usuario.getDni().isEmpty()) {
			return true;
		}else if(usuario.getDireccion().isEmpty()){
			return true;
		}else if (usuario.getTelefono().isEmpty()) {
			return true;
		}else if (usuario.getNumero_emergencia().isEmpty()) {
			return true;
		}else if (usuario.getContacto_emergencia().isEmpty()) {
			return true;
		}
		
		return false;
	}
}
