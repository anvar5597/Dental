/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/10/2024
 * Time:12:13 PM
 */


package dental.payment.service;

import dental.patientHistory.entity.PatientHistoryEntity;
import dental.patientHistory.service.PatientHistoryService;
import dental.payment.dto.PaymentRequestDto;
import dental.payment.dto.PaymentResponseDto;
import dental.payment.entity.PaymentEntity;
import dental.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;
    private final PatientHistoryService historyService;

    @Override
    public String create(PaymentRequestDto dto) {
        PatientHistoryEntity patientHistory = historyService.getpPatientByid(dto.getId());
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setPaidValue(dto.getPaidValue());
        paymentEntity.setPatientHistoryEntity(patientHistory);
        paymentEntity.setPaidDate(LocalDate.now());
        repository.save(paymentEntity);

        Integer total = patientHistory.getTotal();
        Integer paid = patientHistory.getPaid() + dto.getPaidValue();
        patientHistory.setPaid(paid);

        if (total <= paid) {
            patientHistory.setIsPaid(true);
        }
        historyService.save(patientHistory);
        return paid + " so`m to`landi" + "\n" + (total - paid) + " so`m qoldi";
    }

    @Override
    public String update(PaymentRequestDto dto, Long id) {
        PatientHistoryEntity patientHistory = historyService.getpPatientByid(dto.getId());
        PaymentEntity paymentEntity = repository.findById(id).get();
        paymentEntity.setPaidValue(dto.getPaidValue());
        paymentEntity.setPatientHistoryEntity(patientHistory);
        paymentEntity.setPaidDate(LocalDate.now());
        repository.save(paymentEntity);

        Integer total = patientHistory.getTotal();
        Integer paid = patientHistory.getPaid() + dto.getPaidValue();
        patientHistory.setPaid(paid);

        if (total <= paid) {
            patientHistory.setIsPaid(true);
        }
        historyService.save(patientHistory);

        return paid + " so`m to`landi";
    }

    @Override
    public List<PaymentResponseDto> paymentsDate(LocalDate startDate, LocalDate endDate) {

         List<PaymentEntity> paymentEntities = repository.findAllByPaidDateBetween(startDate, endDate);

         List<PaymentResponseDto> responseDtos;

         responseDtos = paymentEntities.stream().map(this::toDto).toList();

         return responseDtos;

    }

    @Override
    public PaymentResponseDto toDto(PaymentEntity entity) {
        PaymentResponseDto responseDto = new PaymentResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setPatientName(entity.getPatientHistoryEntity().getClient().getName());
        responseDto.setPatientLastName(entity.getPatientHistoryEntity().getClient().getLastName());
        responseDto.setTotalValue(entity.getPatientHistoryEntity().getTotal());
        responseDto.setPaid(entity.getPaidValue());
        responseDto.setDebit(entity.getPatientHistoryEntity().getTotal() - entity.getPatientHistoryEntity().getPaid());
        responseDto.setPaidDate(entity.getPaidDate());
        return responseDto;
    }
}
