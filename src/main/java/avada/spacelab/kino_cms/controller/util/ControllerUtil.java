package avada.spacelab.kino_cms.controller.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import org.springframework.web.multipart.MultipartFile;

public class ControllerUtil {
    public static String savePictureOnServer(
            String serverPath,
            String fileName,
            String timestamp,
            String type,
            MultipartFile file
    ) throws IOException {
        File dir = new File(serverPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        for (File fileEntry : Objects.requireNonNull(dir.listFiles())) {
            if (fileEntry.getName().contains(fileName)) {
                fileEntry.delete();
            }
        }
        String filePath = File.separator +
                serverPath + File.separator +
                fileName + File.separator +
                timestamp + "." + type;
        Files.write(Path.of(filePath), file.getBytes());
        return filePath;
    }

    public static String saveEmailOnServer(
            String serverPath,
            String fileName,
            String type,
            byte[] file
    ) throws IOException {
        File dir = new File(serverPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        for (File fileEntry : Objects.requireNonNull(dir.listFiles())) {
            if (fileEntry.getName().contains(fileName)) {
                fileEntry.delete();
            }
        }
        String filePath = serverPath + fileName + "." + type;
        Files.write(Path.of(filePath), file);
        return filePath;
    }
}
