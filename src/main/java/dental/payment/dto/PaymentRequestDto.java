/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/10/2024
 * Time:11:37 AM
 */


package dental.payment.dto;

import lombok.Data;

@Data
public class PaymentRequestDto {

    private Long id;

    private Integer paidValue;
}
