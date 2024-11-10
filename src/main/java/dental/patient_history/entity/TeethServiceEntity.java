/**
 * Author: Anvar Olimov
 * User:user
 * Date:6/28/2024
 * Time:5:57 PM
 */


package dental.patient_history.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "teeth_services")
@Data
public class TeethServiceEntity {

    @EmbeddedId
    private TeethServiceKey teethServiceKey;

    @JsonBackReference
    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "id")
    private PatientHistoryEntity patientHistory;
}