package wearme.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wearme.server.model.Promocode;
/**
 * Интерфейс JpaRepository для работы с промокодами
 */
@Repository
public interface PromocodeRepository extends JpaRepository<Promocode,Long> {
    /**
     * Получает промокод по названию
     * @param name - название
     * @return promocode
     */
    Promocode findPromocodeByName(String name);
}
