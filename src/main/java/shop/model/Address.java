package shop.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "addresses")
public class Address {
    public Address(){}
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
    @Column(name = "apartment", nullable = false)
    private Integer apartment;
    //@OneToOne( cascade = CascadeType.ALL, orphanRemoval = true)
    //private Office office;


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
