package wearme.server.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
/**
 * Сущность чеков
 */
@Entity
@Table(name = "checks")
public class Check {
    /**
     * Инициализация класса
     */
    public Check(){};

    /**
     * Получает скидку
     * @return discount
     */
    public Integer getDiscount() {
        return discount;
    }

    /**
     * Устанавливает скидку
     * @param discount - скидка
     */
    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    /**
     * Конструктор для сущности чека
     * @param dateTime - дата создания
     * @param total - общая сумма чека
     * @param discount - скидка чека
     */
    public Check(LocalDateTime dateTime, Integer total, Integer discount){

        this.dateTime = dateTime;
        this.total = total;
        this.discount = discount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="dateTime", nullable = false)
    private LocalDateTime dateTime;

    @Column(name="total", nullable = false)
    private Integer total;

    @Column(name="discount", nullable = false)
    private Integer discount;

    /**
     * Получает общую сумму
     * @return total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * Устанавливает идентификатор
     * @param id - идентификатор
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Устанавливает время
     * @param dateTime - время
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Устанавливает общую сумму
     * @param total - общая сумма
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee",nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER)
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promocode")
    private Promocode promocode;

    /**
     * Получает идентификатор
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Получает время
     * @return dateTime
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Получает сотрудника
     * @return Employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Получает клиента
     * @return client
     */
    public Client getClient() {
        return client;
    }

    /**
     * Получает промокод
     * @return promocode
     */

    public Promocode getPromocode() {
        return promocode;
    }
}
