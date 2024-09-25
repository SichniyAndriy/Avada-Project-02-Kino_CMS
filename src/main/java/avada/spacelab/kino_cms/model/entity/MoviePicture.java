package avada.spacelab.kino_cms.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "movie_pictures")
public class MoviePicture {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_pictures_gen")
    @SequenceGenerator(name = "movie_pictures_gen", sequenceName = "movie_pictures_seq")
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    @Column(name = "path", length = 512, nullable = false, unique = true)
    private String path;

    @ManyToOne(targetEntity = Movie.class)
    @JoinColumn(name = "movie_id", referencedColumnName = "id", nullable = false)
    private Movie movie;
}
