package client.utils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class PromocodeStructure {
    private StringProperty name;
    private IntegerProperty discount;
    private StringProperty startDate;
    private StringProperty endDate;

    public PromocodeStructure(String name, Integer discount, String startDate,
                              String endDate){
        this.name = new SimpleStringProperty(name);
        this.discount = new SimpleIntegerProperty(discount);
        this.startDate = new SimpleStringProperty(startDate);
        this.endDate = new SimpleStringProperty(endDate);
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

    public int getDiscount() {
        return discount.get();
    }

    public IntegerProperty discountProperty() {
        return discount;
    }

    public String getStartDate() {
        return startDate.get();
    }

    public StringProperty startDateProperty() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate.set(startDate);
    }

    public String getEndDate() {
        return endDate.get();
    }

    public StringProperty endDateProperty() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate.set(endDate);
    }

    public void setDiscount(int discount) {
        this.discount.set(discount);
    }
}
