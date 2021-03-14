package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.model.Check;

import java.util.List;

@Repository
public interface CheckRepository extends JpaRepository<Check,Long> {
    List<Check> findAll();
}
