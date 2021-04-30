package wearme.server.model;

import javax.persistence.*;
import java.time.LocalDate;
/**
 * Сущность истории промокодов
 */
@Entity
@Table(name = "promocodes",uniqueConstraints = {@UniqueConstraint(columnNames={"name"})})
public class Promocode {
    /**
     * Инициализация класса
     */
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

    /**
     * Устанавливает название
     * @param name - название
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Устанавливает скидку
     * @param discount - скидка
     */
    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    /**
     * Получает начальную дату
     * @return startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Устанавливает начальную дату
     * @param startDate - начальная дата
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Получает конечную дату
     * @return endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Устанавливает конечную дату
     * @param endDate - конечная дата
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Преобразует объект в строку
     * @return String - преобразованный объект
     */
    @Override
    public String toString() {
        return "Promocode{" +
                "name='" + name + '\'' +
                ", discount=" + discount +
                '}';
    }

    /**
     * Получает название
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Получает скидку
     * @return discount
     */
    public Integer getDiscount() {
        return discount;
    }
}
