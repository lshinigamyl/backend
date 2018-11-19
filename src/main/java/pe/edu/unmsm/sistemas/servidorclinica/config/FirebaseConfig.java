/**
 * RQ- Parametros Generales
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.config;
/**
 * RQ- Configuración Interna
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */


import java.io.InputStream;

import javax.annotation.PostConstruct;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {

    private static final Logger log = LoggerFactory.getLogger(FirebaseConfig.class);

    @Bean
    public DatabaseReference firebaseDatabase() {
        DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
        return firebase;
    }

    @Value("${app.firebase.database-url}")
    private String databaseUrl;

    @Value("${app.firebase.auth-key-path}")
    private String authKeyPath;

    @PostConstruct
    public void init() {
    	System.out.println(authKeyPath+"");
    	
//        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("firebase_auth_key.json");
        try {
        	InputStream inputStream;
//        	inputStream = new ClassPathResource("credentials/firebase_auth_key.json").getInputStream();
        	inputStream = FirebaseConfig.class.getClassLoader().getResourceAsStream(authKeyPath);
        	GoogleCredentials googleCredentials = GoogleCredentials.fromStream(inputStream);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(googleCredentials)
                    .setDatabaseUrl(databaseUrl)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
        	e.printStackTrace();
            log.error("Error al iniciar la conexion a Firebase", e);
        }
    }
}