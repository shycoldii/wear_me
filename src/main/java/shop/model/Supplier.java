package shop.model;

import javax.persistence.*;

@Entity
@Table(name = "suppliers")
public class Supplier {
    public Supplier(String name, String phoneNumber, String email){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    };
    public Supplier(){};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Column(name = "phoneNumber", length = 128, nullable = false)
    private String phoneNumber;

    @Column(name = "email", length = 128, nullable = false)
    private String email;


}