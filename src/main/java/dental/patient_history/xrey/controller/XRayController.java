package dental.patient_history.xrey.controller;

import dental.client.analys.entity.ClientXRayEntity;
import dental.patient_history.xrey.entity.XRayEntity;
import dental.patient_history.xrey.service.FileDownloadZip;
import dental.patient_history.xrey.service.XRayService;
import dental.patient_history.xrey.xRayDto.XRayDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/xray")
public class XRayController {

    private final XRayService xrayService;

    private final FileDownloadZip fileDownloadZip;
    // Fayl yuklash
    @PostMapping("/upload/{patientHistoryId}")
    public ResponseEntity<String> uploadXRay(@RequestParam("file") MultipartFile file, @PathVariable Long patientHistoryId) {
        try {
            XRayEntity savedXRay = xrayService.saveXRay(file, patientHistoryId);
            return ResponseEntity.ok("X-ray yuklandi: " + savedXRay.getFilePath());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Xatolik: " + e.getMessage());
        }
    }

    @GetMapping("/{patientId}/files/download")
    public void downloadClientFiles(@PathVariable Long patientId, HttpServletResponse response) throws IOException {
        List<XRayEntity> files = xrayService.findXRayByPatientId(patientId);
        if (files.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("No files found for client ID: " + patientId);
            return;
        }
        fileDownloadZip.writeFilesToZip(files, response);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadXRayById(@PathVariable Long id) throws IOException {
        XRayEntity xRay = xrayService.getXRayById(id);
        Resource file = xrayService.getFileResource(xRay);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + xRay.getFileName() + "\"")
                .body(file);
    }
    @GetMapping("/x-ray/list/{patientId}")
    private ResponseEntity<List<XRayDto>> findXray(@PathVariable Long patientId){
        return ResponseEntity.ok(xrayService.findByPatientId(patientId));
    }

    // Faylni o‘chirish
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteXRay(@PathVariable Long id) {
        try {
            xrayService.deleteXRay(id);
            return ResponseEntity.ok("X-ray fayl o‘chirildi!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Xatolik: " + e.getMessage());
        }
    }
}
