package dental.payment.service;


import dental.payment.dto.PaymentRequestDto;
import dental.payment.dto.PaymentResponseDto;
import dental.payment.entity.PaymentEntity;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface PaymentService {

    String create(PaymentRequestDto dto);

    String update(PaymentRequestDto dto, Long id);

    List<PaymentResponseDto> findAll();
    List<PaymentResponseDto> paymentByDebt();

    List<PaymentResponseDto> paymentsByClient(Long id);

    List<PaymentResponseDto> paymentDate(LocalDate date);

    List<PaymentResponseDto> paymentByDoctor(Long id);

    List<PaymentResponseDto> paymentsDate(LocalDate startDate, LocalDate endDate);

    void exportToExcel(String filePath) throws IOException;

    List<PaymentResponseDto> findByPatientId(Long id);


    PaymentResponseDto toDto(PaymentEntity entity);

    void deleteWithEmployee(Long id);
    void deleteWithClient(Long id);
    void  delete(Long id);
}
