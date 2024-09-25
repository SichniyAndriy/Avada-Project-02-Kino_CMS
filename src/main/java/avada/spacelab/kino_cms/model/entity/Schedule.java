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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "schedule")
public class Schedule {
    @EmbeddedId
    private ScheduleCompositeKey key;

    public Schedule(LocalDate date, LocalTime time, Movie movie, Auditorium auditorium) {
        this.key = new ScheduleCompositeKey(date, time, movie, auditorium);
    }

    @Getter @Setter @NoArgsConstructor
    @Embeddable
    public static class ScheduleCompositeKey implements Serializable {
        @Temporal(TemporalType.DATE)
        @Column(name = "date", nullable = false, updatable = false)
        private LocalDate date;

        @Temporal(TemporalType.TIME)
        @Column(name = "time", nullable = false, updatable = false)
        private LocalTime time;

        @ManyToOne(targetEntity = Movie.class,optional = false)
        @JoinColumn(name = "movie_id", referencedColumnName = "id")
        private Movie movie;

        @ManyToOne(targetEntity = Auditorium.class, optional = false)
        @JoinColumn(name = "auditorium_id", referencedColumnName = "id")
        private Auditorium auditorium;

        public ScheduleCompositeKey(LocalDate date, LocalTime time, Movie movie, Auditorium auditorium) {
            this.date = date;
            this.time = time;
            this.movie = movie;
            this.auditorium = auditorium;
        }
    }
}
