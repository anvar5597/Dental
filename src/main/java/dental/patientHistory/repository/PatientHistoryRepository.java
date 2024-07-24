package dental.patientHistory.repository;

import dental.patientHistory.dto.PatientResponseDto;
import dental.patientHistory.entity.PatientHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PatientHistoryRepository extends JpaRepository<PatientHistoryEntity, Long> {

    List<PatientHistoryEntity> findByEmployeesId(Long id);

}
