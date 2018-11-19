/**
 * RQ- Mantenimiento de las Reglas de Negocio
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.impl;

import java.time.LocalTime;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.edu.unmsm.sistemas.servidorclinica.administracion.dao.NegocioRepository;
import pe.edu.unmsm.sistemas.servidorclinica.utils.FirebaseUtils;

@Repository
public class NegocioRepositoryImpl implements NegocioRepository {

    private static final Logger log = LoggerFactory.getLogger(NegocioRepositoryImpl.class);

    @Autowired
    private DatabaseReference databaseReference;

    @Override
    public Integer getHorasLimiteRecordatorio() {
        return getReglaComoInteger("horas-limite-recordatorio");
    }

    @Override
    public Integer getMinutosDuracionCita() {
        return getReglaComoInteger("minutos-duracion-cita");
    }

    private Integer getReglaComoInteger(String regla){
        log.info("Leyendo la hoja reglas-negocio ...");
        DataSnapshot reglasSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("reglas-negocio"));

        if (reglasSnapshot == null) {
            log.error("Error al leer la data de reglas-negocio");
            return null;
        }

        return FirebaseUtils.getInteger(reglasSnapshot, regla);
    }

	@Override
	public LocalTime getHoraEntrada() {
        return getReglaComoLocalTime("hora-entrada");
	}

	@Override
	public LocalTime getHoraCierre() {
        return getReglaComoLocalTime("hora-cierre");
    }
    
    private LocalTime getReglaComoLocalTime(String regla){
        log.info("Leyendo la hoja reglas-negocio ...");
        DataSnapshot reglasSnapshot = FirebaseUtils.getDataSnapshot(databaseReference.child("reglas-negocio"));

        if (reglasSnapshot == null) {
            log.error("Error al leer la data de reglas-negocio");
            return null;
        }

        return FirebaseUtils.getHora(reglasSnapshot, regla);
    }
}