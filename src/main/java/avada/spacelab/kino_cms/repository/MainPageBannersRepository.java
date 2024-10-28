package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.MainPageBanner;
import avada.spacelab.kino_cms.model.entity.MainPageBanner.Replacement;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface MainPageBannersRepository extends JpaRepository<MainPageBanner, Long> {

    List<MainPageBanner> findAllByPlace(Replacement place);

    @Modifying @Transactional
    void removeAllByPlaceEquals(Replacement place);
}
