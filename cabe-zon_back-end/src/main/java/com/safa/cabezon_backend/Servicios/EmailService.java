package com.safa.cabezon_backend.Servicios;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    // Método para enviar correo usando un html
    public void enviarCorreo(String destinatario, String asunto, String contenidoHtml) {
        MimeMessage mensaje = javaMailSender.createMimeMessage();

        try {
            // true indica que es un mensaje MULTIPART (necesario para adjuntos o html complejo)
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setFrom("sirf2410@gmail.com");

            // true indica que el contenido es HTML
            helper.setText(contenidoHtml, true);

            javaMailSender.send(mensaje);

        } catch (MessagingException e) {
            // Manejo básico de errores
            e.printStackTrace();
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage());
        }
    }
}