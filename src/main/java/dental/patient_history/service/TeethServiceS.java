package dental.patient_history.service;

import dental.patient_history.dto.PatientServiceAddDto;
import dental.utils.DefaultResponseDto;

public interface TeethServiceS {


    DefaultResponseDto createTeethServiceForPatientHistory(PatientServiceAddDto dto);
}
