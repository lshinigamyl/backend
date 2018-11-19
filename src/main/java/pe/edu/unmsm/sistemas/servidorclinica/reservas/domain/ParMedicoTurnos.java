/**
 * RQ- Mantenimiento de las Reservas de Cita
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.reservas.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Medico;
import pe.edu.unmsm.sistemas.servidorclinica.administracion.domain.Turno;

public class ParMedicoTurnos {

    @JsonProperty("idMedico")
    private String idMedico;

    @JsonProperty("medico")
    private Medico medico;

    @JsonProperty("turnos")
    private List<Turno> turnos;

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

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }
}