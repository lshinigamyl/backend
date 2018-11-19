/**
 * RQ- Mantenimiento de las Especialidades
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RespuestaEspecialidades {

    @JsonProperty("data")
    private List<Especialidad> data;

    public List<Especialidad> getData() {
        return data;
    }

    public void setData(List<Especialidad> data) {
        this.data = data;
    }
}