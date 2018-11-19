/**
 * RQ- Mantenimiento de las Reservas de Cita
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.reservas.negocio.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.edu.unmsm.sistemas.servidorclinica.reservas.dao.ReservasRepository;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.domain.Reserva;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.negocio.ReservasBusiness;
import pe.edu.unmsm.sistemas.servidorclinica.utils.Constantes;

@Component
public class ReservasBusinessImpl implements ReservasBusiness {

    private static final Logger log = LoggerFactory.getLogger(ReservasBusinessImpl.class);

    @Autowired
    private ReservasRepository reservasRepository;

    @Override
    public void moverReservasCompletadas() {
        List<Reserva> listaReservas = reservasRepository.listarReservasxIdPaciente(null, Constantes.ESTADO_RESERVADO, null, null);

        for (Reserva reserva : listaReservas) {
            LocalDateTime fechaHoraReserva = LocalDateTime.of(reserva.getFechaObj(), reserva.getInicioObj());
            LocalDateTime ahora = LocalDateTime.now(ZoneId.of("-05:00"));
            long nowEpoch = ahora.toEpochSecond(ZoneOffset.UTC);

            if (fechaHoraReserva.toEpochSecond(ZoneOffset.UTC) - nowEpoch <= 0) {
                log.info("Cambiando el estado de reserva {} a completada", reserva.getIdReserva());
                reservasRepository.completarReserva(reserva.getIdReserva());
            }
        }
    }
}