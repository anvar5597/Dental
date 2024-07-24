package dental.notification.service;

import dental.notification.dto.NotificationRequestDto;
import dental.notification.dto.NotificationRespondDto;
import dental.notification.entity.Notification;
import dental.utils.DefaultResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface NotificationService {

    DefaultResponseDto create(NotificationRequestDto notificationRequestDto);

    NotificationRespondDto toDto(Notification notification);

    DefaultResponseDto update(NotificationRequestDto notificationRequestDto, Long id);

    NotificationRespondDto getById(Long id);

    List<NotificationRespondDto> getByDate (LocalDate date);

    List<NotificationRespondDto> getBetweenDate(LocalDate start, LocalDate end);

    DefaultResponseDto delete(Long id);

}
