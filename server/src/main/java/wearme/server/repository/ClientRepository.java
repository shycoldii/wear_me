package wearme.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wearme.server.model.Client;
/**
 * Интерфейс JpaRepository для работы с клиентами
 */
@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
      /**
       * Получает клиента по идентификатору
       * @param id - идентификатор
       * @return client
       */
      Client findClientById(Long id);

      /**
       * Получает клиента по почте
       * @param email - почта
       * @return client
       */
      Client findClientByEmail(String email);

      /**
       * Получает клиента по номеру телефона
       * @param phone - номер телефона
       * @return client
       */
      Client findClientByPhoneNumber(String phone);

      /**
       * Получает клиента по номеру телефона и почте
       * @param phone - номер телефона
       * @param email - почта
       * @return client
       */
      Client findClientByPhoneNumberAndEmail(String phone,String email);
}
