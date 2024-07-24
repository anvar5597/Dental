/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/16/2024
 * Time:4:44 PM
 */


package dental.notification.controller;

import dental.notification.dto.NotificationRequestDto;
import dental.notification.dto.NotificationRespondDto;
import dental.notification.service.NotificationService;
import dental.utils.DefaultResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class NotificationControllerImpl implements NotificationController {

    private final NotificationService service;

    @Override
    public DefaultResponseDto create(NotificationRequestDto notificationRequestDto) {
        return service.create(notificationRequestDto);
    }

    @Override
    public DefaultResponseDto update(NotificationRequestDto notificationRequestDto, Long id) {
        return service.update(notificationRequestDto, id);
    }

    @Override
    public NotificationRespondDto getById(Long id) {
        return service.getById(id);
    }

    @Override
    public List<NotificationRespondDto> getByDate(LocalDate date) {
        return service.getByDate(date);
    }

    @Override
    public List<NotificationRespondDto> getBetweenDate(LocalDate start, LocalDate end) {
        return service.getBetweenDate(start, end);
    }

    @Override
    public DefaultResponseDto delete(Long id) {
        return service.delete(id);
    }
}
