/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/10/2024
 * Time:11:30 AM
 */


package dental.payment.dto;

import lombok.Data;

import java.time.LocalDate;


@Data
public class PaymentResponseDto {
    private Long id;

    private String patientName;

    private String patientLastName;

    private Integer totalValue;

    private Integer debit;

    private Integer paid;

    private LocalDate paidDate;
}
