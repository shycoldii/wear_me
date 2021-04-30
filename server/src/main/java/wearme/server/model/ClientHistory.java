package wearme.server.model;

import javax.persistence.*;
import java.time.LocalDate;
/**
 * Сущность истории клиентов
 */
@Entity
@Table(name = "clientsHistory")
public class ClientHistory {
    /**
     * Инициализация класса
     */
    public ClientHistory(){};

    /**
     * Конструктор для истории регистрации клиентов
     * @param date - дата создания клиента
     */
    public ClientHistory(LocalDate date){
        this.date = date;
    };

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee")
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client")
    private Client client;

    /**
     * Устанавливает сотрудника
     * @param employee - сотрудник
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * Устанавливает клиента
     * @param client - клиент
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Получает дату
     * @return date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Получает идентификатор
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Получает сотрудника
     * @return employee
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
}
