package avada.spacelab.kino_cms.controller.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Objects;
import org.springframework.web.multipart.MultipartFile;

public class ControllerUtil {
    public static final String PATH_TO_SENT_EMAIL = "src/main/resources/service_files/sent_emails";

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
            String fileName,
            String type,
            byte[] file
    ) throws IOException {
        File dir = new File(PATH_TO_SENT_EMAIL);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File olderFile = null;
        Instant older = Instant.now();
        int amount = 0;
        for (File fileEntry : Objects.requireNonNull(dir.listFiles())) {
            ++amount;
            if (fileEntry.lastModified() < older.toEpochMilli()) {
                older = Instant.ofEpochMilli(fileEntry.lastModified());
                olderFile = fileEntry;
            }
            if (fileEntry.getName().contains(fileName)) {
                fileEntry.delete();
            }
        }
        if (amount > 4) {
            assert olderFile != null;
            olderFile.delete();
        }
        String filePath = PATH_TO_SENT_EMAIL + "/" + fileName + "." + type;
        Files.write(Path.of(filePath), file);
        return filePath;
    }
}
