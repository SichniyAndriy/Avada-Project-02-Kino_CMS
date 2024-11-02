package avada.spacelab.kino_cms.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "schedules")
public class Schedule {

    @EmbeddedId
    private ScheduleCompositeKey key;

    public Schedule(
            Auditorium auditorium,
            Movie movie,
            LocalDate date,
            LocalTime time
    ) {
        this.key = new ScheduleCompositeKey(auditorium, movie, date, time);
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    @Embeddable
    public static class ScheduleCompositeKey implements Serializable {
        @ManyToOne(targetEntity = Auditorium.class)
        @JoinColumn(name = "auditorium_id")
        private Auditorium auditorium;

        @ManyToOne(targetEntity = Movie.class)
        @JoinColumn(name = "movie_id")
        private Movie movie;

        @Temporal(TemporalType.DATE)
        @Column(name = "date", nullable = false)
        private LocalDate date;

        @Temporal(TemporalType.TIME)
        @Column(name = "time", nullable = false)
        private LocalTime time;
    }

}
