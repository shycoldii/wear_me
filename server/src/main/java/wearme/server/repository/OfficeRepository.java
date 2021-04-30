package wearme.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wearme.server.model.Office;
/**
 * Интерфейс JpaRepository для работы с офисами
 */
@Repository
public interface OfficeRepository extends JpaRepository<Office,Long> {
   /**
    * Получает офис по идентификатору
    * @param id - идентификатор
    * @return office
    */
   Office findOfficeById(Long id);
}
