package avada.spacelab.kino_cms.repository;

import avada.spacelab.kino_cms.model.entity.Contact;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Override
    @NotNull
    List<Contact> findAll();

    @Override
    @NotNull
    Optional<Contact> findById(@NotNull Long aLong);

    @Override
    <S extends Contact> @NotNull List<S> saveAllAndFlush(@NotNull Iterable<S> entities);

    @Override
    <S extends Contact> @NotNull S save(@NotNull S entity);

    @Override
    void deleteById(@NotNull Long aLong);

}
