package client.utils;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Класс для структуризации поставщиков
 */
public class SupplierStructure {
    /**
     * StringProperty название
     */
    private final StringProperty name;
    /**
     * LongProperty идентификатор
     */
    private final LongProperty id;

    /**
     * Инициализирует данные поставщика
     * @param id - идентификатор
     * @param name - название
     */
    public SupplierStructure(Long id,String name){
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
    }

    /**
     * Получает название
     * @return name
     */
    public String getName() {
        return name.get();
    }

    /**
     * Получает nameProperty
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
     * Получает id
     * @return id
     */
    public long getId() {
        return id.get();
    }

    /**
     * Устанавливает id
     * @param id - идентификатор
     */
    public void setId(long id) {
        this.id.set(id);
    }
}
