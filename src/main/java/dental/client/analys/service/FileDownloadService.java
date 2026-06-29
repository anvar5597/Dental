/**
 * Author: Anvar Olimov
 * User:user
 * Date:5/4/2025
 * Time:4:19 PM
 */


package dental.client.analys.service;

import dental.client.analys.entity.ClientXRayEntity;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileDownloadService {

    public void writeFilesToZip(List<ClientXRayEntity> files, HttpServletResponse response) throws IOException {
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=\"client_" + files.get(0).getClient().getId() + "_files.zip\"");

        try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {
            for (ClientXRayEntity file : files) {
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
