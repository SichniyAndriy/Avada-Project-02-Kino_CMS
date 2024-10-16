package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.Contact;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Override
    List<Contact> findAll();

    @Override
    Optional<Contact> findById(Long aLong);

    @Override
    <S extends Contact> List<S> saveAllAndFlush(Iterable<S> entities);

    @Override
    <S extends Contact> S save(S entity);

    @Override
    void deleteById(Long aLong);
}
