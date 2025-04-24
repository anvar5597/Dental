/**
 * Author: Anvar Olimov
 * User:user
 * Date:2/3/2025
 * Time:5:30 PM
 */


package dental.patient_history.xrey.entity;

import dental.patient_history.entity.PatientHistoryEntity;
import dental.utils.TableName;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = TableName.X_RAY)
public class XRayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_history_id", nullable = false)  // `nullable = false` agar patient_id bo‘lishi kerak bo‘lsa
    private PatientHistoryEntity patientHistory;  // Boshqa klass bilan bog‘langan

    // Boshqa maydonlar
    private String fileName;
    private String filePath;
    private String description;
}
