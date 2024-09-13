package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.Promotion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionsRepository extends JpaRepository<Promotion, Integer> {

    @Override
    List<Promotion> findAll();

    Promotion findById(int id);
}
