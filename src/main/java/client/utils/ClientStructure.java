package client.utils;

import javafx.beans.property.*;

/**
 * Класс для структуризации клиента
 */
public class ClientStructure {
    private final LongProperty id;
    private final StringProperty birthday;
    private final StringProperty email;
    private final StringProperty firstName;
    private final StringProperty secondName;
    private final StringProperty patronymic;
    private final StringProperty phoneNumber;
    private final StringProperty regday;
    private final IntegerProperty bonuses;

    /**
     * Инициализирует данные клиента
     * @param id - идентификатор
     * @param birthday - день рождения
     * @param email - электронная почта
     * @param firstName - имя
     * @param bonuses - количество бонусов
     * @param patronymic - отчество
     * @param phoneNumber - телефонный номер
     * @param regday - дата регистрации
     * @param secondName - фамилия
     */
    public  ClientStructure(Long id, String birthday, String email, String firstName,
                            Integer bonuses, String patronymic, String phoneNumber, String regday,
                            String secondName){
        this.id = new SimpleLongProperty(id);
        this.birthday = new SimpleStringProperty(birthday);
        this.email = new SimpleStringProperty(email);
        this.firstName = new SimpleStringProperty(firstName);
        this.bonuses = new SimpleIntegerProperty(bonuses);
        this.patronymic = new SimpleStringProperty(patronymic);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.secondName = new SimpleStringProperty(secondName);
        this.regday = new SimpleStringProperty(regday);

    }

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getBirthday() {
        return birthday.get();
    }

    public StringProperty birthdayProperty() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday.set(birthday);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getSecondName() {
        return secondName.get();
    }

    public StringProperty secondNameProperty() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName.set(secondName);
    }

    public String getPatronymic() {
        return patronymic.get();
    }

    public StringProperty patronymicProperty() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic.set(patronymic);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getRegday() {
        return regday.get();
    }

    public StringProperty regdayProperty() {
        return regday;
    }

    public void setRegday(String regday) {
        this.regday.set(regday);
    }

    public int getBonuses() {
        return bonuses.get();
    }

    public IntegerProperty bonusesProperty() {
        return bonuses;
    }

    public void setBonuses(int bonuses) {
        this.bonuses.set(bonuses);
    }
}
