/**
 * RQ- Utilitarios de Firebase
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FirebaseUtils {

    private static final Logger log = LoggerFactory.getLogger(FirebaseUtils.class);

    public static String getString(DataSnapshot snapshot, String key) {
        return snapshot.child(key).exists() ? snapshot.child(key).getValue().toString() : null;
    }

    public static Integer getInteger(DataSnapshot snapshot, String key) {
        if (snapshot.child(key).exists()) {
            try {
                return Integer.parseInt(snapshot.child(key).getValue().toString());
            } catch (Exception e) {
                log.error("No se puede parsear Integer", e);
            }
        }
        return null;
    }

    public static Boolean getBoolean(DataSnapshot snapshot, String key) {
        if (snapshot.child(key).exists()) {
            if (snapshot.child(key).getValue() instanceof Boolean) {

                return (Boolean) snapshot.child(key).getValue();
            } else {
                log.error("No se puede parsear Boolean");
            }
        }
        return null;
    }

    public static LocalDate getFecha(DataSnapshot snapshot, String key) {
        return snapshot.child(key).exists() ? FechaHoraUtils.parseFecha(snapshot.child(key).getValue().toString())
                : null;
    }

    public static LocalTime getHora(DataSnapshot snapshot, String key) {
        return snapshot.child(key).exists() ? FechaHoraUtils.parseHora(snapshot.child(key).getValue().toString())
                : null;
    }

    public static DataSnapshot getDataSnapshot(DatabaseReference databaseReference) {
        log.trace("entrando a getDataSnapshot ...");

        final DataSnapshotWrapper snapshotWrapper = new DataSnapshotWrapper();
        final CountDownLatch latch = new CountDownLatch(1);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                snapshotWrapper.snapshot = dataSnapshot;
                log.debug("Datos leidos");
                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError dbError) {
                log.error("Error al leer los datos");
                latch.countDown();
            }
        });
        try {
            log.debug("Prelatch");
            latch.await();
            log.debug("Returning from latch");
            return snapshotWrapper.snapshot;
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private static class DataSnapshotWrapper {
        private DataSnapshot snapshot;
    }
}