package server.model;

import javax.persistence.*;

@Entity
@Table(name = "storeProducts")
public class StoreProduct {
    public StoreProduct(){};

    public StoreProduct(Integer articul,String name,Integer tradePrice,Integer retailPrice,
                        String type,String size,String color,String description,Integer status){
        this.articul = articul;
        this.name = name;
        this.tradePrice = tradePrice;
        this.retailPrice= retailPrice;
        this.type=type;
        this.size = size;
        this.color = color;
        this.description = description;
        this.status = status;
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "articul", nullable = false)
    private Integer articul;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Column(name = "tradePrice", nullable = false)
    private Integer tradePrice;

    @Column(name = "retailPrice", nullable = false)
    private Integer retailPrice;

    @Column(name = "type", length = 128, nullable = false)
    private String type;

    @Column(name = "size", length = 3, nullable = false)
    private String size;

    @Column(name = "color", length = 128,nullable=false)
    private String color;

    @Column(name = "description", length = 128,nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "supplierId",nullable = false)
    private Supplier supplierId;

    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "checkId")
    private Check check;

    @ManyToOne(fetch = FetchType.EAGER,cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "office",nullable = false)
    private Office office;

    public void setCheck(Check check) {
        this.check = check;
    }

    public void setSupplierId(Supplier supplierId) {
        this.supplierId = supplierId;
    }

    public Long getId() {
        return id;
    }


    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public Integer getArticul() {
        return articul;
    }

    public String getName() {
        return name;
    }

    public Integer getTradePrice() {
        return tradePrice;
    }

    public Integer getRetailPrice() {
        return retailPrice;
    }

    public String getType() {
        return type;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public Supplier getSupplierId() {
        return supplierId;
    }

    public Integer getStatus() {
        return status;
    }

    public Check getCheck() {
        return check;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setArticul(Integer articul) {
        this.articul = articul;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTradePrice(Integer tradePrice) {
        this.tradePrice = tradePrice;
    }

    public void setRetailPrice(Integer retailPrice) {
        this.retailPrice = retailPrice;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "StoreProduct{" +
                "id=" + id +
                ", articul=" + articul +
                ", name='" + name + '\'' +
                ", tradePrice=" + tradePrice +
                ", retailPrice=" + retailPrice +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", description='" + description + '\'' +
                ", supplierId=" + supplierId +
                ", status=" + status +
                ", check=" + check +
                ", office=" + office +
                '}';
    }
}
