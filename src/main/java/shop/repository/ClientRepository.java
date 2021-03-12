package shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
      Client findClientById(Long id);
}
