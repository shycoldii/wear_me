package wearme.server.model;

import javax.persistence.*;
/**
 * Сущность адресов
 */
@Entity
@Table(name = "addresses")
public class Address {
    /**
     * Инициализация класса
     */
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

    /**
     * Получает идентификатор
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Получает город
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * Получает улицу
     * @return street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Получает подъезд
     * @return porch
     */
    public String getPorch() {
        return porch;
    }

    /**
     * Получает этаж
     * @return floor
     */
    public String getFloor() {
        return floor;
    }

    /**
     * Получает код улицы
     * @return streetCode
     */
    public Integer getStreetCode() {
        return streetCode;
    }

    /**
     * Получает номер квартиры
     * @return apartment
     */
    public Integer getApartment() {
        return apartment;
    }

    /**
     * Представляет объект в виде строки
     * @return String - преобразованный объект
     */
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
