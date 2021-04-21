package server.model;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
/**
 * Сущность истории клиентов
 */
@Entity
@Table(name = "clients")
public class Client {
    public Client(){};

    /**
     * Конструктор для сущности клиента
     * @param firstName - имя
     * @param secondName - фамилия
     * @param patronymic - отчество
     * @param phoneNumber - телефонный номер
     * @param email - почта
     * @param regday - дата регистрации
     * @param birthday - дата дня рождения
     * @param numberOfBonuses - количество бонусов
     */
    public Client(String firstName, String secondName,String patronymic,
                    String phoneNumber, String email,LocalDate regday,LocalDate birthday,
                  Integer numberOfBonuses){
        this.firstName = firstName;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.regDay = regday;
        this.birthday = birthday;
        this.patronymic = patronymic;
        this.numberOfBonuses = numberOfBonuses;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegDay(LocalDate regDay) {
        this.regDay = regDay;
    }

    public void setNumberOfBonuses(Integer numberOfBonuses) {
        this.numberOfBonuses = numberOfBonuses;
    }

    @Column(name = "firstName", length = 128, nullable = false)
    private String firstName;
    @Column(name = "secondName", length = 128, nullable = false)
    private String secondName;

    @Column(name = "patronymic", length = 128)
    private String patronymic;

    @Column(name = "phoneNumber", length = 128, nullable = false,unique = true)
    private String phoneNumber;

    @Column(name="birthday",nullable = false)
    private LocalDate birthday;

    @Column(name = "email", length = 128, nullable = false,unique = true)
    private String email;

    @Column(name="regDay",nullable = false)
    private LocalDate regDay;

    @Column(name="numberOfBonuses",nullable = false)
    private Integer numberOfBonuses;

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getRegDay() {
        return regDay;
    }

    public Integer getNumberOfBonuses() {
        return numberOfBonuses;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                ", regDay=" + regDay +
                ", numberOfBonuses=" + numberOfBonuses +
                '}';
    }
}
