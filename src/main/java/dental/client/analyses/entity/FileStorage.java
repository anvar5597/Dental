package dental.client.analyses.entity;

import dental.client.entity.Client;
import jakarta.persistence.*;
import lombok.Data;


import java.io.Serializable;

@Data
@Entity
public class FileStorage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "client_id",insertable = false,updatable = true)
    private Client client;

    private String extension;

    private Long fileSize;

    private String hashId;

    private String contentType;

    private String uploadPath;

    @Enumerated(EnumType.STRING)
    private FileStorageStatus fileStorageStatus;


}
