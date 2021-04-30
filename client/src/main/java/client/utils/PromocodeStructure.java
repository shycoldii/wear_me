package client.utils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Класс для структуризации промокодов
 */
public class PromocodeStructure {
    /**
     * StringProperty название
     */
    private final StringProperty name;
    /**
     * IntegerProperty скидка
     */
    private final IntegerProperty discount;
    /**
     * StringProperty начальная дата
     */
    private final StringProperty startDate;
    /**
     * StringProperty конечная дата
     */
    private final StringProperty endDate;

    /**
     * Инициализирует данные промокода
     * @param name - название
     * @param discount - скидка
     * @param startDate - начальная дата
     * @param endDate - конечная дата
     */
    public PromocodeStructure(String name, Integer discount, String startDate,
                              String endDate){
        this.name = new SimpleStringProperty(name);
        this.discount = new SimpleIntegerProperty(discount);
        this.startDate = new SimpleStringProperty(startDate);
        this.endDate = new SimpleStringProperty(endDate);
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
     * Получает discountProperty
     * @return discountProperty
     */
    public IntegerProperty discountProperty() {
        return discount;
    }
    /**
     * Получает дату начала
     * @return startDate
     */
    public String getStartDate() {
        return startDate.get();
    }
    /**
     * Получает startDateProperty
     * @return startDateProperty
     */
    public StringProperty startDateProperty() {
        return startDate;
    }
    /**
     * Получает дату окончания
     * @return endDate
     */
    public String getEndDate() {
        return endDate.get();
    }
    /**
     * Получает endDateProperty
     * @return endDateProperty
     */
    public StringProperty endDateProperty() {
        return endDate;
    }

    /**
     * Устанавливает скидку
     * @param discount - скидка
     */
    public void setDiscount(int discount) {
        this.discount.set(discount);
    }

    /**
     * Преобразует объект в строку
     * @return string - преобразованный объект
     */
    @Override
    public String toString() {
        return "PromocodeStructure{" +
                "name=" + name +
                ", discount=" + discount +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
