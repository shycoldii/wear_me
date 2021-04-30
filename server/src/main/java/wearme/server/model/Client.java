package wearme.server.model;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
/**
 * Сущность истории клиентов
 */
@Entity
@Table(name = "clients")
public class Client {
    /**
     * Инициализация класса
     */
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
     * Устанавливает фамилию
     * @param secondName - фамилия
     */
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    /**
     * Устанавливает количество бонусов
     * @param numberOfBonuses - количество бонусов
     */
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

    /**
     * Получает имя
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Получает фамилию
     * @return secondName
     */
    public String getSecondName() {
        return secondName;
    }

    /**
     * Получает отчество
     * @return patronymic
     */
    public String getPatronymic() {
        return patronymic;
    }

    /**
     * Получает телефонный номер
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Получает день рождения
     * @return birthday
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Получает электронную почту
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Получает дату регистрации
     * @return regDay
     */
    public LocalDate getRegDay() {
        return regDay;
    }

    /**
     * Получает количество бонусов
     * @return numberOfBonuses
     */
    public Integer getNumberOfBonuses() {
        return numberOfBonuses;
    }

    /**
     * Преобразует объект в строку
     * @return String - преобразованный объект
     */
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
