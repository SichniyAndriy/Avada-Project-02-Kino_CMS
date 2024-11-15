package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.MainPageInfo;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainPageInfoRepository extends JpaRepository<MainPageInfo, Long> {

    @Override
    <S extends MainPageInfo> @NotNull S save(@NotNull S entity);

    @Override
    @NotNull
    Optional<MainPageInfo> findById(@NotNull Long aLong);

}
