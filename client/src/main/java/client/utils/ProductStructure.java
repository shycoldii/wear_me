package client.utils;

import javafx.beans.property.*;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Класс для структуризации товаров
 */
public class ProductStructure {
    /**
     * идентификатор
     */
    private final LongProperty idLabel;
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
     * размер
     */
    private final SimpleStringProperty size;
    /**
     * тип
     */
    private final SimpleStringProperty type;
    /**
     * описание
     */
    private final SimpleStringProperty description;
    /**
     * цена
     */
    private final IntegerProperty price;
    /**
     * цена продажи
     */
    private final IntegerProperty retail_price;
    /**
     * статус на складе
     */
    private final IntegerProperty status;
    /**
     * офис
     */
    private final ObjectProperty office;
    /**
     * поставщик
     */
    private final ObjectProperty supplier;
    /**
     * чек
     */
    private final ObjectProperty check;

    /**
     * Инициализирует данные товара
     * @param id - идентификатор
     * @param articul - артикул
     * @param name - название
     * @param color - цвет
     * @param size - размер
     * @param price - цена закупки
     * @param description - описание
     * @param retail_price - цена в магазине
     * @param status - статус
     * @param type - тип
     * @param office - офис
     * @param supplier - поставщик
     * @param check - прикрепленный чек
     */
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

    /**
     * Получает идентификатор чека, если он существует
     * @return checkId
     */
    public SimpleStringProperty getCheckId() {
        try{
            JSONObject jsoncheck = new JSONObject((check.getValue().toString()));
            return new SimpleStringProperty(jsoncheck.getString("id"));
        }
        catch(JSONException | NullPointerException e){
            return new SimpleStringProperty("");
        }

    }

    /**
     * Получает скидку чека, если она существует
     * @return discount
     */
    public int getDiscountCheck(){
        try{
            JSONObject jsoncheck = new JSONObject((check.getValue().toString()));
            return jsoncheck.getInt("discount");
        } catch (JSONException e) {
            return 0;
        }

    }

    /**
     * Получает идентификатор поставщика, если он существует
     * @return supplierId
     */
    public SimpleLongProperty getSupplierId() {
        try{
            JSONObject jsonsupp = new JSONObject((supplier.get().toString()));
            return new SimpleLongProperty(jsonsupp.getLong("id"));
        }
        catch(JSONException e){
            return new SimpleLongProperty(-1L);
        }
    }

    /**
     * Получает идентификатор офиса, если он существует
     * @return officeId
     */
    public SimpleLongProperty getOfficeId() {
        try{
            JSONObject jsonoffice = new JSONObject((office.get().toString()));
            return new SimpleLongProperty(jsonoffice.getLong("id"));
        }
        catch(JSONException e){
            return new SimpleLongProperty(-1L);
        }
    }

    /**
     * Устанавливает значение артикулу
     * @param articul - артикул
     */
    public void setArticul(int articul) {
        this.articul.set(articul);
    }

    /**
     * Устанавливает название
     * @param name - название
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Устанавливает цвет
     * @param color - цвет
     */
    public void setColor(String color) {
        this.color.set(color);
    }

    /**
     * Устанавливает размер
     * @param size - размер
     */
    public void setSize(String size) {
        this.size.set(size);
    }

    /**
     * Устанавливает цену
     * @param price - цена
     */
    public void setPrice(int price) {
        this.price.set(price);
    }

    /**
     * Получает идентификатор
     * @return id
     */
    public long getIdLabel() {
        return idLabel.get();
    }

    /**
     * Получает idLabelProperty
     * @return idLabelProperty
     */
    public LongProperty idLabelProperty() {
        return idLabel;
    }

    /**
     * Получает тип
     * @return type
     */
    public String getType() {
        return type.get();
    }

    /**
     * Устанавливает значение типу
     * @param type - тип
     */
    public void setType(String type) {
        this.type.set(type);
    }

    /**
     * Устанавливает значение статусу
     * @param status - статус
     */
    public void setStatus(int status) {
        this.status.set(status);
    }

    /**
     * Получает typeProperty
     * @return typeProperty
     */
    public SimpleStringProperty typeProperty() {
        return type;
    }
    /**
     * Получает чек
     * @return check
     */
    public Object getCheck() {
        return check.get();
    }
    /**
     * Получает descriptionProperty
     * @return descriptionProperty
     */
    public SimpleStringProperty descriptionProperty() {
        return description;
    }
    /**
     * Получает розничную цену
     * @return retailPrice
     */
    public int getRetail_price() {
        return retail_price.get();
    }
    /**
     * Получает retailPriceProperty
     * @return retailPriceProperty
     */
    public IntegerProperty retail_priceProperty() {
        return retail_price;
    }
    /**
     * Получает статус
     * @return status
     */
    public int getStatus() {
        return status.get();
    }
    /**
     * Получает statusProperty
     * @return statusProperty
     */
    public IntegerProperty statusProperty() {
        return status;
    }

    /**
     * Преобразует объект в строку
     * @return string - преобразованный объект
     */
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
     * Получает цвет
     * @return color
     */
    public String getColor() {
        return color.get();
    }
    /**
     * Получает colorProperty
     * @return colorProperty
     */
    public SimpleStringProperty colorProperty() {
        return color;
    }
    /**
     * Получает размер
     * @return size
     */
    public String getSize() {
        return size.get();
    }
    /**
     * Получает sizeProperty
     * @return sizeProperty
     */
    public SimpleStringProperty sizeProperty() {
        return size;
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
}
