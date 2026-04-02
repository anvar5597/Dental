/**
 * Author: Anvar Olimov
 * User:Anvar
 * Date:7/7/2025
 * Time:2:40 PM
 */


package dental.patient_history.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyIncomeDto {

    private Integer month;
    private Integer year;
    private Double income;
    private Double monthlyIncome;

    public MonthlyIncomeDto(Integer month, Integer year, Double income, Double monthlyIncome) {
        this.month = month;
        this.year = year;
        this.income = income;
        this.monthlyIncome = monthlyIncome;
    }
}
