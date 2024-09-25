package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    List<User> findAll();

    @Override
    Page<User> findAll(Pageable pageable);

    @Override
    Optional<User> findById(Long id);

    @Override
    User save(User user);

    @Override
    void deleteById(Long id);

    @Query("SELECT u.phone FROM User u")
    List<String>findAllPhones();

    @Query("SELECT u.phone FROM User u WHERE u.id = :id")
    String findPhoneById(Long id);

    @Query("SELECT u.email FROM User u")
    List<String> findAllEmails();

    @Query("SELECT u.email FROM User u WHERE u.id = :id")
    String findEmailById(Long id);
}
