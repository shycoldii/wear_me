package client.utils;

import javafx.beans.property.*;

import java.time.LocalDate;

/**
 * Класс для структуризации чеков
 */
public class CheckStructure {
    private IntegerProperty articul;
    private SimpleStringProperty name;
    private SimpleStringProperty color;
    private IntegerProperty amount;
    private IntegerProperty price;
    private IntegerProperty total;
    public CheckStructure(){
        this(null,null,null,null,null,null);
    }

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

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    public String getColor() {
        return color.get();
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public SimpleStringProperty colorProperty() {
        return color;
    }

    public void setTotal(int total) {
        this.total.set(total);
    }

    public int getArticul() {
        return articul.get();
    }

    public IntegerProperty articulProperty() {
        return articul;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public int getAmount() {
        return amount.get();
    }

    public IntegerProperty amountProperty() {
        return amount;
    }

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public int getTotal() {
        return total.get();
    }

    public IntegerProperty totalProperty() {
        return total;
    }

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
