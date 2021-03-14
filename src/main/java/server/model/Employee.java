package server.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
public class Employee {
    public Employee(){};


    public Employee(String FirstName, String SecondName, String Login,String Patronymic, String Password,
                    String PhoneNumber, String Passport, String Email,LocalDateTime regday,LocalDate birthday){
        this.firstName = FirstName;
        this.secondName = SecondName;
        this.login = Login;
        this.password = Password;
        this.phoneNumber = PhoneNumber;
        this.passport = Passport;
        this.email = Email;
        this.regDay = regday;
        this.birthday = birthday;
        this.patronymic = Patronymic;

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

    @Column(name = "login", length = 128, nullable = false)
    private String login;

    @Column(name = "password", length = 128, nullable = false)
    private String password;

    @Column(name = "phoneNumber", length = 128, nullable = false)
    private String phoneNumber;

    @Column(name="birthday",length = 60)
    private LocalDate birthday;

    @Column(name = "passport", length = 128, nullable = false)
    private String passport;

    @Column(name = "email", length = 128, nullable = false)
    private String email;

    //photo

    @Column(name="regDay",length = 60)
    private LocalDateTime regDay;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "positionId")
    private Position positionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "officeId")
    private Office officeId;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", login='" + login + '\'' +
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

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getPassport() {
        return passport;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getRegDay() {
        return regDay;
    }

    public Position getPositionId() {
        return positionId;
    }

    public Office getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Office officeId) {
        this.officeId = officeId;
    }

    public void setPosition(Position position) {
        this.positionId = position;
    }
}
