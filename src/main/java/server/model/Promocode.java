package server.model;

import javax.persistence.*;
import java.time.LocalDate;
/**
 * Сущность истории промокодов
 */
@Entity
@Table(name = "promocodes",uniqueConstraints = {@UniqueConstraint(columnNames={"name"})})
public class Promocode {
    public Promocode(){};

    /**
     * Конструктор для сущности промокод
     * @param name - название промокода
     * @param discount - скидка, действующая при данном промокоде
     * @param startDate - дата начала действия промокода
     * @param endDate - дата окончания действия промокода
     */
    public Promocode(String name, Integer discount, LocalDate startDate, LocalDate endDate){
        this.name = name;
        this.discount = discount;
        this.startDate = startDate;
        this.endDate = endDate;
    };

    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "discount", nullable = false)
    private Integer discount;
    @Column(name = "startDate", nullable = false)
    private LocalDate startDate;
    @Column(name = "endDate", nullable = false)
    private LocalDate endDate;

    public void setName(String name) {
        this.name = name;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Promocode{" +
                "name='" + name + '\'' +
                ", discount=" + discount +
                '}';
    }

    public String getName() {
        return name;
    }

    public Integer getDiscount() {
        return discount;
    }
}
