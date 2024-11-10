package dental.payment.controller;

import dental.payment.dto.PaymentRequestDto;
import dental.payment.dto.PaymentResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RequestMapping("/payment")

public interface PaymentController {

    @PostMapping("/pay")
    String pay(@RequestBody PaymentRequestDto dto);

    @PutMapping("/update/{id}")
    String update(@RequestBody PaymentRequestDto dto, @PathVariable Long id);

    @GetMapping("/find-all")
    ResponseEntity<List<PaymentResponseDto>> findAll();

    @GetMapping("/date")
    ResponseEntity<List<PaymentResponseDto>> payByDate( LocalDate date);

    @GetMapping("/doctor/{id}")
    ResponseEntity<List<PaymentResponseDto>> paymentByDoctor(@PathVariable Long id);
    @GetMapping("/client/{id}")
    ResponseEntity<List<PaymentResponseDto>> payByClient(@PathVariable Long id);

    @GetMapping("/between")
    List<PaymentResponseDto> paymentsDate(LocalDate startDate, LocalDate endDate);

    @GetMapping("/export/excel")
    Object exportToExcel(@RequestParam(defaultValue = "teeth_service_data.xlsx") String fileName);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> delete( @PathVariable Long id);

 }
