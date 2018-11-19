/**
 * RQ- Mantenimiento de las reglas de Negocio
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.dao;

import java.time.LocalTime;

public interface NegocioRepository  {
    Integer getHorasLimiteRecordatorio();
    Integer getMinutosDuracionCita();
    LocalTime getHoraEntrada();
    LocalTime getHoraCierre();
}