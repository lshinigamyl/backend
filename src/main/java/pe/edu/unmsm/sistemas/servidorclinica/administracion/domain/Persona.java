package pe.edu.unmsm.sistemas.servidorclinica.administracion.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

// El mapeo de la Hoja Persona
public class Persona {
	@JsonProperty("id")
	public String idPersona;
	
	@JsonProperty("Nombre")
	public String Nombre;
	
	@JsonProperty("Apellido")
	public String Apellido;
	
	@JsonProperty("Edad")
	public int Edad;
	
	
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getApellido() {
		return Apellido;
	}
	public void setApellido(String apellido) {
		Apellido = apellido;
	}
	public int getEdad() {
		return Edad;
	}
	public void setEdad(int edad) {
		Edad = edad;
	}
}
