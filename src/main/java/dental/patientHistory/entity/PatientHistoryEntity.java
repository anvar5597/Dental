/**
 * Author: Anvar Olimov
 * User:user
 * Date:6/24/2024
 * Time:3:05 PM
 */


package dental.patientHistory.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dental.client.entity.Client;
import dental.epms.entity.Employees;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class PatientHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id")
    private Employees employees;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;

    private LocalDate createdAt;

    private Integer total;

    private Integer paid;

    private Boolean isPaid;
    @JsonManagedReference
    @OneToMany(mappedBy = "patientHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeethServiceEntity> teethServiceEntities = new ArrayList<>();

    private Boolean isServiced;
}
