package shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.model.Check;
@Repository
public interface CheckRepository extends JpaRepository<Check,Long> {
}
