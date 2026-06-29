/**
 * Author: Anvar Olimov
 * User:user
 * Date:2/3/2025
 * Time:5:34 PM
 */


package dental.patient_history.xrey.repository;

import dental.patient_history.entity.PatientHistoryEntity;
import dental.patient_history.xrey.entity.XRayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface XRayRepository extends JpaRepository<XRayEntity, Long> {
    List<XRayEntity> findByPatientHistory(PatientHistoryEntity patientHistory);

    List<XRayEntity> findAllByPatientHistoryId(Long id);
    List<XRayEntity> findByPatientHistoryId(Long id);

    @Query("SELECT x FROM XRayEntity x WHERE x.patientHistory.client.id = :clientId")
    List<XRayEntity> findByClientId(@Param("clientId") Long clientId);

}
