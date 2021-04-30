package wearme.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wearme.server.model.Check;

import java.util.List;

/**
 * Интерфейс JpaRepository для работы с чеками
 */
@Repository
public interface CheckRepository extends JpaRepository<Check,Long> {
    /**
     * Получает список всех чеков
     * @return list of checks
     */
    List<Check> findAll();

    /**
     * Получает чек по идентификатору
     * @param id - идентификатор
     * @return check
     */
    Check findCheckById(Long id);
}
