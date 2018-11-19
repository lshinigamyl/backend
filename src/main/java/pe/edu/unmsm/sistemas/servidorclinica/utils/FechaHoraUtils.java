/**
 * RQ- Formato de Fecha y Hora
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FechaHoraUtils {

    private static final Logger log = LoggerFactory.getLogger(FechaHoraUtils.class);

    private static DateTimeFormatter fechaFormatter;
    private static DateTimeFormatter horaFormatter;

    public static void setFechaFormatter(String formato) {
        FechaHoraUtils.fechaFormatter = DateTimeFormatter.ofPattern(formato);
    }

    public static void setHoraFormatter(String formato) {
        FechaHoraUtils.horaFormatter = DateTimeFormatter.ofPattern(formato);
    }

    public static LocalDate parseFecha(String fecha) {
        try {
            return LocalDate.parse(fecha, fechaFormatter);
        } catch (Exception e) {
            log.error("No se puede parsear Fecha", e);
        }
        return null;
    }

    public static LocalTime parseHora(String hora) {
        try {
            return LocalTime.parse(hora, horaFormatter);
        } catch (Exception e) {
            log.error("No se puede parsear Hora", e);
        }
        return null;
    }

    public static String writeFecha(LocalDate fecha) {
        return fechaFormatter.format(fecha);
    }

    public static String writeHora(LocalTime hora) {
        return horaFormatter.format(hora);
    }
}