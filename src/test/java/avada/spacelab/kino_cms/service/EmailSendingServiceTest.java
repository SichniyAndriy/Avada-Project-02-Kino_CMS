package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.repository.UserRepository;
import avada.spacelab.kino_cms.service.impl.EmailSendingServiceImpl;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailSendingServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private JavaMailSender mailSender;
    @Mock
    WebSocketSession session;
    @Captor
    private ArgumentCaptor<SimpleMailMessage> emailMessageCaptor;
    @Captor
    private ArgumentCaptor<TextMessage> textMessageCaptor;
    @InjectMocks
    private EmailSendingServiceImpl emailSendingService;

    private final int SIZE = 3;
    private final int ONCE = 1;


    @TestFactory
    @DisplayName("Test sendEmail()")
    List<DynamicNode> sendEmail() {
        return List.of(
                dynamicTest(
                        "With null ids and valid file",
                        () -> {
                            when(userRepository.findAllEmails())
                                    .thenReturn(List.of("a@a.com", "b@b.com", "c@c.com"));
                            doNothing().when(mailSender).send(emailMessageCaptor.capture());
                            doNothing().when(session).sendMessage(textMessageCaptor.capture());
                            doNothing().when(session).close(any());

                            emailSendingService.sendEmail(null, "greeting.html", session);

                            verify(userRepository, times(ONCE)).findAllEmails();
                            verify(mailSender, times(ONCE)).send(emailMessageCaptor.capture());
                            verify(session, times(SIZE)).sendMessage(textMessageCaptor.capture());
                        }
                ),
                dynamicTest(
                        "With exist ids and valid file",
                        () -> {
                            when(userRepository.findEmailById(anyLong()))
                                    .thenReturn(Optional.of("a@a.com"))
                                    .thenReturn(Optional.of("b@b.com"))
                                    .thenReturn(Optional.of("c@c.com"));
                            doNothing().when(mailSender).send(emailMessageCaptor.capture());
                            doNothing().when(session).sendMessage(textMessageCaptor.capture());
                            doNothing().when(session).close(any());

                            emailSendingService.sendEmail(List.of(1L, 2L, 3L), "greeting.html", session);

                            verify(userRepository, times(SIZE)).findEmailById(anyLong());
                            verify(mailSender, times(2)).send(emailMessageCaptor.capture());
                            verify(session, times(SIZE * 2)).sendMessage(textMessageCaptor.capture());
                        }
                ),
                dynamicTest(
                        "With invalid file",
                        () -> {
                            assertThrows(
                                    IOException.class,
                                    () -> emailSendingService.sendEmail(null, "", session)
                            );

                            verify(userRepository, times(ONCE)).findAllEmails();
                            verify(userRepository, times(SIZE)).findEmailById(anyLong());
                            verify(mailSender, times(2)).send(emailMessageCaptor.capture());
                            verify(session, times(SIZE * 2)).sendMessage(textMessageCaptor.capture());
                        }
                )
        );
    }

}
