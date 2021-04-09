package server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "returnsHistory")
public class ReturnsHistory {
    public ReturnsHistory(){};
    public ReturnsHistory(Integer total){
        this.total = total;
    };
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="total", nullable = false)
    private Integer total;

    @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "storeProduct",nullable = false)
   private StoreProduct storeProduct;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee",nullable = false)
    private Employee employee;

    public StoreProduct getStoreProduct() {
        return storeProduct;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStoreProduct(StoreProduct storeProduct) {
        this.storeProduct = storeProduct;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setCheck(Check check) {
        this.check = check;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Check getCheck() {
        return check;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "check_id",nullable = false)
    private Check check;


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public void setTotal(Integer total) {
        this.total = total;
    }


//    public void setCheck(Check check) {
//        this.check = check;
//    }

    public Long getId() {
        return id;
    }

    public Integer getTotal() {
        return total;
    }

//
//    public Check getCheck() {
//        return check;
//    }
}
