package com.br.lavaja.controls;

import com.br.lavaja.services.FCMService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notificacoes")
public class NotificationController {

    @PostMapping("/enviar")
    public String enviarNotificacao(@RequestParam String token, @RequestParam String mensagem) {
        Message message = Message.builder()
                .setToken(token)
                .setNotification(Notification.builder()
                        .setTitle("Título da Notificação")
                        .setBody(mensagem)
                        .build())
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            return "Notificação enviada com sucesso: " + response;
        } catch (FirebaseMessagingException e) {
            return "Erro ao enviar notificação: " + e.getMessage();
        }
    }

    

}
