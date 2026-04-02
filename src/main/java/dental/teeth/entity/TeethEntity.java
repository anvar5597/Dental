package dental.teeth.entity;

import dental.utils.TableName;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = TableName.TEETH)
public class TeethEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teethName;

    private Boolean active = true;
}
