package wearme.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wearme.server.model.Address;

/**
 * Интерфейс JpaRepository для работы с адресами
 */
@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    /**
     * Получает адрес по идентификатору
     * @param id - идентификатор
     * @return address
     */
    Address findAddressById(Long id);
}
