package shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.model.Promocode;

@Repository
public interface PromocodeRepository extends JpaRepository<Promocode,Long> {
    Promocode findPromocodeByName(String name);
}
