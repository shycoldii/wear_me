package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.model.Address;

/**
 * Интерфейс JpaRepository для работы с адресами
 */
@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    Address findAddressByStreetCode(Integer street_code);
    Address findAddressByStreetAndApartment(String street, Integer apartment);
    Address findAddressById(Long id);
}
