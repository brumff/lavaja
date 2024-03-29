package com.br.lavaja.services;

import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

@Service
public class FCMService {

        public void enviarNotServFinalizado(String token, String mensagem) {
            Message message = Message.builder()
        .setToken(token)
        .setNotification(Notification.builder()
            .setTitle("Seu veículo brilha novamente!")
            .setBody(mensagem)
            .build())
        .setAndroidConfig(AndroidConfig.builder()
            .setNotification(AndroidNotification.builder()
                .setIcon("ic_stat_onesignal_default")
                .build()) 
            .build()) 
        .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Notificação enviada com sucesso: " + response);
        } catch (FirebaseMessagingException e) {
            System.err.println("Erro ao enviar notificação: " + e.getMessage());
        }
    }

   
}