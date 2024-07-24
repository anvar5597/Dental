package dental.notification.controller;

import dental.notification.dto.NotificationRequestDto;
import dental.notification.dto.NotificationRespondDto;
import dental.utils.DefaultResponseDto;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/notification")
public interface NotificationController {
    @PostMapping("/create")
    DefaultResponseDto create(NotificationRequestDto notificationRequestDto);

    @PutMapping("/update")
    DefaultResponseDto update(NotificationRequestDto notificationRequestDto, Long id);

    @GetMapping("/get/{id}")
    NotificationRespondDto getById(@PathVariable Long id);

    @GetMapping("/get-by-date")
    List<NotificationRespondDto> getByDate(LocalDate date);

    @GetMapping("/get-by-between-days")
    List<NotificationRespondDto> getBetweenDate(LocalDate start, LocalDate end);

    @DeleteMapping("/delete/{id}")
    DefaultResponseDto delete(@PathVariable Long id);


}
