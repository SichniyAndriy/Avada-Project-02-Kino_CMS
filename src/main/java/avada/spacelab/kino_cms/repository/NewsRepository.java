package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.News;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface NewsRepository extends JpaRepository<News, Long> {

    @Override
    @NonNull
    List<News> findAll();

    @Override
    @NotNull
    Optional<News> findById(@NotNull Long id);

    @Override
    void deleteById(@NotNull Long id);

}
