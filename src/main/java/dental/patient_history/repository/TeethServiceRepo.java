package dental.patient_history.repository;

import dental.patient_history.entity.TeethServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeethServiceRepo extends JpaRepository<TeethServiceEntity, Long > {
}

