package client.utils;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Класс для структуризации поставщиков
 */
public class SupplierStructure {
    private final StringProperty name;
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public long getId() {
        return id.get();
    }


    public void setId(long id) {
        this.id.set(id);
    }
}
