package org.generation.socialNetwork.notifications.repository;

import org.generation.socialNetwork.notifications.model.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationsRepository extends JpaRepository<Notifications, Long> {
}
