/**
 * RQ- Mantenimiento de las Reservas de Cita
 * @author: Grupo 01 - TP2
 * @version: 10/10/2018 1.0
 */

package pe.edu.unmsm.sistemas.servidorclinica.reservas.service.impl;

import java.io.InputStream;
import java.util.Scanner;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import pe.edu.unmsm.sistemas.servidorclinica.reservas.domain.Reserva;
import pe.edu.unmsm.sistemas.servidorclinica.reservas.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender sender;

    @Value("${app.email.plantillas.confirmacion-creacion}")
    private String confirmacionCreacionTemplatePath;

    @Value("${app.email.plantillas.recordatorio-dia-anterior}")
    private String recordatorioDiaAnteriorTemplatePath;

    @Value("${app.email.plantillas.recordatorio-mismo-dia}")
    private String recordatorioMismoDiaTemplatePath;

    @Value("${spring.mail.username}")
    private String sendmail;

    @Value("${app.email.alias}")
    private String alias;

    @Override
    public boolean enviarEmailConfirmacionCreacion(Reserva reserva) {
        InputStream inputStream = EmailServiceImpl.class.getClassLoader()
                .getResourceAsStream(confirmacionCreacionTemplatePath);
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        String email = "";

        try {
            String emailTemplate = "";
            while (scanner.hasNext()) {
                emailTemplate += scanner.next();
            }

            email = emailTemplate.replaceAll("%tag%nombre%tag%", reserva.getPaciente().getNombre())
                    .replaceAll("%tag%especialidad%tag%", reserva.getEspecialidad().getNombre())
                    .replaceAll("%tag%medico%tag%",
                            String.format("%s %s", reserva.getMedico().getNombre(), reserva.getMedico().getApellidos()))
                    .replaceAll("%tag%sede%tag%", reserva.getSede().getNombre())
                    .replaceAll("%tag%fecha%tag%", reserva.getFecha()).replaceAll("%tag%hora%tag%",
                            String.format("%s - %s", reserva.getInicio(), reserva.getFin()));
        } catch (Exception e) {
            log.error("Error en la lectura de la plantilla de correo", e);
        } finally {
            scanner.close();
        }

        return enviarEmail(reserva.getPaciente().getEmail(), email, "Confirmación de creación de reserva");
    }

    @Override
    public boolean enviarEmailRecordatorioDiaAnterior(Reserva reserva) {
        InputStream inputStream = EmailServiceImpl.class.getClassLoader()
                .getResourceAsStream(recordatorioDiaAnteriorTemplatePath);
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        String email = "";

        try {
            String emailTemplate = "";
            while (scanner.hasNext()) {
                emailTemplate += scanner.next();
            }

            email = emailTemplate.replaceAll("%tag%nombre%tag%", reserva.getPaciente().getNombre())
                    .replaceAll("%tag%horainicio%tag%", reserva.getInicio())
                    .replaceAll("%tag%especialidad%tag%", reserva.getEspecialidad().getNombre())
                    .replaceAll("%tag%medico%tag%",
                            String.format("%s %s", reserva.getMedico().getNombre(), reserva.getMedico().getApellidos()))
                    .replaceAll("%tag%sede%tag%", reserva.getSede().getNombre())
                    .replaceAll("%tag%fecha%tag%", reserva.getFecha()).replaceAll("%tag%hora%tag%",
                            String.format("%s - %s", reserva.getInicio(), reserva.getFin()));
        } catch (Exception e) {
            log.error("Error en la lectura de la plantilla de correo", e);
        } finally {
            scanner.close();
        }

        return enviarEmail(reserva.getPaciente().getEmail(), email, "Recordatorio de Reserva de Cita");
    }

    @Override
    public boolean enviarEmailRecordatorioMismoDia(Reserva reserva) {
        InputStream inputStream = EmailServiceImpl.class.getClassLoader()
                .getResourceAsStream(recordatorioMismoDiaTemplatePath);
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        String email = "";

        try {
            String emailTemplate = "";
            while (scanner.hasNext()) {
                emailTemplate += scanner.next();
            }

            email = emailTemplate.replaceAll("%tag%nombre%tag%", reserva.getPaciente().getNombre())
                    .replaceAll("%tag%horainicio%tag%", reserva.getInicio())
                    .replaceAll("%tag%especialidad%tag%", reserva.getEspecialidad().getNombre())
                    .replaceAll("%tag%medico%tag%",
                            String.format("%s %s", reserva.getMedico().getNombre(), reserva.getMedico().getApellidos()))
                    .replaceAll("%tag%sede%tag%", reserva.getSede().getNombre())
                    .replaceAll("%tag%fecha%tag%", reserva.getFecha()).replaceAll("%tag%hora%tag%",
                            String.format("%s - %s", reserva.getInicio(), reserva.getFin()));
        } catch (Exception e) {
            log.error("Error en la lectura de la plantilla de correo", e);
        } finally {
            scanner.close();
        }

        return enviarEmail(reserva.getPaciente().getEmail(), email, "Recordatorio de Reserva de Cita");
    }

    private boolean enviarEmail(String destino, String mensaje, String asunto) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(destino);
            helper.setText(mensaje, true);
            helper.setSubject(asunto);
            helper.setFrom(sendmail, alias);
            sender.send(message);

            log.info("Mensaje enviado");
            return true;
        } catch (Exception e) {
            log.error("Error al enviar el mensaje", e);
            return false;
        }
    }
}
