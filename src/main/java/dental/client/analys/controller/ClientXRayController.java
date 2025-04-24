package dental.client.analys.controller;

import dental.client.analys.entity.ClientXRayEntity;
import dental.client.analys.service.ClientXRayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/analyse")
public class ClientXRayController {

    private final ClientXRayService xrayService;

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

    // Faylni yuklab olish
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getXRay(@PathVariable Long id) {
        try {
            byte[] fileData = xrayService.getXRayFile(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"xray_" + id + "\"")
                    .body(fileData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
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
