/**
 * RQ- Mantenimiento de los Pacientes
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Paciente {

    @JsonProperty("idPaciente")
    private String idPaciente;

    @JsonProperty("nombre")
    private String nombre;
    
    @JsonProperty("ape_pat")
	private String ape_pat;
	
	@JsonProperty("ape_mat")
	private String ape_mat;

    @JsonProperty("dni")
    private String dni;
    
    @JsonProperty("direccion")
	private String direccion;
	
	@JsonProperty("fec_nac")
	private String fec_nac;
	
	@JsonProperty("sexo")
	private String sexo;
	
	@JsonProperty("telefono")
	private String telefono;
	
	@JsonProperty("numero_emergencia")
	private String numero_emergencia;
	
	@JsonProperty("contacto_emergencia")
	private String contacto_emergencia;

    @JsonProperty("email")
    private String email;
    
    @JsonProperty("password")
	private String password;

    @JsonProperty("fotoUrl")
    private String fotoUrl;

    public String getApe_pat() {
		return ape_pat;
	}

	public void setApe_pat(String ape_pat) {
		this.ape_pat = ape_pat;
	}

	public String getApe_mat() {
		return ape_mat;
	}

	public void setApe_mat(String ape_mat) {
		this.ape_mat = ape_mat;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getFec_nac() {
		return fec_nac;
	}

	public void setFec_nac(String fec_nac) {
		this.fec_nac = fec_nac;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNumero_emergencia() {
		return numero_emergencia;
	}

	public void setNumero_emergencia(String numero_emergencia) {
		this.numero_emergencia = numero_emergencia;
	}

	public String getContacto_emergencia() {
		return contacto_emergencia;
	}

	public void setContacto_emergencia(String contacto_emergencia) {
		this.contacto_emergencia = contacto_emergencia;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}