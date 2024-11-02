package avada.spacelab.kino_cms.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
@Entity @Table(name = "edit_pages")
public class EditPage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "edit_pages_gen")
    @SequenceGenerator(name = "edit_pages_gen", sequenceName = "edit_pages_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", length = 1024)
    private String description;

    @Column(name = "banner_url", length = 512)
    private String bannerUrl;

    @OneToMany(targetEntity = EditPagePicture.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER,
            orphanRemoval = true, mappedBy = "editPage")
    private List<EditPagePicture> pictures = new ArrayList<>();

    @Column(name = "video_url", length = 512)
    private String videoUrl;

    @OneToOne(targetEntity = SeoBlock.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "seo_block_id", referencedColumnName = "id")
    private SeoBlock seoBlock;

    @Enumerated(EnumType.STRING)
    private EditPageType type;

    public enum EditPageType {
        ABOUT,
        CAFE_BAR,
        VIP_ROOM,
        ADVERTISING,
        CHILD_ROOM,
    }

}
