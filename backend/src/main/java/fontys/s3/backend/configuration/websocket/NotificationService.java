package fontys.s3.backend.configuration.websocket;

import fontys.s3.backend.business.exception.UnauthorizedDataAccessException;
import fontys.s3.backend.domain.model.AccessToken;
import fontys.s3.backend.domain.model.Notification;
import fontys.s3.backend.persistence.entity.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;
    private final SimpUserRegistry userRegistry;
    private final AccessToken requestToken;


    @Autowired
    public NotificationService(SimpMessagingTemplate messagingTemplate, SimpUserRegistry userRegistry, AccessToken requestToken) {
        this.messagingTemplate = messagingTemplate;
        this.userRegistry = userRegistry;
        this.requestToken = requestToken;
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

        messagingTemplate.convertAndSendToUser(email, "/topic/specific-notifications", notification);
    }

    public List<String> getOnlineUsers() {
        if (requestToken.hasRole(RoleEnum.ADMIN.name())) {
            List<String> users = new ArrayList<>();
            userRegistry.getUsers().forEach(user -> users.add(user.getName()));
            return users;
        }
        throw new UnauthorizedDataAccessException("USER_IS_NOT_ADMIN");
    }
}
