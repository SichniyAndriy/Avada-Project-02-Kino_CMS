package avada.spacelab.kino_cms.controller.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Objects;
import org.springframework.web.multipart.MultipartFile;

public class ControllerUtil {

    private final static String PATH_TO_RESOURCES = "/home/slj/web/slj.demodev.cc/public_html/resources";
    public final static String PATH_TO_SENT_EMAIL =
            PATH_TO_RESOURCES + File.separator + "kino-cms/service_files/sent_emails";


    public static String savePictureOnServer(
            String path,
            String fileName,
            String timestamp,
            String ext,
            MultipartFile file
    ) throws IOException {
        System.out.println(path);
        File dir = new File(PATH_TO_RESOURCES + File.separator + path);
        System.out.println(dir.getAbsolutePath());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        for (File fileEntry : Objects.requireNonNull(dir.listFiles())) {
            if (fileEntry.getName().contains(fileName)) {
                fileEntry.delete();
            }
        }
        Path filePath = Paths.get(PATH_TO_RESOURCES, path, fileName + "-" + timestamp + "." + ext);
        file.transferTo(filePath);
        System.out.println(filePath.toAbsolutePath());
        System.out.println("/" + path + "/" + fileName + "-" + timestamp + "." + ext);
        return "/resources" + "/" + path + "/" + fileName + "-" + timestamp + "." + ext;
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
