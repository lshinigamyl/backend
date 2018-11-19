/**
 * RQ- Mantenimiento de las Reservas de Cita
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.reservas.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RespuestaReservas {

    @JsonProperty("data")
    private List<Reserva> data;

    public List<Reserva> getData() {
        return data;
    }

    public void setData(List<Reserva> data) {
        this.data = data;
    }
}