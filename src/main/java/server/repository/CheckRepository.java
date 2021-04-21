package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.model.Check;

import java.util.List;
/**
 * Интерфейс JpaRepository для работы с чеками
 */
@Repository
public interface CheckRepository extends JpaRepository<Check,Long> {
    List<Check> findAll();
    Check findCheckById(Long id);
}
