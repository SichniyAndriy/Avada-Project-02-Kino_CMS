package avada.spacelab.kino_cms.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movies_gen")
    @SequenceGenerator(name = "movies_gen", sequenceName = "movies_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    @Column(name = "native_title", nullable = false)
    private String nativeTitle;

    @Column(name = "ua_title", nullable = false)
    private String uaTitle;

    @Column(name = "description", length = 2048)
    private String description;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "trailer_url")
    private String trailerUrl;

    @Column(name = "details_url")
    private String detailsUrl;

    @Column(name = "has_2D", nullable = false)
    private Boolean has2D = false;

    @Column(name = "has_3D", nullable = false)
    private Boolean has3D = false;

    @Column(name = "has_imax", nullable = false)
    private Boolean hasImax = false;

    @Column(name = "age_cenz", nullable = false)
    private Integer ageCenz;

    @Embedded
    private MovieDetails details;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = SeoBlock.class, orphanRemoval = true)
    private SeoBlock seoBlock;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = MoviePicture.class, orphanRemoval = true, mappedBy = "movie")
    private List<MoviePicture> pictures = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Schedule.class, mappedBy = "key.movie")
    private List<Schedule> schedules = new ArrayList<>();
}
