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
@Entity @Table(name = "edit_page_picture")
public class EditPagePicture {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "edit_page_picture_gen")
    @SequenceGenerator(name = "edit_page_picture_gen", sequenceName = "edit_page_picture_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    @Column(name = "path", length = 512)
    private String path;

    @ManyToOne(targetEntity = EditPage.class)
    @JoinColumn(name = "edit_page_id", referencedColumnName = "id")
    private EditPage editPage;
}
