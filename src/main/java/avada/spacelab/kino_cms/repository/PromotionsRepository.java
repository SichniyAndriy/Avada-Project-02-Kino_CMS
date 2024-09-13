package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.Promotions;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionsRepository extends JpaRepository<Promotions, Integer> {

    @Override
    List<Promotions> findAll();
}
