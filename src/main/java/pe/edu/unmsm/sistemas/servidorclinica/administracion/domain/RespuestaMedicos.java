/**
 * RQ- Mantenimiento de los Medicos
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RespuestaMedicos {

    @JsonProperty("data")
    private List<Medico> data;

    public List<Medico> getData() {
        return data;
    }

    public void setData(List<Medico> data) {
        this.data = data;
    }
}