package dental.patient_history.xrey.service;

import dental.patient_history.entity.PatientHistoryEntity;
import dental.patient_history.repository.PatientRepository;
import dental.patient_history.xrey.entity.XRayEntity;
import dental.patient_history.xrey.repository.XRayRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class XRayService {

    @Value("${file.store.path}")
    private String uploadDir;
    private final XRayRepository xrayRepository;

    private final PatientRepository patientHistoryRepository;

    private static final List<String> ALLOWED_FILE_TYPES = Arrays.asList("application/pdf", "image/png", "image/jpeg", "image/jpg");

    public XRayService(XRayRepository xrayRepository, PatientRepository patientHistoryRepository) {
        this.xrayRepository = xrayRepository;
        this.patientHistoryRepository = patientHistoryRepository;
    }

    public XRayEntity saveXRay(MultipartFile file, Long patientHistoryId) throws IOException {
        Optional<PatientHistoryEntity> optionalPatientHistory = patientHistoryRepository.findById(patientHistoryId);
        if (optionalPatientHistory.isEmpty()) {
            throw new RuntimeException("Bemor tarixi topilmadi!");
        }

        // Fayl nomini yaratish
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String filePath = uploadDir + fileName;

        // Faylni serverga saqlash
        Files.createDirectories(Paths.get(uploadDir)); // Papka yo‘q bo‘lsa, yaratadi
        Path destination = Paths.get(filePath);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        // Fayl ma’lumotlarini bazaga saqlash
        XRayEntity xrayEntity = new XRayEntity();
        xrayEntity.setFileName(file.getOriginalFilename());
        xrayEntity.setFilePath(filePath);
        xrayEntity.setPatientHistory(optionalPatientHistory.get());
        return xrayRepository.save(xrayEntity);
    }

    public byte[] getXRayFile(Long id) throws IOException {
        Optional<XRayEntity> xray = xrayRepository.findById(id);
        if (xray.isPresent()) {
            Path filePath = Paths.get(xray.get().getFilePath());
            return Files.readAllBytes(filePath);
        }
        throw new RuntimeException("Fayl topilmadi!");
    }

    public void deleteXRay(Long id) throws IOException {
        Optional<XRayEntity> xray = xrayRepository.findById(id);
        if (xray.isPresent()) {
            Files.deleteIfExists(Paths.get(xray.get().getFilePath())); // Faylni serverdan o‘chirish
            xrayRepository.deleteById(id);
        }
    }
}
