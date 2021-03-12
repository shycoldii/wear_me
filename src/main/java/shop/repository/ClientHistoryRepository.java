package shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.model.ClientHistory;

@Repository
public interface ClientHistoryRepository extends JpaRepository<ClientHistory,Long> {

}
