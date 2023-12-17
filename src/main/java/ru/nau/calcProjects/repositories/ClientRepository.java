package ru.nau.calcProjects.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.nau.calcProjects.models.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @EntityGraph(attributePaths = "owner")
    Optional<Client> findByTitle(String title);
    @EntityGraph(attributePaths = "owner")
    List<Client> findByTitleContaining(String title, Sort sort);
    @EntityGraph(attributePaths = "owner")
    List<Client> findByOwnerIdAndTitleContaining(Long userId, String title, Sort sort);
    @EntityGraph(attributePaths = "owner")
    List<Client> findByOwnerId(Long ownerId, Sort sort);
}
