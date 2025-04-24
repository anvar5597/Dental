/**
 * Author: Anvar Olimov
 * User:user
 * Date:2/25/2025
 * Time:9:47 AM
 */


package dental.patient_history.dto;

import lombok.Data;

@Data
public class MonthlyIncomeExpenseDTO {
    private int year;
    private int month;
    private Long employeeId;
    private Long totalIncome;
    private Long totalExpense;

    public MonthlyIncomeExpenseDTO(int year, int month, Long employeeId, Long totalIncome, Long totalExpense) {
        this.year = year;
        this.month = month;
        this.employeeId = employeeId;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
    }

}
