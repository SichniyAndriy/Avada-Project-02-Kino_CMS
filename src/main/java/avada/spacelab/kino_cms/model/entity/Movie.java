package avada.spacelab.kino_cms.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Column(name = "has_2D")
    private Boolean has2D = false;

    @Column(name = "has_3D")
    private Boolean has3D = false;

    @Column(name = "has_imax")
    private Boolean hasImax = false;

    @Column(name = "age_cenz", nullable = false)
    private Integer ageCenz;

    @Embedded
    private MovieDetails details;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            targetEntity = MoviePicture.class, orphanRemoval = true, mappedBy = "movie")
    private List<MoviePicture> pictures = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            targetEntity = Schedule.class, mappedBy = "key.movie")
    private List<Schedule> schedules = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, targetEntity = SeoBlock.class, orphanRemoval = true)
    @JoinColumn(name = "seo_block_id", referencedColumnName = "id")
    private SeoBlock seoBlock;

}
