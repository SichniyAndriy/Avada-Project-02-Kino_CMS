package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.News;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

public interface NewsRepository extends JpaRepository<News, Long> {
   @Override @NonNull
   List<News> findAll();

   @Override
   Optional<News> findById(Long id);

   @Override
   void deleteById(Long id);
}
