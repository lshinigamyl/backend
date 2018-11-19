/**
 * RQ- Mantenimiento de las Reservas de Cita
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.reservas.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Especialidad;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Medico;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Paciente;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Sede;

public class Reserva {

    @JsonProperty("idReserva")
    private String idReserva;

    @JsonProperty("idEspecialidad")
    private String idEspecialidad;

    @JsonProperty("especialidad")
    private Especialidad especialidad;

    @JsonProperty("idSede")
    private String idSede;

    @JsonProperty("sede")
    private Sede sede;

    @JsonProperty("idPaciente")
    private String idPaciente;

    @JsonProperty("paciente")
    private Paciente paciente;

    @JsonProperty("idMedico")
    private String idMedico;

    @JsonProperty("medico")
    private Medico medico;

    @JsonIgnore
    private LocalDate fechaObj;

    @JsonProperty("fecha")
    private String fecha;

    @JsonIgnore
    private LocalTime inicioObj;

    @JsonProperty("inicio")
    private String inicio;

    @JsonIgnore
    private LocalTime finObj;

    @JsonProperty("fin")
    private String fin;

    @JsonProperty("estado")
    private String estado;

    @JsonProperty("recordado")
    private boolean recordado;

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public String getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(String idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public String getIdSede() {
        return idSede;
    }

    public void setIdSede(String idSede) {
        this.idSede = idSede;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public LocalDate getFechaObj() {
        return fechaObj;
    }

    public void setFechaObj(LocalDate fechaObj) {
        this.fechaObj = fechaObj;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public LocalTime getInicioObj() {
        return inicioObj;
    }

    public void setInicioObj(LocalTime inicioObj) {
        this.inicioObj = inicioObj;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public LocalTime getFinObj() {
        return finObj;
    }

    public void setFinObj(LocalTime finObj) {
        this.finObj = finObj;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isRecordado() {
        return recordado;
    }

    public void setRecordado(boolean recordado) {
        this.recordado = recordado;
    }
}