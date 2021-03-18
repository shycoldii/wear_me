package server.model;

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

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "employee",nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "client",nullable = false)
    private Client client;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "storeProducts",nullable = false)
    private List<StoreProduct> storeProducts;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
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

    @Override
    public String toString() {
        return "Check{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", employee=" + employee +
                ", client=" + client +
                ", storeProducts=" + storeProducts +
                ", promocode=" + promocode +
                '}';
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Client getClient() {
        return client;
    }

    public List<StoreProduct> getStoreProducts() {
        return storeProducts;
    }

    public Promocode getPromocode() {
        return promocode;
    }
}
