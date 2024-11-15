package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.Promotion;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    @Override @NonNull
    List<Promotion> findAll();

    @Override
    @NotNull
    Optional<Promotion> findById(@NotNull Long id);

    @Override
    void deleteById(@NotNull Long id);

}
