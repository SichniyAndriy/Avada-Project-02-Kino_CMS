package avada.spacelab.kino_cms.model.dto.user;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record SchedulePageResponceDto(
        LocalDate date,
        LocalTime time,
        String movie,
        String theater,
        Integer auditorium,
        BigDecimal price
) {
}
