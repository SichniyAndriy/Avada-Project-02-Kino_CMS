package avada.spacelab.kino_cms.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Entity @Table(name = "theaters")
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "theater_gen")
    @SequenceGenerator(name = "theater_gen", sequenceName = "theater_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", length = 2048, nullable = false)
    private String description;

    @Column(name = "conditions")
    private String conditions;

    @Column(name = "logo_url", nullable = false)
    private String logoUrl;

    @Column(name= "main_banner_url", nullable = false)
    private String mainBannerUrl;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = TheaterPicture.class, mappedBy = "theater")
    private List<TheaterPicture> pictures = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Auditorium.class, mappedBy = "theater")
    private List<Auditorium> auditoriums = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, targetEntity = SeoBlock.class, orphanRemoval = true)
    @JoinColumn(name = "seo_block_id", referencedColumnName = "id")
    private SeoBlock seoBlock;
}
