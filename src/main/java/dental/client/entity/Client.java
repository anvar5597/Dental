package dental.client.entity;

import dental.client.analys.entity.ClientXRayEntity;
import dental.utils.TableName;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = TableName.CLIENTS)
@SQLDelete(sql = "UPDATE clients SET  deleted = true WHERE id =?")
@SQLRestriction("deleted <> 'true'")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastName;

    private String patronymic;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthday;

    private String phoneNumber;

    private String address;

    private Boolean active = true;

    private Boolean deleted = false;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<ClientXRayEntity> xrayImages;

    private Integer debt = 0;

}
