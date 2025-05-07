package dental.client.analys.service;

import dental.client.analys.dto.AnalyseDto;
import dental.client.analys.entity.ClientXRayEntity;
import dental.client.analys.repository.ClientXRayRepository;
import dental.client.entity.Client;
import dental.client.repository.ClientRepository;
import dental.patient_history.xrey.entity.XRayEntity;
import dental.patient_history.xrey.xRayDto.XRayDto;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ClientXRayService {

    @Value("${file.store.path}")
    private String uploadDir;
    private final ClientXRayRepository xrayRepository;

    private final ClientRepository clientRepository;

    private static final List<String> ALLOWED_FILE_TYPES = Arrays.asList("application/pdf", "image/png", "image/jpeg", "image/jpg");

    public ClientXRayService(ClientXRayRepository xrayRepository, ClientRepository clientRepository) {
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
        try {
            Files.createDirectories(Paths.get(uploadDir)); // Papka yo‘q bo‘lsa, yaratadi
            Path destination = Paths.get(filePath);
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        // Fayl ma’lumotlarini bazaga saqlash
        ClientXRayEntity xrayEntity = new ClientXRayEntity();
        xrayEntity.setFileName(file.getOriginalFilename());
        xrayEntity.setFilePath(filePath);
        xrayEntity.setClient(clientXRay.get());
        return xrayRepository.save(xrayEntity);
    }

    public ClientXRayEntity getXRayById(Long id) {
        return xrayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("X-Ray fayl topilmadi"));
    }

    public Resource getFileResource(ClientXRayEntity xRay) throws IOException {
        Path path = Paths.get(xRay.getFilePath());
        if (!Files.exists(path)) {
            throw new FileNotFoundException("Fayl mavjud emas: " + xRay.getFilePath());
        }
        return new UrlResource(path.toUri());
    }


    public List<ClientXRayEntity> getFilesByClientId(Long clientId) {
        return xrayRepository.findByClientId(clientId);
    }

    public List<AnalyseDto> findAnalysesByClientId(Long id){
        return  xrayRepository.findByClientId(id)
                .stream()
                .map(this::toDto)
                .toList();

    }

    public AnalyseDto toDto(ClientXRayEntity entity){
        AnalyseDto dto = new AnalyseDto() ;
        dto.setId(entity.getId());
        dto.setFileName(entity.getFileName());

        return dto;
    }


    public void deleteXRay(Long id) throws IOException {
        Optional<ClientXRayEntity> xray = xrayRepository.findById(id);
        if (xray.isPresent()) {
            Files.deleteIfExists(Paths.get(xray.get().getFilePath())); // Faylni serverdan o‘chirish
            xrayRepository.deleteById(id);
        }
    }
}
