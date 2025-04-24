package dental.patient_history.repository;

import dental.epms.entity.Employees;
import dental.patient_history.entity.PatientHistoryEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientHistoryEntity, Long> {

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

    @Query(value = """
        SELECT YEAR(p.appointmentTime) as year, MONTH(p.appointmentTime) as month, COUNT(p.id) as count
        FROM PatientHistoryEntity p
        WHERE p.appointmentTime >= :startDate
        GROUP BY YEAR(p.appointmentTime), MONTH(p.appointmentTime)
        ORDER BY YEAR(p.appointmentTime) DESC, MONTH(p.appointmentTime) DESC
    """)
    List<Object[]> countAppointmentsByMonth(@Param("startDate") LocalDateTime startDate);
    @Query(value = """
        SELECT 
            YEAR(p.appointmentTime) as year, 
            MONTH(p.appointmentTime) as month, 
            SUM(p.paid) as totalIncome, 
            SUM(p.expense) as totalExpense
        FROM PatientHistoryEntity p
        WHERE p.appointmentTime >= :startDate AND p.employees.id = :employeeId
        GROUP BY YEAR(p.appointmentTime), MONTH(p.appointmentTime)
        ORDER BY YEAR(p.appointmentTime) DESC, MONTH(p.appointmentTime) DESC
    """)
    List<Object[]> findMonthlyIncomeAndExpenseByEmployee(
            @Param("startDate") LocalDateTime startDate,
            @Param("employeeId") Long employeeId
    );
    @Query(value = """
        SELECT 
            YEAR(p.appointmentTime) as year, 
            MONTH(p.appointmentTime) as month, 
            p.employees.id as employeeId, 
            SUM(p.paid) as totalIncome, 
            SUM(p.expense) as totalExpense
        FROM PatientHistoryEntity p
        WHERE p.appointmentTime >= :startDate
        GROUP BY YEAR(p.appointmentTime), MONTH(p.appointmentTime), p.employees.id
        ORDER BY YEAR(p.appointmentTime) DESC, MONTH(p.appointmentTime) DESC
    """)
    List<Object[]> findMonthlyIncomeAndExpensePerEmployee(@Param("startDate") LocalDateTime startDate);
}
