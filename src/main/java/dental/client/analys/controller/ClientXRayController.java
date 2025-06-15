package dental.client.analys.controller;

import dental.client.analys.dto.AnalyseDto;
import dental.client.analys.entity.ClientXRayEntity;
import dental.client.analys.service.ClientXRayService;
import dental.client.analys.service.FileDownloadService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/analyse")
public class ClientXRayController {

    private final ClientXRayService xrayService;
    private FileDownloadService fileDownloadService;

    @Autowired
    public ClientXRayController(ClientXRayService clientXRayService,
                                FileDownloadService fileDownloadService) {
        this.xrayService= clientXRayService;
        this.fileDownloadService = fileDownloadService;
    }
    // Fayl yuklash
    @PostMapping("/upload/{ClientId}")
    public ResponseEntity<String> uploadXRay(@RequestParam("file") MultipartFile file, @PathVariable Long ClientId) {
        try {
            ClientXRayEntity savedXRay = xrayService.saveXRay(file, ClientId);
            return ResponseEntity.ok("X-ray yuklandi: " + savedXRay.getFilePath());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Xatolik: " + e.getMessage());
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadXRayById(@PathVariable Long id) throws IOException {
        ClientXRayEntity xRay = xrayService.getXRayById(id);
        Resource file = xrayService.getFileResource(xRay);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + xRay.getFileName() + "\"")
                .body(file);
    }

    @GetMapping("/list/{clientId}")
    public ResponseEntity<List<AnalyseDto>> getXRayList(@PathVariable Long clientId) {
        List<AnalyseDto> list = xrayService.findAnalysesByClientId(clientId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{clientId}/files/download")
    public void downloadClientFiles(@PathVariable Long clientId, HttpServletResponse response) throws IOException {
        List<ClientXRayEntity> files = xrayService.getFilesByClientId(clientId);
        if (files.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("No files found for client ID: " + clientId);
            return;
        }
        fileDownloadService.writeFilesToZip(files, response);
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
