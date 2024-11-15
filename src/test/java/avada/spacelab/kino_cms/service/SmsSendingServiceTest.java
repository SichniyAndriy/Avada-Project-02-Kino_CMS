package avada.spacelab.kino_cms.service;

import avada.spacelab.kino_cms.repository.UserRepository;
import avada.spacelab.kino_cms.service.admin.impl.SmsSendingServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SmsSendingServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    WebSocketSession session;
    @InjectMocks
    private SmsSendingServiceImpl smsSendingService;

    private final int SIZE = 3;
    private final int ONCE = 1;


    @TestFactory
    @DisplayName("Test sendSms()")
    List<DynamicNode> test_sendSms() {
        return List.of(
                dynamicTest(
                        "With null ids",
                        () -> {
                            when(userRepository.findAllPhones())
                                    .thenReturn(List.of("123", "456", "789"));
                            doNothing().when(session).sendMessage(any());
                            doNothing().when(session).close(any());

                            smsSendingService.sendSms(null, "greeting", session);

                            verify(userRepository, times(ONCE)).findAllPhones();
                            verify(session, times(SIZE)).sendMessage(any());
                        }
                ),
                dynamicTest(
                        "With exist ids",
                        () -> {
                            when(userRepository.findPhoneById(anyLong()))
                                    .thenReturn(Optional.of("123"))
                                    .thenReturn(Optional.of("456"))
                                    .thenReturn(Optional.of("789"));
                            doNothing().when(session).sendMessage(any());
                            doNothing().when(session).close(any());

                            smsSendingService.sendSms(List.of(1L, 2L, 3L), "greeting", session);

                            verify(userRepository, times(SIZE)).findPhoneById(anyLong());
                            verify(session, times(SIZE * 2)).sendMessage(any());
                        }
                )
        );
    }

}
