package fontys.s3.backend.controller;

import fontys.s3.backend.configuration.websocket.NotificationService;
import fontys.s3.backend.domain.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class NotificationController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final SimpUserRegistry simpUserRegistry;
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(SimpMessagingTemplate simpMessagingTemplate, SimpUserRegistry simpUserRegistry, NotificationService notificationService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.simpUserRegistry = simpUserRegistry;
        this.notificationService = notificationService;
    }

    // /app/application
    @MessageMapping("/notification")
    @SendTo("/topic/notifications") //subscribe to /all/notification
    public Notification send(final Notification notification) {
        return notification;
    }

    // /app/notification
    @MessageMapping("/specific-notification")
    @SendToUser("/topic/specific-notifications") //subscribe to /specific/notification
    public void sendNotification(@Payload Notification notification, Principal principal) {
        simpMessagingTemplate.convertAndSendToUser(notification.getTo(), "/topic/specific-notifications", notification);
    }

/*    @PostMapping("/notification/specific")
    public void sendPrivateNotification(@RequestBody final Notification notification) {
        notificationService.sendPrivateNotification(notification.getTo(), notification.getText());
    }

    @PostMapping("/notification/all")
    public void sendGlobalNotification(@RequestBody final Notification notification) {
        notificationService.sendGlobalNotification(notification.getText());
    }*/
}
