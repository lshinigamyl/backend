/**
 * RQ- Manejador de Respuesta del Servicio
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RespuestaSimple {

    @JsonProperty("mensaje")
    private String mensaje;

    @JsonProperty("tipoObjeto")
    private String tipoObjeto;

    @JsonProperty("id")
    private String id;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipoObjeto() {
        return tipoObjeto;
    }

    public void setTipoObjeto(String tipoObjeto) {
        this.tipoObjeto = tipoObjeto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}