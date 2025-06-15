package dental.service_category.entity;

import dental.utils.TableName;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = TableName.SERVICE)
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;

    private Integer price;

    private Integer expense;

    private Boolean active=true;

    private LocalDateTime createdAt;

}
