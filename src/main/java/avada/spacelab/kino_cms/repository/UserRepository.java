package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Override @NonNull
    List<User> findAll();

    @Override @NonNull
    Page<User> findAll(@NonNull Pageable pageable);

    @Override @NonNull
    Optional<User> findById(@NonNull Long id);

    @Override @NonNull
    User save(@NonNull User user);

    @Override
    void deleteById(@NonNull Long id);

    @Query("SELECT u.phone FROM User u")
    List<String>findAllPhones();

    @Query("SELECT u.phone FROM User u WHERE u.id = :id")
    Optional<String> findPhoneById(@Param("id") Long id);

    @Query("SELECT u.email FROM User u")
    List<String> findAllEmails();

    @Query("SELECT u.email FROM User u WHERE u.id = :id")
    Optional<String> findEmailById(@Param("id") Long id);
}
