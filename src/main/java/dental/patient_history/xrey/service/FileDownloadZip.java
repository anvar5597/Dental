/**
 * Author: Anvar Olimov
 * User:Anvar
 * Date:5/5/2025
 * Time:9:54 AM
 */


package dental.patient_history.xrey.service;

import dental.patient_history.xrey.entity.XRayEntity;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileDownloadZip {
    public void writeFilesToZip(List<XRayEntity> files, HttpServletResponse response) throws IOException {
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=\"client_" + files.get(0).getPatientHistory().getId() + "_files.zip\"");

        try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {
            for (XRayEntity file : files) {
                File systemFile = new File(file.getFilePath());
                if (systemFile.exists()) {
                    try (FileInputStream fis = new FileInputStream(systemFile)) {
                        ZipEntry zipEntry = new ZipEntry(file.getFileName());
                        zipOut.putNextEntry(zipEntry);

                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = fis.read(buffer)) > 0) {
                            zipOut.write(buffer, 0, length);
                        }
                    }
                }
            }
        }
    }
}
