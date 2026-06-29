/**
 * Author: Anvar Olimov
 * User:user
 * Date:7/3/2024
 * Time:5:40 PM
 */


package dental.patient_history.service;

import dental.exception.ResourceNotFoundException;
import dental.patient_history.dto.PatientServiceAddDto;

import dental.patient_history.entity.PatientHistoryEntity;
import dental.patient_history.entity.TeethServiceEntity;
import dental.patient_history.entity.TeethServiceKey;
import dental.patient_history.repository.PatientRepository;
import dental.patient_history.repository.TeethServiceRepo;
import dental.service_category.service.TServiceImpl;
import dental.teeth.service.TeethServiceImpl;
import dental.utils.DefaultResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TeethServiceSImpl implements TeethServiceS {

    private final TeethServiceRepo repo;

    private final TeethServiceImpl teethService;

    private final TServiceImpl service;

    private final PatientRepository repository;

    @Override
    public DefaultResponseDto createTeethServiceForPatientHistory(PatientServiceAddDto dto) {

        Optional<PatientHistoryEntity> entityOptional = repository.findById(dto.getPatientId());
        if (entityOptional.isEmpty()) {
            throw new ResourceNotFoundException("Patient History Not Found");
        }
        PatientHistoryEntity entity = entityOptional.get();
        if (Boolean.TRUE.equals(entity.getIsServiced())) throw new IllegalArgumentException("Hizmat qo`shib bo`lmaydi");
        Integer total = entity.getTotal();
        entity.setTotal(Integer.sum(total,service.getEntityById(dto.getServiceId()).getPrice()));
        entity.getClient().setDebt(Integer.sum(entity.getClient().getDebt(),total));
        entity.setExpense(Integer.sum(entity.getExpense(),service.getEntityById((dto.getServiceId())).getExpense()));
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
