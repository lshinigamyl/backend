/**
 * RQ- Mantenimiento de los Médicos
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Horario {

    @JsonProperty("lunes")
    private Turno lunes;

    @JsonProperty("martes")
    private Turno martes;

    @JsonProperty("miercoles")
    private Turno miercoles;

    @JsonProperty("jueves")
    private Turno jueves;

    @JsonProperty("viernes")
    private Turno viernes;

    @JsonProperty("sabado")
    private Turno sabado;

    public Turno getLunes() {
        return lunes;
    }

    public void setLunes(Turno lunes) {
        this.lunes = lunes;
    }

    public Turno getMartes() {
        return martes;
    }

    public void setMartes(Turno martes) {
        this.martes = martes;
    }

    public Turno getMiercoles() {
        return miercoles;
    }

    public void setMiercoles(Turno miercoles) {
        this.miercoles = miercoles;
    }

    public Turno getJueves() {
        return jueves;
    }

    public void setJueves(Turno jueves) {
        this.jueves = jueves;
    }

    public Turno getViernes() {
        return viernes;
    }

    public void setViernes(Turno viernes) {
        this.viernes = viernes;
    }

    public Turno getSabado() {
        return sabado;
    }

    public void setSabado(Turno sabado) {
        this.sabado = sabado;
    }

}