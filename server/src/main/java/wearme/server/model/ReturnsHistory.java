package wearme.server.model;

import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.util.Date;
/**
 * Сущность истории возвратов
 */
@Entity
@Table(name = "returnsHistory")
public class ReturnsHistory {
    /**
     * Инициализация класса
     */
    public ReturnsHistory(){};

    /**
     * Конструктор для истории возвратов
     * @param total - сумма возврата чека
     */
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

    /**
     * Получает товар
     * @return storeProduct
     */
    public StoreProduct getStoreProduct() {
        return storeProduct;
    }

    /**
     * Устанавливает идентификатор
     * @param id - идентификатор
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Устанавливает товар
     * @param storeProduct - товар
     */
    public void setStoreProduct(StoreProduct storeProduct) {
        this.storeProduct = storeProduct;
    }

    /**
     * Устанавливает сотрудника
     * @param employee - сотрудник
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * Устанавливает чек
     * @param check - чек
     */
    public void setCheck(Check check) {
        this.check = check;
    }

    /**
     * Получает сотрудника
     * @return employee - сотрудник
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Получает чек
     * @return check
     */
    public Check getCheck() {
        return check;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "check_id",nullable = false)
    private Check check;

    /**
     * Получает дату создания
     * @return createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Устанавливает дату создания
     * @param createDate - дата создания
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Устанавливает общую сумму
     * @param total - общая сумма
     */
    public void setTotal(Integer total) {
        this.total = total;
    }


//    public void setCheck(Check check) {
//        this.check = check;
//    }

    /**
     * Получает идентификатор
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Получает общую сумму
     * @return total
     */
    public Integer getTotal() {
        return total;
    }

//
//    public Check getCheck() {
//        return check;
//    }
}
