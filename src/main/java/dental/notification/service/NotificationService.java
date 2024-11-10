package dental.notification.service;

import dental.notification.dto.NotificationRequestDto;
import dental.notification.dto.NotificationRespondDto;
import dental.notification.entity.Notification;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface NotificationService {

    ResponseEntity<String> create(NotificationRequestDto notificationRequestDto);

    NotificationRespondDto toDto(Notification notification);

    ResponseEntity<String> update(NotificationRequestDto notificationRequestDto, Long id);

    ResponseEntity<List<NotificationRespondDto>> findAll();

    ResponseEntity<NotificationRespondDto> getById(Long id);

    ResponseEntity<List<NotificationRespondDto>> getByDate(LocalDate date);

    ResponseEntity<List<NotificationRespondDto>> getBetweenDate(LocalDate start, LocalDate end);

    ResponseEntity<String> delete(Long id);
}
