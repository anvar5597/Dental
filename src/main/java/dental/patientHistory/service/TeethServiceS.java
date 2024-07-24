package dental.patientHistory.service;

import dental.patientHistory.dto.PatientServiceAddDto;
import dental.patientHistory.dto.TeethServiceDto;
import dental.patientHistory.entity.PatientHistoryEntity;

import java.util.List;

public interface TeethServiceS {


    void createTeethServiceForPatientHistory(PatientServiceAddDto dto);
}
