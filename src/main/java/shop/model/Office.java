package shop.model;

import javax.persistence.*;

@Entity
@Table(name = "offices")
public class Office {
    public Office(){}
    public Office(String name,String phone_number){
        this.phoneNumber = phone_number;
        this.name = name;
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

    @Column(name = "phoneNumber", length = 128, nullable = false)
    private String phoneNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addressId")
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
