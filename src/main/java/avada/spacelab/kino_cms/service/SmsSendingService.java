package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.repository.UserRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
public class SmsSendingService {
    private final UserRepository userRepository;
    private final Logger logger = LogManager.getLogger(SmsSendingService.class);

    public SmsSendingService(
            @Autowired UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Async
    public void sendSms(List<Long> ids, String message, WebSocketSession session) throws IOException {
        List<String> phoneList;
        if (ids == null) {
            phoneList = userRepository.findAllPhones();
        } else {
            phoneList = new ArrayList<>();
            for (Long id : ids) {
                String phoneNumberById = userRepository.findPhoneById(id);
                phoneList.add(phoneNumberById);
            }
        }
        int amountUsers = phoneList.size();
        int sentSms = 0;
        for (String phone : phoneList) {
            String res = String.valueOf((++sentSms * 100) / amountUsers);
            logger.info("Sending SMS to phone number: {} for session: {}", phone, session.getId());
            session.sendMessage(new TextMessage(res));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        session.close(CloseStatus.NORMAL);
    }
}
