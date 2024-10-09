package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.MainPageBanners;
import avada.spacelab.kino_cms.model.entity.MainPageBanners.Replacement;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainPageBannersRepository extends JpaRepository<MainPageBanners, Long> {

    List<MainPageBanners> findAllByPlace(Replacement place);

    void removeAllByPlaceEquals(Replacement place);
}
