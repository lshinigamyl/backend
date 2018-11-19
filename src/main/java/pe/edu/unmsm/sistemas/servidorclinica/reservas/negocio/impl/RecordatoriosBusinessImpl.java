/**
 * RQ- Mantenimiento de las Reservas de Cita
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.reservas.negocio.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.NegocioRepository;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.dao.ReservasRepository;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.domain.Reserva;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.negocio.RecordatoriosBusiness;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.service.RecordatoriosService;
import pe.edu.unmsm.sistemas.servidorclinica.utils.Constantes;

@Component
public class RecordatoriosBusinessImpl implements RecordatoriosBusiness {

    private static final Logger log = LoggerFactory.getLogger(RecordatoriosBusinessImpl.class);

    @Autowired
    private NegocioRepository negocioRepository;

    @Autowired
    private RecordatoriosService recordatoriosService;

    @Autowired
    private ReservasRepository reservasRepository;

    @Override
    public void enviarRecordatoriosDiaAnterior() {
        int horasLimiteRecordatorio = negocioRepository.getHorasLimiteRecordatorio();
        List<Reserva> listaReservas = reservasRepository.listarReservasxIdPaciente(null, Constantes.ESTADO_RESERVADO, null, null);
        LocalDateTime ahora = LocalDateTime.now(ZoneId.of("-05:00"));
        long nowEpoch = ahora.toEpochSecond(ZoneOffset.UTC);
        log.debug("LocalDateTime ahora: {}", ahora);

        for (Reserva reserva : listaReservas) {
            log.info("Leyendo reserva {}", reserva.getIdReserva());

            LocalDateTime horaCita = LocalDateTime.of(reserva.getFechaObj(), reserva.getInicioObj());

            if (!reserva.isRecordado()
                    && horaCita.toEpochSecond(ZoneOffset.UTC) - nowEpoch < horasLimiteRecordatorio * 60 * 60L) {
                log.info("Se debe enviar recordatorio de reserva");
                recordatoriosService.enviarRecordatoriosDiaAnterior(reserva);
            } else {
                log.info("Aun no se debe enviar recordatorio");
            }

        }
    }

    @Override
    public void enviarRecordatoriosMismoDia() {
        List<Reserva> listaReservas = reservasRepository.listarReservasxIdPaciente(null, Constantes.ESTADO_RESERVADO, null, null);
        LocalDateTime ahora = LocalDateTime.now(ZoneId.of("-05:00"));
        log.debug("Hoy es: {}", ahora);

        for (Reserva reserva : listaReservas) {
            log.info("Leyendo reserva {}", reserva.getIdReserva());

            LocalDate fechaCita = reserva.getFechaObj();

            if (fechaCita.equals(ahora.toLocalDate())) {
                log.info("Se debe enviar recordatorio de reserva");
                recordatoriosService.enviarRecordatoriosMismoDia(reserva);
            } else {
                log.info("Aun no se debe enviar recordatorio");
            }

        }
    }
}