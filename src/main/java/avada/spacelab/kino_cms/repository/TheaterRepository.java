package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.Theater;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {
    @Override @NonNull
    List<Theater> findAll();

    Theater findTheaterById(long id);
}
