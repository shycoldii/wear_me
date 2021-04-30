package wearme.server.repository;
import wearme.server.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 * Интерфейс JpaRepository для работы с позициями
 */
@Repository
public interface PositionRepository extends JpaRepository<Position,Long> {
    /**
     * Получает роль по идентификатору
     * @param id - идентификатор
     * @return position
     */
    Position findPositionById(Long id);

    /**
     * Получает все роли
     * @return list of positions
     */
    List<Position> findAll();

    /**
     * Удаляет все роли
     */
    void deleteAll();
}
