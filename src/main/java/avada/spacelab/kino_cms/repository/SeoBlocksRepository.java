package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.SeoBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeoBlocksRepository extends JpaRepository<SeoBlock, Long> {
    @Override
    <S extends SeoBlock> S save(S entity);
}
