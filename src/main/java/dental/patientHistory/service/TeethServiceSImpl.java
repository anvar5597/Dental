/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/3/2024
 * Time:5:40 PM
 */


package dental.patientHistory.service;

import dental.patientHistory.dto.PatientServiceAddDto;

import dental.patientHistory.entity.PatientHistoryEntity;
import dental.patientHistory.entity.TeethServiceEntity;
import dental.patientHistory.entity.TeethServiceKey;
import dental.patientHistory.repository.PatientHistoryRepository;
import dental.patientHistory.repository.TeethServiceRepo;
import dental.serviceCategory.service.TServiceImpl;
import dental.teeth.service.TeethServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TeethServiceSImpl implements TeethServiceS {

    private final TeethServiceRepo repo;

    private final TeethServiceImpl teethService;

    private final TServiceImpl service;

    private final PatientHistoryRepository repository;

    @Override
    public void createTeethServiceForPatientHistory(PatientServiceAddDto dto) {

        PatientHistoryEntity entity = repository.findById(dto.getPatientId()).get();
        Integer total = entity.getTotal();
        entity.setTotal(Integer.sum(total,service.getEntityById(dto.getServiceId()).getPrice()));
        TeethServiceKey teethServiceKey = new TeethServiceKey();
        teethServiceKey.setService(service.getEntityById(dto.getServiceId()));
        teethServiceKey.setTeeth(teethService.getTeethById(dto.getTeethId()));
        TeethServiceEntity teethServiceEntity = new TeethServiceEntity();
        teethServiceEntity.setPatientHistory(entity);
        teethServiceEntity.setTeethServiceKey(teethServiceKey);

        repo.save(teethServiceEntity);

    }
}
