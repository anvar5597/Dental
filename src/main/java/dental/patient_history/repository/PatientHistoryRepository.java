package dental.patient_history.repository;

import dental.epms.entity.Employees;
import dental.patient_history.entity.PatientHistoryEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientHistoryRepository extends JpaRepository<PatientHistoryEntity, Long> {

    List<PatientHistoryEntity> findByEmployeesId(Long id);

    @NotNull Optional<PatientHistoryEntity> findById(@NotNull Long id);

    Boolean existsByEmployeesAndAppointmentTimeBetweenOrEndTimeBetweenOrAndAppointmentTimeLessThanAndEndTimeGreaterThan(
            Employees employees,
            LocalDateTime startTime1,
            LocalDateTime endTime1,
            LocalDateTime startTime2,
            LocalDateTime endTime2,
            LocalDateTime startTime3,
            LocalDateTime endTime3
    );
}
