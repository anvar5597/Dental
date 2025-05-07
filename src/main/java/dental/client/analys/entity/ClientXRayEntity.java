/**
 * Author: Anvar Olimov
 * User:user
 * Date:2/3/2025
 * Time:5:30 PM
 */


package dental.client.analys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dental.client.entity.Client;
import dental.utils.TableName;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = TableName.ANALYSES)
public class ClientXRayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)  // `nullable = false` agar patient_id bo‘lishi kerak bo‘lsa
    @JsonIgnore
    private Client client;  // Boshqa klass bilan bog‘langan

    // Boshqa maydonlar
    private String fileName;
    private String filePath;
    private String description;
}
