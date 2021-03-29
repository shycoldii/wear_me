package server.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "checks")
public class Check {
    public Check(){};
    public Check(LocalDateTime dateTime,Integer total){

        this.dateTime = dateTime;
        this.total = total;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="dateTime", nullable = false)
    private LocalDateTime dateTime;

    @Column(name="total", nullable = false)
    private Integer total;

    public Integer getTotal() {
        return total;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "employee",nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "client")
    private Client client;

    @Override
    public String toString() {
        return "Check{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", total=" + total +
                ", employee=" + employee +
                ", client=" + client +
                ", promocode=" + promocode +
                '}';
    }

    //@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE,mappedBy = "check")
    //@JoinColumn(name = "storeProducts",nullable = false)
    //private List<StoreProduct> storeProducts;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
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



    public Promocode getPromocode() {
        return promocode;
    }
}
