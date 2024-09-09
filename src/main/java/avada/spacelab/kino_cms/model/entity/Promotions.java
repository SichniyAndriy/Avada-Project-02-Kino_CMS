package avada.spacelab.kino_cms.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "promotions")
public class Promotions {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "promotions_gen")
    @SequenceGenerator(name = "promotions_gen", sequenceName = "promotions_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "main_picture", nullable = false)
    private String mainPicture;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = SeoBlock.class, orphanRemoval = true)
    @PrimaryKeyJoinColumn(name = "seo_block_id", referencedColumnName = "id")
    private SeoBlock seoBlock;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;
}
