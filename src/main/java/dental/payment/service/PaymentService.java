package dental.payment.service;


import dental.payment.dto.PaymentRequestDto;
import dental.payment.dto.PaymentResponseDto;
import dental.payment.entity.PaymentEntity;

import java.time.LocalDate;
import java.util.List;

public interface PaymentService {

    String create(PaymentRequestDto dto);

    String update(PaymentRequestDto dto, Long id);

    List<PaymentResponseDto> paymentsDate(LocalDate startDate, LocalDate endDate);

    PaymentResponseDto toDto(PaymentEntity entity);
}
