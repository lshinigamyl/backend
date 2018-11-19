/**
 * RQ- Mantenimiento de los Médicos
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.domain;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Turno {

    @JsonProperty("inicio")
    private String inicioString;

    @JsonIgnore
    private LocalTime inicio;

    @JsonProperty("fin")
    private String finString;

    @JsonIgnore
    private LocalTime fin;

    public String getInicioString() {
        return inicioString;
    }

    public void setInicioString(String inicioString) {
        this.inicioString = inicioString;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }

    public String getFinString() {
        return finString;
    }

    public void setFinString(String finString) {
        this.finString = finString;
    }

    public LocalTime getFin() {
        return fin;
    }

    public void setFin(LocalTime fin) {
        this.fin = fin;
    }

}