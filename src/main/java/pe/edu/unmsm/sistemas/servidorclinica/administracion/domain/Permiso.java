/**
 * RQ- Mantenimiento de Usuarios
 * @author: Jorge Velásquez Ramos
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Permiso {
	
	@JsonProperty("idPermiso")
    private String idPermiso;
	
	@JsonProperty("nombre")
	private String nombre;
	
	@JsonProperty("descripcion")
	private String descripcion;

	public String getIdPermiso() {
		return idPermiso;
	}

	public void setIdPermiso(String idPermiso) {
		this.idPermiso = idPermiso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
