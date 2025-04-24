package dental.client.analys.service;

import dental.client.analys.entity.ClientXRayEntity;
import dental.client.analys.repository.CliedtXRayRepository;
import dental.client.entity.Client;
import dental.client.repository.ClientRepository;
import dental.patient_history.repository.PatientRepository;
import org.jetbrains.annotations.NotNull;
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
public class ClientXRayService {

    @Value("${file.store.path}")
    private String uploadDir;
    private final CliedtXRayRepository xrayRepository;

    private final ClientRepository clientRepository;

    private static final List<String> ALLOWED_FILE_TYPES = Arrays.asList("application/pdf", "image/png", "image/jpeg", "image/jpg");

    public ClientXRayService(CliedtXRayRepository xrayRepository, ClientRepository clientRepository) {
        this.xrayRepository = xrayRepository;
        this.clientRepository = clientRepository;
    }

    public ClientXRayEntity saveXRay(MultipartFile file, Long clientId) throws IOException {
        @NotNull Optional<Client> clientXRay = clientRepository.findById(clientId);
        if (clientXRay.isEmpty()) {
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
        ClientXRayEntity xrayEntity = new ClientXRayEntity();
        xrayEntity.setFileName(file.getOriginalFilename());
        xrayEntity.setFilePath(filePath);
        xrayEntity.setClient(clientXRay.get());
        return xrayRepository.save(xrayEntity);
    }

    public byte[] getXRayFile(Long id) throws IOException {
        Optional<ClientXRayEntity> xray = xrayRepository.findById(id);
        if (xray.isPresent()) {
            Path filePath = Paths.get(xray.get().getFilePath());
            return Files.readAllBytes(filePath);
        }
        throw new RuntimeException("Fayl topilmadi!");
    }

    public void deleteXRay(Long id) throws IOException {
        Optional<ClientXRayEntity> xray = xrayRepository.findById(id);
        if (xray.isPresent()) {
            Files.deleteIfExists(Paths.get(xray.get().getFilePath())); // Faylni serverdan o‘chirish
            xrayRepository.deleteById(id);
        }
    }
}
