package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.model.Promocode;
/**
 * Интерфейс JpaRepository для работы с промокодами
 */
@Repository
public interface PromocodeRepository extends JpaRepository<Promocode,Long> {
    Promocode findPromocodeByName(String name);
}
