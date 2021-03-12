package shop.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "checks")
public class Check {
    public Check(){};
    public Check(LocalDateTime dateTime){
        this.dateTime = dateTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="dateTime", nullable = false)
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client")
    private Client client;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeProducts")
    private List<StoreProduct> storeProducts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promocode")
    private Promocode promocode;

    public void setClient(Client client) {
        this.client = client;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setPromocode(Promocode promocode) {
        this.promocode = promocode;
    }

    public void setStoreProducts(List<StoreProduct> storeProducts) {
        this.storeProducts = storeProducts;
    }
}
