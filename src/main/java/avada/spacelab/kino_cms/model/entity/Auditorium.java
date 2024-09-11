package avada.spacelab.kino_cms.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
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
@Entity @Table(name = "auditoriums")
public class Auditorium {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auditoriums_gen")
    @SequenceGenerator(name = "auditoriums_gen", sequenceName = "auditoriums_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "scheme_url", nullable = false)
    private String schemeUrl;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "main_banner_url", nullable = false)
    private String mainBannerUrl;

    @ManyToOne(targetEntity = Theater.class)
    @PrimaryKeyJoinColumn(name = "theater_id", referencedColumnName = "id")
    private Theater theater;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Schedule.class, mappedBy = "key.auditorium")
    private List<Schedule> schedules = new ArrayList<>();

    @OneToOne(targetEntity = SeoBlock.class)
    @JoinColumn(name = "seo_block_id", referencedColumnName = "id")
    private SeoBlock seoBlock;
}
