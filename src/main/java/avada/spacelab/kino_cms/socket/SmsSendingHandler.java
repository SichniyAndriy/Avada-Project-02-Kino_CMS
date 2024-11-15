package avada.spacelab.kino_cms.socket;

import avada.spacelab.kino_cms.service.admin.SmsSendingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SmsSendingHandler extends TextWebSocketHandler {
    private final SmsSendingService smsSendingService;
    private final Logger logger = LogManager.getLogger(SmsSendingHandler.class);

    public SmsSendingHandler(SmsSendingService smsSendingService) {
        this.smsSendingService = smsSendingService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        logger.info("SmsWebSocket connection established: {}", session.getId());
    }

    @Override
    public void afterConnectionClosed(
            WebSocketSession session,
            @NonNull CloseStatus status
    ) {
        logger.info("SmsWebSocket connection closed: {}", session.getId());
    }

    @Override
    protected void handleTextMessage(
             @NonNull WebSocketSession session,
             TextMessage message
    ) throws Exception {
        SmsRequest smsRequest = new ObjectMapper()
                .readValue(message.getPayload(), SmsRequest.class);
        smsSendingService.sendSms(
                smsRequest.ids,
                smsRequest.message,
                session
        );
    }

    public static class SmsRequest {
        public String message;
        public List<Long> ids;
    }
}
