package dental.patient_history.x_rey.controller;


import dental.patient_history.x_rey.dto.PatientDto;
import dental.patient_history.x_rey.entity.XReyFileStorage;
import dental.patient_history.x_rey.service.XReyFileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/patient/x-ray")
public class XReyFileStorageResource {

    private final XReyFileStorageService fileStorageService;

    @Value("${upload.xfolder}")
    private String uploadFolder;

    public XReyFileStorageResource(XReyFileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity upload(@RequestPart("file") MultipartFile multipartFile, @RequestPart PatientDto dto){
        fileStorageService.save(multipartFile, dto.getId());
        return ResponseEntity.ok(multipartFile.getOriginalFilename() + "file saqlandi");
    }

    @GetMapping("/preview/{hashId}")
    public ResponseEntity previewFile(@PathVariable String hashId) throws IOException {

        XReyFileStorage fileStorage = fileStorageService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName=\"" + URLEncoder.encode(fileStorage.getName()))
                .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(fileStorage.getFileSize())
                .body(new FileUrlResource(String.format("%s/%s", uploadFolder, fileStorage.getUploadPath())));
    }

    @GetMapping("/download/{hashId}")
    public ResponseEntity downloadFile(@PathVariable String hashId) throws IOException {

        XReyFileStorage fileStorage = fileStorageService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + URLEncoder.encode(fileStorage.getName()))
                .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(fileStorage.getFileSize())
                .body(new FileUrlResource(String.format("%s/%s", uploadFolder, fileStorage.getUploadPath())));
    }

    @DeleteMapping("/delete/{hashId}")
    public ResponseEntity delete(@PathVariable String hashId){

        fileStorageService.delete(hashId);
        return ResponseEntity.ok("File ochirildi");
    }

    /*@GetMapping("/hash-id/{id}")
    String getByHashId(@PathVariable Long id){
        return fileStorageService.f
    }*/

}
