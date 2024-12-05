package avada.spacelab.kino_cms.service.admin.impl;

import avada.spacelab.kino_cms.controller.util.ControllerUtil;
import avada.spacelab.kino_cms.repository.UserRepository;
import avada.spacelab.kino_cms.service.admin.EmailSendingService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
public class EmailSendingServiceImpl implements EmailSendingService {

    private final UserRepository userRepository;

    private final Logger logger = LogManager.getLogger(EmailSendingServiceImpl.class);


    public EmailSendingServiceImpl(
            @Autowired UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Async
    public void sendEmail(
            List<Long> ids,
            String fileName,
            WebSocketSession session
    ) throws IOException, InterruptedException {
        String emailContent = getEmailContent(fileName);
        List<String> emailList = createEmailList(ids);

        int emailCount = emailList.size();
        int sentEmails = 0;
        for (String email : emailList) {
            logger.info("Sending email to address: {} for session: {}", email, session.getId());
            String res = String.valueOf(++sentEmails * 100 / emailCount);
            session.sendMessage(new TextMessage(res));
            Thread.sleep(50);
        }
        session.close(CloseStatus.NORMAL);
    }

    private List<String> createEmailList(List<Long> ids) {
        List<String> emailList;
        if (ids == null) {
            emailList = userRepository.findAllEmails();
        } else {
            emailList = new ArrayList<>();
            for (Long id : ids) {
                Optional<String> optionalEmail = userRepository.findEmailById(id);
                optionalEmail.ifPresent(emailList::add);
            }
        }
        return emailList;
    }

    private String getEmailContent(String fileName) throws IOException {
        List<String> allLines = Files.readAllLines(
                Path.of(ControllerUtil.PATH_TO_SENT_EMAIL + "/" + fileName)
        );
        return String.join("/n", allLines);
    }

}
