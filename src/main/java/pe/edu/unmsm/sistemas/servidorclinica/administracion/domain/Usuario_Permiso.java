package pe.edu.unmsm.sistemas.servidorclinica.administracion.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Usuario_Permiso {
	
	@JsonProperty("idUsuario_Permiso")
	private String idUsuario_Permiso;
	
	@JsonProperty("idusuario")
	private Usuario idusuario;
	
	@JsonProperty("idpermisos")
	private Permiso idpermisos;
	
	@JsonProperty("estado")
	private String estado;

	public String getIdUsuario_Permiso() {
		return idUsuario_Permiso;
	}

	public void setIdUsuario_Permiso(String idUsuario_Permiso) {
		this.idUsuario_Permiso = idUsuario_Permiso;
	}

	public Usuario getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(Usuario idusuario) {
		this.idusuario = idusuario;
	}

	public Permiso getIdpermisos() {
		return idpermisos;
	}

	public void setIdpermisos(Permiso idpermisos) {
		this.idpermisos = idpermisos;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
