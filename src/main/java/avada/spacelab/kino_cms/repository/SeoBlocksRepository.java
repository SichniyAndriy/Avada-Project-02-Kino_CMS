package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.SeoBlock;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeoBlocksRepository extends JpaRepository<SeoBlock, Long> {

    @Override
    <S extends SeoBlock> @NotNull S save(@NotNull S entity);

}
