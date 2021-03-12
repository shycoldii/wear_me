package shop.model;

import javax.persistence.*;

@Entity
@Table(name = "storeProducts")
public class StoreProduct {

    public StoreProduct(){};

    public StoreProduct(Integer supplyId, Integer status){
        this.supplyId = supplyId;
        this.status = status;
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "supplyId", nullable = false)
    private Integer supplyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office")
    private Office office;

    public Long getId() {
        return id;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
