package dental.client.analyses.service;


import dental.client.analyses.entity.FileStorage;
import dental.client.analyses.entity.FileStorageStatus;
import dental.client.analyses.repository.FileStorageRepository;
import dental.client.entity.Client;
import dental.client.repository.ClientRepository;
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
public class FileStorageService {

    private final FileStorageRepository fileStorageRepository;
    private final ClientRepository repository;

    @Value("${upload.folder}")
    private String uploadFolder;

    private final Hashids hashids;

    public FileStorageService(FileStorageRepository fileStorageRepository, ClientRepository repository) {
        this.fileStorageRepository = fileStorageRepository;
        this.repository = repository;
        this.hashids = new Hashids(getClass().getName(), 6);

    }


    public void save(MultipartFile multipartFile , Long id) {
        FileStorage fileStorage = new FileStorage();
        fileStorage.setName(multipartFile.getOriginalFilename());
        fileStorage.setExtension(getExt(multipartFile.getOriginalFilename()));
        fileStorage.setFileSize(multipartFile.getSize());
        fileStorage.setContentType(multipartFile.getContentType());
        fileStorage.setFileStorageStatus(FileStorageStatus.DRAFT);
        Optional<Client> optionalClient =repository.findById(id);
        if(optionalClient.isEmpty()){
             throw new RuntimeException("Bunday id li client yo`q");
        }
        fileStorage.setClient(optionalClient.get());
        fileStorageRepository.save(fileStorage);


        Date now = new Date();
        File uploadFolder = new File(String.format("%s/upload_files/%d/%d/%d/", this.uploadFolder,
               1900 + now.getYear(), 1 + now.getMonth(), now.getDate()));
        if (!uploadFolder.exists() && uploadFolder.mkdirs()){
            System.out.println("aytilgan papkalar yaratildi");
        }
        fileStorage.setHashId(hashids.encode(fileStorage.getId()));
        fileStorage.setUploadPath(String.format("upload_files/%d/%d/%d/%s.%s",
                1900 + now.getYear(),
                1 + now.getMonth(),
                now.getDate(),
                fileStorage.getHashId(),
                fileStorage.getExtension()));
        fileStorageRepository.save(fileStorage);
        uploadFolder = uploadFolder.getAbsoluteFile();
        File file = new File(uploadFolder, String.format("%s.%s", fileStorage.getHashId(), fileStorage.getExtension()));
        try {
            multipartFile.transferTo(file);
        } catch (IOException e){
            e.printStackTrace();
        }
        
    }

    @Transactional(readOnly = true)
    public FileStorage findByHashId(String hashId){
        return fileStorageRepository.findByHashId(hashId);
    }

    public void delete(String hashId){
        FileStorage fileStorage = findByHashId(hashId);
        File file = new File(String.format("%s/%s", this.uploadFolder, fileStorage.getUploadPath()));
        if (file.delete()){
            fileStorageRepository.delete(fileStorage);
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteAllDraft(){
        List<FileStorage> fileStorageList = fileStorageRepository.findAllByFileStorageStatus(FileStorageStatus.DRAFT);
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
