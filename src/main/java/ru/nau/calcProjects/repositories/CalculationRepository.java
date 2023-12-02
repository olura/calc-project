package ru.nau.calcProjects.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.nau.calcProjects.models.Calculation;

import java.util.List;

public interface CalculationRepository extends JpaRepository<Calculation, Long> {
    List<Calculation> findTop100ByAuthorIdAndClientId(Long authorId, Long clientId, Sort sort);
    List<Calculation> findTop100ByClientId(Long clientId, Sort sort);
    List<Calculation> findTop100ByAuthorId(Long clientId, Sort sort);
    List<Calculation> findTop100ByOrderByCreationDateDesc();
}
