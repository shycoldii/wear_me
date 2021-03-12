package shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    Address findAddressByStreetCode(Integer street_code);
    Address findAddressByStreetAndApartment(String street, Integer apartment);
}
