package server.model;

import javax.persistence.*;


@Entity
@Table(name = "products")
public class Product {
    public Product(){};
    public Product(Integer articul,String name, Integer tradePrice, Integer retailPrice, String type, String size,
                    String color, String description){
        this.name = name;
        this.articul = articul;
        this.tradePrice = tradePrice;
        this.retailPrice = retailPrice;
        this.type = type;
        this.size = size;
        this.color = color;
        this.description = description;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "articul", length = 128, nullable = false)
    private Integer articul;

    @Column(name = "name", length = 128, nullable = false)
    private String name;


    @Column(name = "tradePrice", nullable = false)
    private Integer tradePrice;

    @Column(name = "retailPrice", nullable = false)
    private Integer retailPrice;

    @Column(name = "type", length = 128, nullable = false)
    private String type;

    @Column(name = "size", length = 5, nullable = false)
    private String size;

    @Column(name = "color", length = 128)
    private String color;

    @Column(name = "description", length = 128)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier",nullable = false)
    private Supplier supplier;

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", articul=" + articul +
                ", name='" + name + '\'' +
                ", tradePrice=" + tradePrice +
                ", retailPrice=" + retailPrice +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", description='" + description + '\'' +
                ", supplier=" + supplier +
                '}';
    }

    public Long getId() {
        return id;
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

    public Supplier getSupplier() {
        return supplier;
    }
}
