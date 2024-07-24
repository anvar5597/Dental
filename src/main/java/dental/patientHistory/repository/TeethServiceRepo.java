package dental.patientHistory.repository;

import dental.patientHistory.entity.TeethServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeethServiceRepo extends JpaRepository<TeethServiceEntity, Long > {
}

