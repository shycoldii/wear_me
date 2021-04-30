package client.utils;

import javafx.beans.property.*;

import java.time.LocalDate;

/**
 * Класс для структуризации чеков
 */
public class CheckStructure {
    /**
     * артикул
     */
    private final IntegerProperty articul;
    /**
     * название
     */
    private final SimpleStringProperty name;
    /**
     * цвет
     */
    private final SimpleStringProperty color;
    /**
     * количество
     */
    private final IntegerProperty amount;
    /**
     * цена
     */
    private final IntegerProperty price;
    /**
     * общая сумма
     */
    private final IntegerProperty total;

    /**
     * Инициализирует чек
     * @param articul - артикул
     * @param name - название
     * @param color - цвет
     * @param amount - количество
     * @param price - цена
     * @param total - сумма
     */
    public CheckStructure(Integer articul,String name,String color,Integer amount,Integer price,Integer total){
        this.articul = new SimpleIntegerProperty(articul);
        this.name = new SimpleStringProperty(name);
        this.color = new SimpleStringProperty(color);
        this.amount = new SimpleIntegerProperty(amount);
        this.price = new SimpleIntegerProperty(price);
        this.total = new SimpleIntegerProperty(total);
    }

    /**
     * Устанавливает количество
     * @param amount - количество
     */
    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    /**
     * Получает цвет
     * @return color - цвет
     */
    public String getColor() {
        return color.get();
    }

    /**
     * Устанавливает цвет
     * @param color - цвет
     */
    public void setColor(String color) {
        this.color.set(color);
    }

    /**
     * Получает colorProperty
     * @return colorProperty
     */
    public SimpleStringProperty colorProperty() {
        return color;
    }

    /**
     * Устанавливает общую цену
     * @param total - общая цена
     */
    public void setTotal(int total) {
        this.total.set(total);
    }

    /**
     * Получает артикул
     * @return articul
     */
    public int getArticul() {
        return articul.get();
    }
    /**
     * Получает articulProperty
     * @return articulProperty
     */
    public IntegerProperty articulProperty() {
        return articul;
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
    public SimpleStringProperty nameProperty() {
        return name;
    }

    /**
     * Получает количество
     * @return amount
     */
    public int getAmount() {
        return amount.get();
    }
    /**
     * Получает amountProperty
     * @return amountProperty
     */
    public IntegerProperty amountProperty() {
        return amount;
    }

    /**
     * Получает цену
     * @return price
     */
    public int getPrice() {
        return price.get();
    }
    /**
     * Получает priceProperty
     * @return priceProperty
     */
    public IntegerProperty priceProperty() {
        return price;
    }

    /**
     * Получает общую сумму
     * @return total
     */
    public int getTotal() {
        return total.get();
    }
    /**
     * Получает totalProperty
     * @return totalProperty
     */
    public IntegerProperty totalProperty() {
        return total;
    }

    /**
     * Обертывает  в строку
     * @return string - информация об объекте
     */
    @Override
    public String toString() {
        return "CheckStructure{" +
                "articul=" + articul +
                ", name=" + name +
                ", color=" + color +
                ", amount=" + amount +
                ", price=" + price +
                ", total=" + total +
                '}';
    }
}
