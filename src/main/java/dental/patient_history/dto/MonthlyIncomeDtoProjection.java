package dental.patient_history.dto;

public interface MonthlyIncomeDtoProjection {
    Integer getYear();
    Integer getMonth();
    Double getMonthlyIncome();
    Double getTotalIncome();
}
