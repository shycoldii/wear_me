package server.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "supplyHistory")
public class SupplyHistory {
    public SupplyHistory(){};
    public SupplyHistory(LocalDate date){
        this.date = date;
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "employee")
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "storeProduct")
    private StoreProduct storeProduct;

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Employee getEmployee() {
        return employee;
    }

    public StoreProduct getStoreProduct() {
        return storeProduct;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setStoreProduct(StoreProduct storeProduct) {
        this.storeProduct = storeProduct;
    }
}
