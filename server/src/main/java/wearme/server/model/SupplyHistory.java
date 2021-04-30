package wearme.server.model;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Сущность истории поставок
 */
@Entity
@Table(name = "supplyHistory")
public class SupplyHistory {
    /**
     * Инициализация класса
     */
    public SupplyHistory(){};

    /**
     * Конструктор для истории поставок
     * @param date - дата поставки
     */
    public SupplyHistory(LocalDate date){
        this.date = date;
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee")
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "storeProduct")
    private StoreProduct storeProduct;

    /**
     * Получает идентификатор
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Получает дату
     * @return date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Получает сотрудника
     * @return employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Получает товар
     * @return storeProduct
     */
    public StoreProduct getStoreProduct() {
        return storeProduct;
    }

    /**
     * Устанавливает сотрудника
     * @param employee - сотрудник
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * Устанавливает товар
     * @param storeProduct - товар
     */
    public void setStoreProduct(StoreProduct storeProduct) {
        this.storeProduct = storeProduct;
    }
}
