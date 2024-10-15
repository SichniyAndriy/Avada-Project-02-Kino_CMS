package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.MainPageInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainPageInfoRepository extends JpaRepository<MainPageInfo, Long> {
    @Override
    <S extends MainPageInfo> S save(S entity);

    @Override
    Optional<MainPageInfo> findById(Long aLong);
}
