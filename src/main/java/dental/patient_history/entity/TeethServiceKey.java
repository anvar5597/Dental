/**
 * Author: Anvar Olimov
 * User:user
 * Date:6/28/2024
 * Time:5:58 PM
 */


package dental.patient_history.entity;

import dental.service_category.entity.ServiceEntity;
import dental.teeth.entity.TeethEntity;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class TeethServiceKey implements Serializable {

    private Long id;

    @ManyToOne
    @JoinColumn(name = "teeth_id", referencedColumnName = "id", nullable = false)
    private TeethEntity teeth;

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id", nullable = false)
    private ServiceEntity service;

    public TeethServiceKey(TeethEntity teeth, ServiceEntity service) {
        this.teeth = teeth;
        this.service = service;
    }

}
