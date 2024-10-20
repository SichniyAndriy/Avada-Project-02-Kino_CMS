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
@Entity @Table(name = "auditorium_pictures")
public class AuditoriumPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auditorium_picture_gen")
    @SequenceGenerator(name = "auditorium_picture_gen", sequenceName = "auditorium_picture_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    @Column(name = "path",length = 512, nullable = false)
    private String path;

    @ManyToOne(targetEntity = Auditorium.class)
    @JoinColumn(name = "auditorium_id", referencedColumnName = "id", nullable = false)
    private Auditorium auditorium;
}
