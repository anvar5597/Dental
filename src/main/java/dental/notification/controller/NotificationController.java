package dental.notification.controller;

import dental.notification.dto.NotificationRequestDto;
import dental.notification.dto.NotificationRespondDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/notification")
public interface NotificationController {
    @PostMapping("/create")
    ResponseEntity<String> create(NotificationRequestDto notificationRequestDto);

    @PutMapping("/update")
    ResponseEntity<String> update(NotificationRequestDto notificationRequestDto, Long id);

    @GetMapping("/get/{id}")
    ResponseEntity<NotificationRespondDto> getById(@PathVariable Long id);

    @GetMapping("/find-all")
    ResponseEntity<List<NotificationRespondDto>> findAll();

    @GetMapping("/get-by-date")
    ResponseEntity<List<NotificationRespondDto>> getByDate(LocalDate date);

    @GetMapping("/get-by-between-days")
    ResponseEntity<List<NotificationRespondDto>> getBetweenDate(LocalDate start, LocalDate end);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> delete(@PathVariable Long id);


}
