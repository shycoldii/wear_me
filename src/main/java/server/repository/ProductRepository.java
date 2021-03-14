package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
   Product findProductByArticul(Long articul);
}
