package avada.spacelab.kino_cms.service.impl;

import avada.spacelab.kino_cms.repository.UserRepository;
import avada.spacelab.kino_cms.service.SmsSendingService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
public class SmsSendingServiceImpl implements SmsSendingService {
    private final UserRepository userRepository;
    private final Logger logger = LogManager.getLogger(SmsSendingServiceImpl.class);

    public SmsSendingServiceImpl(UserRepository userRepository) {
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
                Optional<String> optionalPhone = userRepository.findPhoneById(id);
                optionalPhone.ifPresent(phone -> phoneList.add(phone));
            }
        }
        int amountUsers = phoneList.size();
        int sentSms = 0;
        for (String phone : phoneList) {
            logger.info("Sending SMS to phone number: {} for session: {}", phone, session.getId());
            String res = String.valueOf((++sentSms * 100) / amountUsers);
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
