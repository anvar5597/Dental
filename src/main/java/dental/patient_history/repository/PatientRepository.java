package dental.patient_history.repository;

import dental.patient_history.dto.MonthlyIncomeDto;
import dental.patient_history.dto.MonthlyIncomeDtoProjection;
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


  List<PatientHistoryEntity> findByClient_Id(Long id);

  @NotNull Optional<PatientHistoryEntity> findById(@NotNull Long id);

  List<PatientHistoryEntity> findByAppointmentTimeAfter(LocalDateTime appointmentTimeAfter);

  @Query("""
          SELECT YEAR(p.appointmentTime), MONTH(p.appointmentTime), COUNT(p.id)
          FROM PatientHistoryEntity p
          WHERE p.appointmentTime >= :startDate
          GROUP BY YEAR(p.appointmentTime), MONTH(p.appointmentTime)
          ORDER BY YEAR(p.appointmentTime) DESC, MONTH(p.appointmentTime) DESC
      """)
  List<Object[]> countAppointmentsByMonth(
      @Param("startDate") LocalDateTime startDate
  );

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

  @Query("""
          select new dental.patient_history.dto.MonthlyIncomeDto(
              MONTH(p.appointmentTime),
              YEAR(p.appointmentTime),
              SUM(p.paid),
              SUM(p.expense)
          ) from PatientHistoryEntity p
          where p.appointmentTime >= :startDate AND p.employees.id = :employeeId
          group by YEAR(p.appointmentTime), MONTH(p.appointmentTime)
      """)
  List<MonthlyIncomeDto> getMonthlyIncome(@Param("startDate") LocalDateTime startDate,
      @Param("employeeId") Long employeeId);

  @Query(value = """
      select
          2025 as year,
          5 as month,
          sum(p.paid) as monthlyIncome,
          sum(p.expense) as totalIncome
          from dental.public.patient p
      where p.appointment_time >= :startDate AND p.doctor_id = :employeeId
      """, nativeQuery = true)
  List<MonthlyIncomeDtoProjection> getMonthlyIncomeProjection(
      @Param("startDate") LocalDateTime startDate,
      @Param("employeeId") Long employeeId);

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
  List<Object[]> findMonthlyIncomeAndExpensePerEmployee(
      @Param("startDate") LocalDateTime startDate);

  @Query(value = """
          SELECT
              YEAR(p.appointmentTime) as year,
              MONTH(p.appointmentTime) as month,
              SUM(p.paid) as totalIncome,
              SUM(p.expense) as totalExpense
          FROM PatientHistoryEntity p
          WHERE p.appointmentTime >= :startDate
          GROUP BY YEAR(p.appointmentTime), MONTH(p.appointmentTime)
          ORDER BY YEAR(p.appointmentTime) DESC, MONTH(p.appointmentTime) DESC
      """)
  List<Object[]> findMonthlyTotalIncomeAndExpense(@Param("startDate") LocalDateTime startDate);
}
