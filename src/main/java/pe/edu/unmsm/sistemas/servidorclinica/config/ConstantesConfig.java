/**
 * RQ- Configuración Interna
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import pe.edu.unmsm.sistemas.servidorclinica.utils.FechaHoraUtils;

@Component
public class ConstantesConfig {

    @Value("${app.formatos.fecha}")
    private String formatoFecha_interno;

    @Value("${app.formatos.hora}")
    private String formatoHora_interno;

    @PostConstruct
    public void init() {
        FechaHoraUtils.setFechaFormatter(formatoFecha_interno);
        FechaHoraUtils.setHoraFormatter(formatoHora_interno);
    }
}