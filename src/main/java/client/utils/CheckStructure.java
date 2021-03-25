package client.utils;

import javafx.beans.property.*;

import java.time.LocalDate;

public class CheckStructure {
    private IntegerProperty articul;
    private SimpleStringProperty name;
    private IntegerProperty amount;
    private IntegerProperty price;
    private IntegerProperty total;
    public CheckStructure(){
        this(null,null,null,null,null);
    }

    public CheckStructure(Integer articul,String name,Integer amount,Integer price,Integer total){
        this.articul = new SimpleIntegerProperty(articul);
        this.name = new SimpleStringProperty(name);
        this.amount = new SimpleIntegerProperty(amount);
        this.price = new SimpleIntegerProperty(price);
        this.total = new SimpleIntegerProperty(total);
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    public void setTotal(int total) {
        this.total.set(total);
    }

    public long getArticul() {
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
}
