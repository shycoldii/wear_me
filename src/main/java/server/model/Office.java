package server.model;

import javax.persistence.*;
import java.util.List;
/**
 * Сущность офисов
 */
@Entity
@Table(name = "offices")
public class Office {
    public Office(){}

    /**
     * Конструктор для сущности офиса
     * @param name - название офиса
     * @param phone_number - телефонный номер офиса
     */
    public Office(String name,String phone_number){
        this.phoneNumber = phone_number;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Address getAddressId() {
        return addressId;
    }

    public void setAddress_id(Address address_id) {
        this.addressId = address_id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Column(name = "phoneNumber", length = 128, nullable = false,unique = true)
    private String phoneNumber;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "addressId",nullable = false,unique = true)
    private Address addressId;

    @Override
    public String toString() {
        return "Office{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", addressId=" + addressId.toString() +
                '}';
    }
}
