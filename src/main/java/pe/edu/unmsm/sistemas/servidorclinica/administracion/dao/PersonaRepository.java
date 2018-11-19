package pe.edu.unmsm.sistemas.servidorclinica.administracion.dao;

import java.util.List;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Persona;

public interface PersonaRepository {
	// CRUD => CREATE, READ, UPDATE AND DELETE
	// public int suma(); =====> Funcion
	// public void crear() ====> procedimiento
	
	public Persona crearPersona(Persona persona); // Es una funcion que me devuelve un objeto Persona
	public List<Persona> listarPersona();
	public Persona buscarPersona(String idPersona);
	public void actualizarPersona(Persona persona);
	public void eliminarPersona(Persona persona);
}
