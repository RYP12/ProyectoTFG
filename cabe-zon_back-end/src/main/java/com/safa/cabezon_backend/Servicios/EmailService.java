package com.safa.cabezon_backend.Servicios;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Async
    public void enviarCorreoVerificacion(String to, String token) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Verifica tu cuenta en Cabe-zon");

            // Angular cogerá este token y llamará al backend.
            String url = "http://localhost:4200/verificar?token=" + token;

            String htmlContent = """
                <div style='font-family: Arial, sans-serif; padding: 20px;'>
                    <h2>Bienvenido a Cabe-zon</h2>
                    <p>Gracias por registrarte. Para activar tu cuenta, haz clic en el botón:</p>
                    <a href='%s' style='background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>Activar Cuenta</a>
                    <p>Si no funciona el botón, copia este enlace: %s</p>
                    <p><small>Este enlace caduca en 15 minutos.</small></p>
                </div>
                """.formatted(url, url);

            helper.setText(htmlContent, true);
            javaMailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo de verificación", e);
        }
    }
}
