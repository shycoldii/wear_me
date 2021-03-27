package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
      Client findClientById(Long id);
      Client findClientByEmail(String email);
      Client findClientByPhoneNumber(String phone);
      Client findClientByPhoneNumberAndEmail(String phone,String email);
}
