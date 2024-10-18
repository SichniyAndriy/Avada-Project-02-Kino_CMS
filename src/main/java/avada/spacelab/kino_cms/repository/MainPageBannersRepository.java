package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.MainPageBanners;
import avada.spacelab.kino_cms.model.entity.MainPageBanners.Replacement;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface MainPageBannersRepository extends JpaRepository<MainPageBanners, Long> {

    List<MainPageBanners> findAllByPlace(Replacement place);

    @Modifying @Transactional
    void removeAllByPlaceEquals(Replacement place);
}
