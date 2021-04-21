package server.repository;
import server.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 * Интерфейс JpaRepository для работы с позициями
 */
@Repository
public interface PositionRepository extends JpaRepository<Position,Long> {
    Position findPositionById(Long id);
    Position findPositionByName(String name);
    List<Position> findAll();
    void deleteAll();
}
