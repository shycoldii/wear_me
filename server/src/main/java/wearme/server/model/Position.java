package wearme.server.model;
import javax.persistence.*;
/**
 * Сущность позиций (рангов) работников
 */
@Entity
@Table(name = "positions",uniqueConstraints = {@UniqueConstraint(columnNames={"name"})})
public class Position {
    /**
     * Инициализация класса
     */
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

    /**
     * Получает идентификатор
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор
     * @param id - идентификатор
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Устанавливает имя
     * @param name - имя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Получает имя
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * Преобразует объект в строку
     * @return String - преобразованный объект
     */
    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


}
