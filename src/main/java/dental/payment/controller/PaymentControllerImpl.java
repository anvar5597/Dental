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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PaymentControllerImpl implements PaymentController {

    private final PaymentServiceImpl service;

    @Override
    public String pay(PaymentRequestDto dto) {
        return service.create(dto);
    }

    @Override
    public String update(PaymentRequestDto dto, Long id) {
        return service.update(dto, id);
    }

    @Override
    public ResponseEntity<List<PaymentResponseDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Override
    public ResponseEntity<List<PaymentResponseDto>> payByDate(LocalDate date) {
        return ResponseEntity.ok(service.paymentDate(date));
    }

    @Override
    public ResponseEntity<List<PaymentResponseDto>> paymentByDoctor(Long id) {
        return ResponseEntity.ok(service.paymentByDoctor(id));
    }

    @Override
    public ResponseEntity<List<PaymentResponseDto>> payByClient(Long id) {
        return ResponseEntity.ok(service.paymentsByClient(id));
    }

    @Override
    public List<PaymentResponseDto> paymentsDate(LocalDate startDate, LocalDate endDate) {
        return service.paymentsDate(startDate, endDate);
    }

    @Override
    public ResponseEntity<String> exportToExcel(String fileName) {
        try {
            service.exportToExcel(fileName);
            return ResponseEntity.ok("Excel file created successfully: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok("Failed to create Excel file.");
        }
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        service.delete(id);
        return ResponseEntity.ok("To`lov o`chirildi");
    }


}
