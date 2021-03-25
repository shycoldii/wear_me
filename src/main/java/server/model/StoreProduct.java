package server.model;

import javax.persistence.*;

@Entity
@Table(name = "storeProducts")
public class StoreProduct {

    public StoreProduct(){};

   // public StoreProduct(){
        //this.supplyId = supplyId;
        //this.status = status;
   // };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    //@Column(name = "status", nullable = false)
    //private Integer status;

    //@Column(name = "supplyId", nullable = false)
    //private Integer supplyId;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "product",nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "checkId")
    private Check check;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "office",nullable = false)
    private Office office;

    public Long getId() {
        return id;
    }

    //public Integer getStatus() {
       // return status;
   // }

    //public Integer getSupplyId() {
       // return supplyId;
   // }

    public Product getProduct() {
        return product;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "StoreProduct{" +
                "id=" + id +
                ", product=" + product +
                ", office=" + office +
                '}';
    }
}
