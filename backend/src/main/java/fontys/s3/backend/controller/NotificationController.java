package fontys.s3.backend.controller;

import fontys.s3.backend.business.exception.UnauthorizedDataAccessException;
import fontys.s3.backend.configuration.websocket.NotificationService;
import fontys.s3.backend.domain.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class NotificationController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(SimpMessagingTemplate simpMessagingTemplate, NotificationService notificationService ) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.notificationService = notificationService;
    }

    @MessageMapping("/notification")
    @SendTo("/topic/notifications")
    public Notification send(final Notification notification) {
        return notification;
    }

    @MessageMapping("/specific-notification")
    @SendToUser("/topic/specific-notifications")
    public void sendNotification(@Payload Notification notification) {
        simpMessagingTemplate.convertAndSendToUser(notification.getTo(), "/topic/specific-notifications", notification);
    }

    @GetMapping("/notifications/online-users")
    public ResponseEntity<List<String>> getOnlineUsers() {
        try {
            var response = notificationService.getOnlineUsers();
            return ResponseEntity.ok(response);
        } catch (UnauthorizedDataAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
