/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/10/2024
 * Time:4:01 PM
 */


package dental.payment.controller;

import dental.payment.dto.PaymentRequestDto;
import dental.payment.dto.PaymentResponseDto;
import dental.payment.service.PaymentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PaymentControllerImpl implements PaymentController{

    private  final PaymentServiceImpl service;
    @Override
    public String pay(PaymentRequestDto dto) {
        return service.create(dto);
    }

    @Override
    public List<PaymentResponseDto> paymentsDate(LocalDate startDate, LocalDate endDate) {
        return service.paymentsDate(startDate,endDate);
    }


}
