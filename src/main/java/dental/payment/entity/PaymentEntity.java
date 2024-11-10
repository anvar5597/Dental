/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/10/2024
 * Time:11:14 AM
 */


package dental.payment.entity;

import dental.patient_history.entity.PatientHistoryEntity;
import dental.utils.TableName;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity
@Data
@SQLDelete(sql = "UPDATE patient SET  deleted = true WHERE id =?")
@SQLRestriction("deleted <> 'true'")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "id", nullable = false)
    private PatientHistoryEntity patientHistoryEntity;

    private Integer paidValue;

    private LocalDate paidDate;

    private Boolean deleted;

}
