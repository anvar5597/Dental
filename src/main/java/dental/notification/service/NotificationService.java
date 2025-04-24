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

    List<NotificationRespondDto> findAll();

    NotificationRespondDto getById(Long id);

    List<NotificationRespondDto> getByDate(LocalDate date);

    List<NotificationRespondDto> getBetweenDate(LocalDate start, LocalDate end);

    void deleteWithClient(Long id);
    String delete(Long id);
}
