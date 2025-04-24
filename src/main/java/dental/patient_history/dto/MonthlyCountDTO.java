/**
 * Author: Anvar Olimov
 * User:user
 * Date:2/24/2025
 * Time:9:44 PM
 */


package dental.patient_history.dto;

import lombok.Data;

@Data
public class MonthlyCountDTO {
    private int year;
    private int month;
    private long count;

    public MonthlyCountDTO(int year, int month, long count) {
        this.year = year;
        this.month = month;
        this.count = count;
    }
}
