package client.utils;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Класс для структуризации офисов
 */
public class OfficeStructure {
    /**
     * id
     */
    private final LongProperty id;
    /**
     * название
     */
    private final StringProperty name;
    /**
     * телефон
     */
    private final StringProperty phone;
    /**
     * адрес
     */
    private final StringProperty address;

    /**
     * Инициализирует данные офиса
     * @param id - идентификатор
     * @param name - название
     * @param phone - телефонный номер
     * @param address - адрес
     */
    public OfficeStructure(Long id, String name, String phone, String address){
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty(phone);
        this.address= new SimpleStringProperty(address);

    }

    /**
     * Получает значение идентификатора
     * @return id
     */
    public long getId() {
        return id.get();
    }

    /**
     * Получает значение idProperty
     * @return idProperty
     */
    public LongProperty idProperty() {
        return id;
    }

    /**
     * Устанавливает значение id
     * @param id - идентификатор
     */
    public void setId(long id) {
        this.id.set(id);
    }

    /**
     * Получает название
     * @return name
     */
    public String getName() {
        return name.get();
    }
    /**
     * Получает значение nameProperty
     * @return nameProperty
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Устанавливает название
     * @param name - название
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Получает значение телефона
     * @return phone
     */
    public String getPhone() {
        return phone.get();
    }
    /**
     * Получает значение phoneProperty
     * @return phoneProperty
     */
    public StringProperty phoneProperty() {
        return phone;
    }

    /**
     * Устанавливает значение телефону
     * @param phone - телефон
     */
    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    /**
     * Получает значение адреса
     * @return address
     */
    public String getAddress() {
        return address.get();
    }
    /**
     * Получает значение addressProperty
     * @return addressProperty
     */
    public StringProperty addressProperty() {
        return address;
    }

    /**
     * Устанавливает значение адресу
     * @param address - адрес
     */
    public void setAddress(String address) {
        this.address.set(address);
    }
}
