package shop.model;
import javax.persistence.*;

@Entity
@Table(name = "Positions",uniqueConstraints = {@UniqueConstraint(columnNames={"name"})})
public class Position {
    public Position() {}

    public Position(String lastName) {
        this.name = lastName;
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
        name = name;
    }

    public String getName() {
        return name;
    }

}
