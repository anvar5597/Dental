/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/16/2024
 * Time:12:42 PM
 */


package dental.notification.service;

import dental.client.service.ClientService;
import dental.notification.dto.NotificationRequestDto;
import dental.notification.dto.NotificationRespondDto;
import dental.notification.entity.Notification;
import dental.notification.repository.NotificationRepository;
import dental.utils.DefaultResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;
    private final ClientService service;

    @Override
    public DefaultResponseDto create(NotificationRequestDto notificationRequestDto) {

        Notification notification = new Notification();
        notification.setClient(service.getClientByID(notificationRequestDto.getClientId()));
        notification.setNextVisit(notificationRequestDto.getNextVisit());

        repository.save(notification);

        return DefaultResponseDto.builder()
                .status(200)
                .message("Eslatma yaratildi")
                .build();
    }

    @Override
    public NotificationRespondDto toDto(Notification notification) {

        NotificationRespondDto dto = new NotificationRespondDto();
        dto.setId(notification.getId());
        dto.setClientName(notification.getClient().getName());
        dto.setClientLastName(notification.getClient().getLastName());
        dto.setNextVisit(notification.getNextVisit());

        return dto;
    }


    @Override
    public DefaultResponseDto update(NotificationRequestDto notificationRequestDto, Long id) {

        Optional<Notification> optionalNotification = repository.findById(id);
        if (optionalNotification.isEmpty()) {
            DefaultResponseDto.builder()
                    .status(400)
                    .message("Bunday estalma yo`q")
                    .build();
        }
        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            notification.setClient(service.getClientByID(notificationRequestDto.getClientId()));
            notification.setNextVisit(notificationRequestDto.getNextVisit());

            repository.save(notification);
        }
        return DefaultResponseDto.builder()
                .status(200)
                .message("Sizning" + id + "-raqamli eslatmangiz o`zgartirildi")
                .build();

    }


    @Override
    public NotificationRespondDto getById(Long id) {

        Notification notification;
        Optional<Notification> optionalNotification = repository.findById(id);
        if (optionalNotification.isEmpty()) {
            throw new EntityNotFoundException("Bunday " + id + " raqamli eslatma yo`q");
        }
        notification = optionalNotification.get();
        return this.toDto(notification);
    }

    @Override
    public List<NotificationRespondDto> getByDate(LocalDate date) {

        List<Notification> notifications = repository.findAllByNextVisit(date);
        List<NotificationRespondDto> respondDtos = new ArrayList<>();
        for (Notification notification : notifications) {
            respondDtos.add(toDto(notification));
        }
        return respondDtos;
    }

    @Override
    public List<NotificationRespondDto> getBetweenDate(LocalDate start, LocalDate end) {
        List<Notification> notifications = repository.findAllByNextVisitBetween(start, end);
        List<NotificationRespondDto> respondDtos = new ArrayList<>();
        for (Notification notification : notifications) {
            respondDtos.add(toDto(notification));
        }
        return respondDtos;
    }

    @Override
    public DefaultResponseDto delete(Long id) {

        Optional<Notification> optionalNotification = repository.findById(id);

        if (optionalNotification.isEmpty()){
            throw new EntityNotFoundException("Bunday " + id + " raqamli eslatma yo`q");
        }

        Notification notification = optionalNotification.get();
        notification.setDeleted(true);
        repository.save(notification);
        return DefaultResponseDto.builder()
                .status(200)
                .message("Eslatma o`chirildi")
                .build();
    }


}
