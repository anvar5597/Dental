package dental.payment.controller;

import dental.payment.dto.PaymentRequestDto;
import dental.payment.dto.PaymentResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;


@RequestMapping("/payment")

public interface PaymentController {

    @PostMapping("/pay")
    String pay(@RequestBody PaymentRequestDto dto);

    @GetMapping("/between")
    List<PaymentResponseDto> paymentsDate(LocalDate startDate, LocalDate endDate);
}
