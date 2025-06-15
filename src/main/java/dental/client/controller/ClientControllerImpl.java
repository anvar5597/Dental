/**
 * Author: Anvar Olimov
 * User:user
 * Date:6/21/2024
 * Time:12:20 PM
 */


package dental.client.controller;

import dental.client.dto.ClientRequestDto;
import dental.client.dto.ClientResponseDto;
import dental.client.service.ClientService;
import dental.notification.service.NotificationService;
import dental.patient_history.service.PatientHistoryService;
import dental.payment.service.PaymentService;
import dental.utils.DefaultResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientControllerImpl implements ClientController{

    private final ClientService service;
    private final PatientHistoryService historyService;
    private final NotificationService notificationService;
    private final PaymentService paymentService;
    @Override
    public ResponseEntity<List<ClientResponseDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Override
    public ResponseEntity<ClientResponseDto> getOne(Long id) {
        return ResponseEntity.ok(service.getByID(id));
    }

    @Override
    public ResponseEntity<Integer> countClient() {

        return ResponseEntity.ok(service.countClient());
    }

    @Override
    public ResponseEntity<List<ClientResponseDto>> findDeleted() {

        return ResponseEntity.ok(service.findDeleted());
    }

    @Override
    public DefaultResponseDto create(ClientRequestDto dto) {
        return service.create(dto);
    }

    @Override
    public ClientResponseDto update(ClientRequestDto dto, Long id) {
        return service.update(dto,id);
    }

    @Override
    public ResponseEntity<String> passiveDelete(Long id) {
        return ResponseEntity.ok(service.passiveDelete(id));
    }


    @Transactional
    @Override
    public DefaultResponseDto delete(Long id) {
        historyService.deleteWithClient(id);
        notificationService.deleteWithClient(id);
        paymentService.deleteWithClient(id);
        return  service.delete(id);
    }
}
