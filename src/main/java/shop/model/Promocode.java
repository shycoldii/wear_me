package shop.model;

import javax.persistence.*;

@Entity
@Table(name = "promocodes",uniqueConstraints = {@UniqueConstraint(columnNames={"name"})})
public class Promocode {
    public Promocode(){};
    public Promocode(String name, Integer discount){
        this.name = name;
        this.discount = discount;
    };

    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "discount", nullable = false)
    private Integer discount;



}
