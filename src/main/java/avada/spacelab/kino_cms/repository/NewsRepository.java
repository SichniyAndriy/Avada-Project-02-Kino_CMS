package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.News;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
   @Override @NonNull
   List<News> findAll();

   News findNewsById(long id);
}
