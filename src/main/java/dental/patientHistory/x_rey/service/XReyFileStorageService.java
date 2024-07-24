package dental.patientHistory.x_rey.service;


import dental.patientHistory.entity.PatientHistoryEntity;
import dental.patientHistory.repository.PatientHistoryRepository;
import dental.patientHistory.x_rey.entity.XReyFileStorage;
import dental.patientHistory.x_rey.entity.XreyStatus;
import dental.patientHistory.x_rey.repository.XReyFileStorageRepository;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class XReyFileStorageService {

    private final XReyFileStorageRepository xRayFileStorageRepository;
    private final PatientHistoryRepository repository;

    @Value("${upload.xfolder}")
    private String uploadFolder;

    private final Hashids hashids;

    public XReyFileStorageService(XReyFileStorageRepository xRayFileStorageRepository, PatientHistoryRepository repository) {
        this.xRayFileStorageRepository = xRayFileStorageRepository;
        this.repository = repository;
        this.hashids = new Hashids(getClass().getName(), 6);

    }


    public void save(MultipartFile multipartFile, Long id) {
        XReyFileStorage xReyFileStorage = new XReyFileStorage();
        xReyFileStorage.setName(multipartFile.getOriginalFilename());
        xReyFileStorage.setExtension(getExt(multipartFile.getOriginalFilename()));
        xReyFileStorage.setFileSize(multipartFile.getSize());
        xReyFileStorage.setContentType(multipartFile.getContentType());
        xReyFileStorage.setFileStorageStatus(XreyStatus.DRAFT);
        Optional<PatientHistoryEntity> optionalClient = repository.findById(id);
        if (optionalClient.isEmpty()) {
            throw new RuntimeException("Bunday id li client yo`q");
        }
        xReyFileStorage.setPatirntHistory(optionalClient.get());
        xRayFileStorageRepository.save(xReyFileStorage);


        Date now = new Date();
        File uploadFolder = new File(String.format("%s/upload_files/%d/%d/%d/", this.uploadFolder,
                1900 + now.getYear(), 1 + now.getMonth(), now.getDate()));
        if (!uploadFolder.exists() && uploadFolder.mkdirs()) {
            System.out.println("aytilgan papkalar yaratildi");
        }
        xReyFileStorage.setHashId(hashids.encode(xReyFileStorage.getId()));
        xReyFileStorage.setUploadPath(String.format("upload_files/%d/%d/%d/%s.%s",
                1900 + now.getYear(),
                1 + now.getMonth(),
                now.getDate(),
                xReyFileStorage.getHashId(),
                xReyFileStorage.getExtension()));
        xRayFileStorageRepository.save(xReyFileStorage);
        uploadFolder = uploadFolder.getAbsoluteFile();
        File file = new File(uploadFolder, String.format("%s.%s", xReyFileStorage.getHashId(), xReyFileStorage.getExtension()));
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @Transactional(readOnly = true)
    public XReyFileStorage findByHashId(String hashId) {
        return xRayFileStorageRepository.findByHashId(hashId);
    }

    public void delete(String hashId) {
        XReyFileStorage fileStorage = findByHashId(hashId);
        File file = new File(String.format("%s/%s", this.uploadFolder, fileStorage.getUploadPath()));
        if (file.delete()) {
            xRayFileStorageRepository.delete(fileStorage);
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteAllDraft() {
        List<XReyFileStorage> fileStorageList = xRayFileStorageRepository.findAllByFileStorageStatus(XreyStatus.DRAFT);
        fileStorageList.forEach(fileStorage -> {
            delete(fileStorage.getHashId());
        });
    }

    private String getExt(String fileName) {
        String ext = null;
        if (fileName != null && !fileName.isEmpty()) {
            int dot = fileName.lastIndexOf('.');
            if (dot > 0 && dot <= fileName.length() - 2) {
                ext = fileName.substring(dot + 1);
            }
        }
        return ext;
    }
}
