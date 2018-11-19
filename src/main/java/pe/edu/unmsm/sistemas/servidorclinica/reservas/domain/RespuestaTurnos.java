/**
 * RQ- Mantenimiento de las Reservas de Cita
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.reservas.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RespuestaTurnos {

    @JsonProperty("duracion")
    private int duracion;

    @JsonProperty("data")
    private List<ParMedicoTurnos> data;

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public List<ParMedicoTurnos> getData() {
        return data;
    }

    public void setData(List<ParMedicoTurnos> data) {
        this.data = data;
    }
}