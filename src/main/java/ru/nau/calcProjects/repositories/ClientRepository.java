package ru.nau.calcProjects.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.nau.calcProjects.models.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByTitle(String title);
    List<Client> findByTitleContaining(String title, Sort sort);
    Optional<Client> findFirstByTitleContaining(String title);
}
