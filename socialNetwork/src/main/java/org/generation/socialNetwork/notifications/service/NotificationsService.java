package org.generation.socialNetwork.notifications.service;

import lombok.AllArgsConstructor;
import org.generation.socialNetwork.notifications.model.Notifications;
import org.generation.socialNetwork.notifications.repository.NotificationsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationsService {
    private final NotificationsRepository notificationsRepository;

    public Notifications addNotification(Notifications notification){
        return  this.notificationsRepository.save(notification);
    }

    public List<Notifications> getAllNotifications(){
        return this.notificationsRepository.findAll();
    }

    public Notifications readAt(User userId ,LocalDateTime readAt){
        notificationsRepository
        return
    }
}
