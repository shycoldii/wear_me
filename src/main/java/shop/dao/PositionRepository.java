package shop.dao;
import shop.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position,Long> {
    Position findPositionById(Long id);
    List<Position> findAll();
    void deleteAll();
}
