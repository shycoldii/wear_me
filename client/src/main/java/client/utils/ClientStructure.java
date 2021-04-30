package client.utils;

import javafx.beans.property.*;

/**
 * Класс для структуризации клиента
 */
public class ClientStructure {
    /**
     * id
     */
    private final LongProperty id;
    /**
     * день рождения
     */
    private final StringProperty birthday;
    /**
     * электронная почта
     */
    private final StringProperty email;
    /**
     * имя
     */
    private final StringProperty firstName;
    /**
     * фамилия
     */
    private final StringProperty secondName;
    /**
     * отчество
     */
    private final StringProperty patronymic;
    /**
     * телефонный номер
     */
    private final StringProperty phoneNumber;
    /**
     * дата регистрации
     */
    private final StringProperty regday;
    /**
     * количество бонусов
     */
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

    /**
     * Получает уникальный идентификатор
     * @return id
     */
    public long getId() {
        return id.get();
    }

    /**
     * Получает idProperty
     * @return id
     */
    public LongProperty idProperty() {
        return id;
    }

    /**
     * Устанавливает идентификатор
     * @param id - идентификатор
     */
    public void setId(long id) {
        this.id.set(id);
    }
    /**
     * Получает birthdayProperty
     * @return birthday
     */
    public StringProperty birthdayProperty() {
        return birthday;
    }

    /**
     * Получает электронную почту
     * @return email
     */
    public String getEmail() {
        return email.get();
    }
    /**
     * Получает emailProperty
     * @return email
     */
    public StringProperty emailProperty() {
        return email;
    }

    /**
     * Получает имя
     * @return firstName
     */
    public String getFirstName() {
        return firstName.get();
    }
    /**
     * Получает firstNameProperty
     * @return firstName
     */
    public StringProperty firstNameProperty() {
        return firstName;
    }

    /**
     * Получает фамилию
     * @return secondName
     */
    public String getSecondName() {
        return secondName.get();
    }
    /**
     * Получает secondNameProperty
     * @return secondName
     */
    public StringProperty secondNameProperty() {
        return secondName;
    }

    /**
     * Получает телефонный номер
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber.get();
    }
    /**
     * Получает phoneNumberProperty
     * @return phoneNumber
     */
    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    /**
     * Получает дату регистрации
     * @return regday
     */
    public String getRegday() {
        return regday.get();
    }

    /**
     * Устанавливает значение даты регистрации
     * @param regday - дата регистрации
     */
    public void setRegday(String regday) {
        this.regday.set(regday);
    }
    /**
     * Получает bonusesProperty
     * @return bonuses
     */
    public IntegerProperty bonusesProperty() {
        return bonuses;
    }

    /**
     * Устанавливает значение бонусам
     * @param bonuses - бонусы
     */
    public void setBonuses(int bonuses) {
        this.bonuses.set(bonuses);
    }
}
