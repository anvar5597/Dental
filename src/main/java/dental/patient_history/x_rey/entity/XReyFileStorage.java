package dental.patient_history.x_rey.entity;

import dental.patient_history.entity.PatientHistoryEntity;
import jakarta.persistence.*;
import lombok.Data;


import java.io.Serializable;

@Data
@Entity
public class XReyFileStorage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "patient_id",insertable = false,updatable = true)
    private PatientHistoryEntity patirntHistory;

    private String extension;

    private Long fileSize;

    private String hashId;

    private String contentType;

    private String uploadPath;

    @Enumerated(EnumType.STRING)
    private XreyStatus fileStorageStatus;


}
