package avada.spacelab.kino_cms.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.socket.WebSocketSession;

public interface SmsSendingService {

    void sendSms(
            List<Long> ids, String message, WebSocketSession session
    ) throws IOException, InterruptedException;

}
