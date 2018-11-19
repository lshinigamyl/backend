/**
 * RQ- Constantes del Sistema.
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.utils;

public interface Constantes{

    //Codigos de resultado de confirmacion
    public static final int CONFIRMACION_REALIZADA = 1;
    public static final int CONFIRMACION_NO_REALIZADA = 2;
    public static final int CONFIRMACION_PREVIA_ENCONTRADA = 3;

    //Estados de reservas
    public static final String ESTADO_RESERVADO = "reservado";
    public static final String ESTADO_ANULADO = "anulado";
    public static final String ESTADO_COMPLETADO = "completado";

    //Tipos de objetos
    public static final String TIPO_PACIENTE = "paciente";
    public static final String TIPO_RESERVA = "reserva";

}