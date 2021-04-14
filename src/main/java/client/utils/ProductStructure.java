package client.utils;

import javafx.beans.property.*;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductStructure {
    private LongProperty idLabel;
    private IntegerProperty articul;
    private SimpleStringProperty name;
    private SimpleStringProperty color;
    private SimpleStringProperty size;
    private SimpleStringProperty type;
    private SimpleStringProperty description;
    private IntegerProperty price;
    private IntegerProperty retail_price;
    private IntegerProperty status;
    private ObjectProperty office;
    private ObjectProperty supplier;
    private ObjectProperty check;

    public ProductStructure(){
        this(null,null,null,null,null,null,
                null,null,null,null,null,null,null);
    }
    public ProductStructure(Long id,Integer articul,String name,String color,String size,Integer price,
                            String description,Integer retail_price,Integer status,
                            String type,Object office,Object supplier,Object check){
        this.idLabel = new SimpleLongProperty(id);
        this.articul = new SimpleIntegerProperty(articul);
        this.name = new SimpleStringProperty(name);
        this.color = new SimpleStringProperty(color);
        this.size = new SimpleStringProperty(size);
        this.price = new SimpleIntegerProperty(price);
        this.supplier = new SimpleObjectProperty(supplier);
        this.office = new SimpleObjectProperty(office);
        this.status = new SimpleIntegerProperty(status);
        this.retail_price = new SimpleIntegerProperty(retail_price);
        this.type = new SimpleStringProperty(type);
        this.description = new SimpleStringProperty(description);
        this.check = new SimpleObjectProperty(check);
    }

    public void setIdLabel(long idLabel) {
        this.idLabel.set(idLabel);
    }

    public SimpleStringProperty getCheckId() {
        try{
            JSONObject jsoncheck = new JSONObject((check.getValue().toString()));
            return new SimpleStringProperty(jsoncheck.getString("id"));
        }
        catch(JSONException | NullPointerException e){
            return new SimpleStringProperty("");
        }


    }
    public int getDiscountCheck(){
        try{
            JSONObject jsoncheck = new JSONObject((check.getValue().toString()));
            return jsoncheck.getInt("discount");
        } catch (JSONException e) {
            return 0;
        }

    }
    public SimpleLongProperty getSupplierId() {
        try{
            JSONObject jsonsupp = new JSONObject((supplier.get().toString()));
            return new SimpleLongProperty(jsonsupp.getLong("id"));
        }
        catch(JSONException e){
            return new SimpleLongProperty(-1L);
        }
    }
    public SimpleLongProperty getOfficeId() {
        try{
            JSONObject jsonoffice = new JSONObject((office.get().toString()));
            return new SimpleLongProperty(jsonoffice.getLong("id"));
        }
        catch(JSONException e){
            return new SimpleLongProperty(-1L);
        }
    }

    public void setArticul(int articul) {
        this.articul.set(articul);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public void setSize(String size) {
        this.size.set(size);
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public long getIdLabel() {
        return idLabel.get();
    }

    public LongProperty idLabelProperty() {
        return idLabel;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public void setRetail_price(int retail_price) {
        this.retail_price.set(retail_price);
    }

    public void setStatus(int status) {
        this.status.set(status);
    }


    public SimpleStringProperty typeProperty() {
        return type;
    }

    public String getDescription() {
        return description.get();
    }

    public Object getCheck() {
        return check.get();
    }

    public ObjectProperty checkProperty() {
        return check;
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public int getRetail_price() {
        return retail_price.get();
    }

    public IntegerProperty retail_priceProperty() {
        return retail_price;
    }

    public int getStatus() {
        return status.get();
    }

    public IntegerProperty statusProperty() {
        return status;
    }

    public Object getOffice() {
        return office.get();
    }

    public ObjectProperty officeProperty() {
        return office;
    }

    public Object getSupplier() {
        return supplier.get();
    }

    @Override
    public String toString() {
        return "ProductStructure{" +
                "id=" + idLabel +
                ", articul=" + articul +
                ", name=" + name +
                ", color=" + color +
                ", size=" + size +
                ", type=" + type +
                ", description=" + description +
                ", price=" + price +
                ", retail_price=" + retail_price +
                ", status=" + status +
                ", office=" + office +
                ", supplier=" + supplier +
                ", check=" + check +
                '}';
    }

    public ObjectProperty supplierProperty() {
        return supplier;
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

    public String getColor() {
        return color.get();
    }

    public SimpleStringProperty colorProperty() {
        return color;
    }

    public String getSize() {
        return size.get();
    }

    public SimpleStringProperty sizeProperty() {
        return size;
    }

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty priceProperty() {
        return price;
    }
}
