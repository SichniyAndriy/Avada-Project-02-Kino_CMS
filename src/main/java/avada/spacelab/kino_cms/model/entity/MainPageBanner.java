package avada.spacelab.kino_cms.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "main_page_banners")
public class MainPageBanner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "main_page_gen")
    @SequenceGenerator(name = "main_page_gen", sequenceName = "main_page_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    @Column(name = "paths", length = 1024)
    private String path;

    @Column(name = "texts", length = 1024)
    private String text;

    @Enumerated(EnumType.STRING)
    private Replacement place;

    public enum Replacement {
        UP_BANNER,
        SLASH_BANNER,
        BOTTOM_PROMOTION
    }
}
