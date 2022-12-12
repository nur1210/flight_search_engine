package fontys.s3.backend.configuration.websocket;

import fontys.s3.backend.domain.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

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

        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }

    public void sendPrivateNotification(final String email, final String text, final String title, final Map<String, String> queryParam) {
        Notification notification = Notification.builder()
                .date(String.format("%tT", new Date()))
                .title(title)
                .text(text)
                .to(email)
                .queryParam(queryParam)
                .build();

        messagingTemplate.convertAndSendToUser(email,"/topic/specific-notifications", notification);
    }
}
