package wearme.server.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 * Сущность работников
 */
@Entity
@Table(name = "employees",uniqueConstraints = {@UniqueConstraint(columnNames={"email","phoneNumber","passport"})})
public class Employee {
    /**
     * Инициализация класса
     */
    public Employee(){};

    /**
     * Конструктор для работников
     * @param firstName - имя
     * @param secondName - фамилия
     * @param patronymic - отчество
     * @param email - электронная почта
     * @param password - пароль (хешкод)
     * @param phoneNumber - телефонный номер
     * @param passport - паспорт
     * @param regday - дата регистрации
     * @param birthday - дата дня рождения
     */
    public Employee(String firstName, String secondName, String patronymic,String email, String password,
                    String phoneNumber, String passport, LocalDateTime regday,LocalDate birthday){
        this.firstName = firstName;
        this.secondName = secondName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.passport = passport;
        this.email = email;
        this.regDay = regday;
        this.birthday = birthday;
        this.patronymic = patronymic;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "firstName", length = 128, nullable = false)
    private String firstName;

    @Column(name = "secondName", length = 128, nullable = false)
    private String secondName;

    @Column(name = "patronymic", length = 128)
    private String patronymic;

    @Column(name = "password", length = 128, nullable = false)
    private String password;

    @Column(name = "phoneNumber", length = 128, nullable = false)
    private String phoneNumber;

    @Column(name="birthday",length = 60,nullable = false)
    private LocalDate birthday;

    @Column(name = "passport", length = 128, nullable = false)
    private String passport;

    @Column(name = "email", length = 128, nullable = false)
    private String email;

    @Column(name="regDay",length = 60,nullable = false)
    private LocalDateTime regDay;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "positionId",nullable = false)
    private Position positionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "officeId",nullable = false)
    private Office officeId;

    /**
     * Преобразует объект в строку
     * @return String - преобразованный объект
     */
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthday=" + birthday +
                ", passport='" + passport + '\'' +
                ", email='" + email + '\'' +
                ", regDay=" + regDay +
                ", positionId=" + positionId +
                ", officeId=" + officeId +
                '}';
    }

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
     * @param firstName - имя
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Устанавливает фамилию
     * @param secondName - фамилия
     */
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    /**
     * Устанавливает отчество
     * @param patronymic - отчество
     */
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    /**
     * Устанавливает пароль
     * @param password - пароль
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Устанавливает номер телефона
     * @param phoneNumber - номер телефона
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Устанавливает день рождения
     * @param birthday - день рождения
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    /**
     * Устанавливает паспорт
     * @param passport - паспорт
     */
    public void setPassport(String passport) {
        this.passport = passport;
    }

    /**
     * Устанавливает электронную почту
     * @param email - электронная почта
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Устанавливает дату регистрации
     * @param regDay - дата регистрации
     */
    public void setRegDay(LocalDateTime regDay) {
        this.regDay = regDay;
    }

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
     * Получает пароль
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Получает номер телефона
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
     * Получает паспорт
     * @return passport
     */
    public String getPassport() {
        return passport;
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
    public LocalDateTime getRegDay() {
        return regDay;
    }

    /**
     * Получает должность
     * @return position
     */
    public Position getPositionId() {
        return positionId;
    }

    /**
     * Получает офис
     * @return office
     */
    public Office getOfficeId() {
        return officeId;
    }

    /**
     * Устанавливает офис
     * @param officeId - офис
     */
    public void setOfficeId(Office officeId) {
        this.officeId = officeId;
    }

    /**
     * Устанавливает должность
     * @param position - должность
     */
    public void setPositionId(Position position) {
        this.positionId = position;
    }
}
