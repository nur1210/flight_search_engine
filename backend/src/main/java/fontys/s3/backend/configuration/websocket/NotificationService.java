package fontys.s3.backend.configuration.websocket;

import fontys.s3.backend.domain.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendGlobalNotification(String text) {
        Notification notification = Notification.builder()
                .text(text)
                .build();

        messagingTemplate.convertAndSend("/all/notification", notification);
    }

    public void sendPrivateNotification(final String email, final String text) {
        Notification notification = Notification.builder()
                .text(text)
                .to(email)
                .build();

        messagingTemplate.convertAndSendToUser(email,"/specific/notification", notification);
    }
}
