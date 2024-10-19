package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.EditPage;
import avada.spacelab.kino_cms.model.entity.EditPage.EditPageType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditPageRepository extends JpaRepository<EditPage, Long> {

    Optional<EditPage> findByType(EditPageType editPageType);
}
