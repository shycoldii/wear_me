package wearme.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wearme.server.model.ClientHistory;
/**
 * Интерфейс JpaRepository для работы с историей клиентов
 */
@Repository
public interface ClientHistoryRepository extends JpaRepository<ClientHistory,Long> {

}
