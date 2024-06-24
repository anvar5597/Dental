/**
 * Author: Anvar Olimov
 * User:user
 * Date:6/24/2024
 * Time:3:05 PM
 */


package dental.patientHistory.entity;

import dental.client.entity.Client;
import dental.epms.entity.Employees;
import dental.serviceCategory.entity.ServiceEntity;
import dental.teeth.entity.TeethEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class patientHistoryEntity {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "services_categories", cascade = CascadeType.ALL)
    private List<ServiceEntity> serviceEntities;

    @ManyToOne(cascade = CascadeType.ALL)
    private TeethEntity teethEntity;

    private Boolean isServiced ;
}
