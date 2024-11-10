/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/3/2024
 * Time:5:40 PM
 */


package dental.patient_history.service;

import dental.patient_history.dto.PatientServiceAddDto;

import dental.patient_history.entity.PatientHistoryEntity;
import dental.patient_history.entity.TeethServiceEntity;
import dental.patient_history.entity.TeethServiceKey;
import dental.patient_history.repository.PatientHistoryRepository;
import dental.patient_history.repository.TeethServiceRepo;
import dental.service_category.service.TServiceImpl;
import dental.teeth.service.TeethServiceImpl;
import dental.utils.DefaultResponseDto;
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
    public DefaultResponseDto createTeethServiceForPatientHistory(PatientServiceAddDto dto) {

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
        return DefaultResponseDto.builder()
                .status(200)
                .message("Mijozga hizmat hizmat ko`rsatildi").build();
    }
}
