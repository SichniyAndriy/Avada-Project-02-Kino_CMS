package avada.spacelab.kino_cms.socket;

import avada.spacelab.kino_cms.service.EmailSendingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class EmailSendingHandler extends TextWebSocketHandler {

    private final EmailSendingService emailSendingService;
    private final Logger logger = LogManager.getLogger(EmailSendingHandler.class);

    public EmailSendingHandler(EmailSendingService emailSendingService) {
        this.emailSendingService = emailSendingService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("EmailWebSocket connection established: {}", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("EmailWebSocket connection closed: {}", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        EmailRequest emailRequest = new ObjectMapper()
                .readValue(message.getPayload(), EmailRequest.class);
        emailSendingService.sendEmail(
                emailRequest.ids,
                emailRequest.fileName,
                session
        );
    }

    public static class EmailRequest {
        public String fileName;
        public List<Long> ids;
}

}
