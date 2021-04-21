package server.model;

import javax.persistence.*;
/**
 * Сущность адресов
 */
@Entity
@Table(name = "addresses")
public class Address {
    public Address(){}

    /**
     * Конструктор для создания адресов
     * @param city - город
     * @param street - улица
     * @param porch - подъезд
     * @param floor - этаж
     * @param street_code - код улицы
     * @param apartment - номер квартиры
     */
    public Address(String city, String street, String porch, String floor,
                   Integer street_code, Integer apartment){
        this.city = city;
        this.street = street;
        this.porch = porch;
        this.floor = floor;
        this.streetCode = street_code;
        this.apartment = apartment;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "city", length = 128, nullable = false)
    private String city;
    @Column(name = "street", length = 128, nullable = false)
    private String street;
    @Column(name = "porch", length = 128)
    private String porch;
    @Column(name = "floor", length = 128)
    private String floor;
    @Column(name = "streetCode", nullable = false)
    private Integer streetCode;
    @Column(name = "apartment")
    private Integer apartment;

    public Long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getPorch() {
        return porch;
    }

    public String getFloor() {
        return floor;
    }

    public Integer getStreetCode() {
        return streetCode;
    }

    public Integer getApartment() {
        return apartment;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", porch='" + porch + '\'' +
                ", floor='" + floor + '\'' +
                ", streetCode=" + streetCode +
                ", apartment=" + apartment +
                '}';
    }
}
