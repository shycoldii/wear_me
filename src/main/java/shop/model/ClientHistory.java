package shop.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "clientsHistory")
public class ClientHistory {
    public ClientHistory(){};
    public ClientHistory(LocalDate date){
        this.date = date;
    };

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client")
    private Client client;

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}