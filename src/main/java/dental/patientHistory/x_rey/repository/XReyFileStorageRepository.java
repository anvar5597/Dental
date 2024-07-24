package dental.patientHistory.x_rey.repository;


import dental.patientHistory.x_rey.entity.XReyFileStorage;
import dental.patientHistory.x_rey.entity.XreyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XReyFileStorageRepository extends JpaRepository<XReyFileStorage, Long> {

    XReyFileStorage findByHashId(String hashId);


    List<XReyFileStorage> findAllByFileStorageStatus(XreyStatus fileStorageStatus);

}
