/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/10/2024
 * Time:11:14 AM
 */


package dental.payment.entity;

import dental.patientHistory.entity.PatientHistoryEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "id", nullable = false)
    private PatientHistoryEntity patientHistoryEntity;

    private Integer paidValue;

    private LocalDate paidDate;


}
