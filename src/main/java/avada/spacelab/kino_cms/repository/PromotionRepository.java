package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.Promotion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    @Override @NonNull
    List<Promotion> findAll();

    @Override
    Optional<Promotion> findById(Long id);

    @Override
    void deleteById(Long id);
}
