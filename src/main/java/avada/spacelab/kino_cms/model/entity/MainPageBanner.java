package avada.spacelab.kino_cms.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "main_page_banners")
public class MainPageBanner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
