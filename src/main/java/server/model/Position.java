package server.model;
import javax.persistence.*;
/**
 * Сущность позиций (рангов) работников
 */
@Entity
@Table(name = "positions",uniqueConstraints = {@UniqueConstraint(columnNames={"name"})})
public class Position {
    public Position() {}

    /**
     * Конструктор для позиций работников
     * @param name - название позиции
     */
    public Position(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


}
