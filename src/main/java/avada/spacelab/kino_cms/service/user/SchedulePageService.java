package avada.spacelab.kino_cms.service.user;

import avada.spacelab.kino_cms.model.dto.user.SchedulePageResponceDto;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import org.jetbrains.annotations.NotNull;

public interface SchedulePageService {
    @NotNull ConcurrentNavigableMap<LocalDate, List<SchedulePageResponceDto>> getTableEntries();

    List<String> movies();

    List<String> theaters();

    List<String> auditoriums();

    List<String> dates();

    ConcurrentMap<LocalDate, List<SchedulePageResponceDto>> getChoiceResponce(
            boolean is2D,
            boolean is3D,
            boolean isImax,
            String theater,
            String dateString,
            String movie,
            String auditorium
    );
}
