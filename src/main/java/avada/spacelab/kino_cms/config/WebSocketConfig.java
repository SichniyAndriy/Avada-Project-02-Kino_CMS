package avada.spacelab.kino_cms.config;

import avada.spacelab.kino_cms.service.admin.EmailSendingService;
import avada.spacelab.kino_cms.service.admin.SmsSendingService;
import avada.spacelab.kino_cms.socket.EmailSendingHandler;
import avada.spacelab.kino_cms.socket.SmsSendingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final SmsSendingService smsSendingService;
    private final EmailSendingService emailSendingService;


    public WebSocketConfig(
            @Autowired SmsSendingService smsSendingService,
            @Autowired EmailSendingService emailSendingService
    ) {
        this.smsSendingService = smsSendingService;
        this.emailSendingService = emailSendingService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(
                new SmsSendingHandler(smsSendingService), "/admin/sending/sms");
        registry.addHandler(
                new EmailSendingHandler(emailSendingService), "/admin/sending/email"
        );
    }
}
