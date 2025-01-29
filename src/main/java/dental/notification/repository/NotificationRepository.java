package dental.notification.repository;

import dental.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification>  findAllByNextVisit(LocalDate date);

    List<Notification> findAllByNextVisitBetween(LocalDate start, LocalDate end);
}
