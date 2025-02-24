/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/16/2024
 * Time:12:42 PM
 */


package dental.notification.service;

import dental.client.entity.Client;
import dental.client.service.ClientService;
import dental.notification.dto.NotificationRequestDto;
import dental.notification.dto.NotificationRespondDto;
import dental.notification.entity.Notification;
import dental.notification.repository.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> create(NotificationRequestDto notificationRequestDto) {

        Notification notification = new Notification();

        Client client =service.getClientByID(notificationRequestDto.getClientId());
        if (client == null){
            return ResponseEntity.badRequest().body("Bunday mijoz yo`q");
        }
        notification.setClient(client);
        notification.setNextVisit(notificationRequestDto.getNextVisit());

        repository.save(notification);
        return ResponseEntity.ok("Eslatma yaratildi");
    }

    @Override
    public NotificationRespondDto toDto(Notification notification) {

        NotificationRespondDto dto = new NotificationRespondDto();
        dto.setId(notification.getId());
        dto.setClientName(notification.getClient().getName());
        dto.setClientLastName(notification.getClient().getLastName());
        dto.setPhoneNumber(notification.getClient().getPhoneNumber());
        dto.setNextVisit(notification.getNextVisit());

        return dto;
    }


    @Override
    public ResponseEntity<String> update(NotificationRequestDto notificationRequestDto, Long id) {

        Optional<Notification> optionalNotification = repository.findById(id);
        if (optionalNotification.isEmpty()) {
            return ResponseEntity.badRequest().body("Bunday " + id + " raqamli eslatma yo`q");
        }

        Notification notification = optionalNotification.get();
        Client client =service.getClientByID(notificationRequestDto.getClientId());
        if (client == null){
            return ResponseEntity.badRequest().body("Bunday mijoz yo`q");
        }
        notification.setClient(client);
        notification.setNextVisit(notificationRequestDto.getNextVisit());

        repository.save(notification);

        return ResponseEntity.ok("Sizning" + id + "-raqamli eslatmangiz o`zgartirildi");

    }

    @Override
    public List<NotificationRespondDto> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
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
        List<NotificationRespondDto> respondDto = new ArrayList<>();
        for (Notification notification : notifications) {
            respondDto.add(toDto(notification));
        }
        return respondDto;
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
    public void deleteWithClient(Long id) {
        List<Notification> notificationList = repository.findAll();
        for(Notification notification : notificationList){
            if (notification.getClient().getId().equals(id)){
                notification.setDeleted(true);
            }
        }
    }

    @Override
    public String delete(Long id) {

        Optional<Notification> optionalNotification = repository.findById(id);

        if (optionalNotification.isEmpty()) {
            return "Bunday " + id + " raqamli eslatma yo`q";
        }

        Notification notification = optionalNotification.get();
        notification.setDeleted(true);
        repository.save(notification);
        return "Mijoz o`chirildi";
    }


}
