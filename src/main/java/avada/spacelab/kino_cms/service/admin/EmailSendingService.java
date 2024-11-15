package avada.spacelab.kino_cms.service.admin;

import java.io.IOException;
import java.util.List;
import org.springframework.web.socket.WebSocketSession;

public interface EmailSendingService {

    void sendEmail(
            List<Long> ids, String fileName, WebSocketSession session
    ) throws IOException, InterruptedException;

}
