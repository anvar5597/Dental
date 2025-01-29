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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class NotificationControllerImpl implements NotificationController {

    private final NotificationService service;


    @Override
    public ResponseEntity<String> create(NotificationRequestDto notificationRequestDto) {
        return service.create(notificationRequestDto);
    }

    @Override
    public ResponseEntity<String> update(NotificationRequestDto notificationRequestDto, Long id) {
        return service.update(notificationRequestDto, id);
    }

    @Override
    public ResponseEntity<NotificationRespondDto> getById(Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Override
    public ResponseEntity<List<NotificationRespondDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Override
    public ResponseEntity<List<NotificationRespondDto>> getByDate(LocalDate date) {
        return ResponseEntity.ok(service.getByDate(date));
    }

    @Override
    public ResponseEntity<List<NotificationRespondDto>> getBetweenDate(LocalDate start, LocalDate end) {
        return ResponseEntity.ok(service.getBetweenDate(start, end));
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
