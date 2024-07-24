/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/16/2024
 * Time:12:11 PM
 */


package dental.notification.entity;

import dental.client.entity.Client;
import dental.utils.TableName;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@Data
@Table(name = TableName.NOTIFICATION)
@SQLDelete(sql = "UPDATE notification SET  deleted = true WHERE id =?")
@Where(clause = "deleted=false")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private Client client;

    private LocalDate nextVisit;

    private Boolean deleted = false;
}
