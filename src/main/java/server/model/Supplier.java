package server.model;

import javax.persistence.*;
/**
 * Сущность поставщиков
 */
@Entity
@Table(name = "suppliers")
public class Supplier {
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Конструктор для поставщиков
     * @param name - имя поставщика
     * @param phoneNumber - телефонный номер
     * @param email - электронная почта
     */
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

    @Column(name = "phoneNumber", length = 128, nullable = false,unique = true)
    private String phoneNumber;

    @Column(name = "email", length = 128, nullable = false,unique = true)
    private String email;


}
