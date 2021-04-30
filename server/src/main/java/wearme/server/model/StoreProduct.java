package wearme.server.model;
import javax.persistence.*;
/**
 * Сущность товаров
 */
@Entity
@Table(name = "storeProducts")
public class StoreProduct {
    /**
     * Инициализация класса
     */
    public StoreProduct(){};

    /**
     * Конструктор для товаров
     * @param articul - артикул
     * @param name - название
     * @param tradePrice - цена покупки у поставщика
     * @param retailPrice - цена продажи в магазине
     * @param type - тип
     * @param size - размер
     * @param color - цвет
     * @param description - описание
     * @param status - статус(состояние) товара. Принимает значения
     *               1 - поставка, 2 - на складе, 3 - списан
     */
    public StoreProduct(Integer articul,String name,Integer tradePrice,Integer retailPrice,
                        String type,String size,String color,String description,Integer status){
        this.articul = articul;
        this.name = name;
        this.tradePrice = tradePrice;
        this.retailPrice= retailPrice;
        this.type=type;
        this.size = size;
        this.color = color;
        this.description = description;
        this.status = status;
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "articul", nullable = false)
    private Integer articul;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Column(name = "tradePrice", nullable = false)
    private Integer tradePrice;

    @Column(name = "retailPrice", nullable = false)
    private Integer retailPrice;

    @Column(name = "type", length = 128, nullable = false)
    private String type;

    @Column(name = "size", length = 3, nullable = false)
    private String size;

    @Column(name = "color", length = 128,nullable=false)
    private String color;

    @Column(name = "description", length = 128,nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplierId",nullable = false)
    private Supplier supplierId;

    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "checkId")
    private Check check;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "office",nullable = false)
    private Office office;

    /**
     * Устанавливает чек
     * @param check - чек
     */
    public void setCheck(Check check) {
        this.check = check;
    }

    /**
     * Устанавливает поставщика
     * @param supplierId - поставщик
     */
    public void setSupplierId(Supplier supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * Получает идентификатор
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Получает офис
     * @return office
     */
    public Office getOffice() {
        return office;
    }

    /**
     * Устанавливает офис
     * @param office - офис
     */
    public void setOffice(Office office) {
        this.office = office;
    }

    /**
     * Получает артикул
     * @return articul
     */
    public Integer getArticul() {
        return articul;
    }

    /**
     * Получает название
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Получает цену закупки
     * @return tradePrice
     */
    public Integer getTradePrice() {
        return tradePrice;
    }

    /**
     * Получает цену продажи
     * @return retailPrice
     */
    public Integer getRetailPrice() {
        return retailPrice;
    }

    /**
     * Получает тип
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Получает размер
     * @return size
     */
    public String getSize() {
        return size;
    }

    /**
     * Получает цвет
     * @return color
     */
    public String getColor() {
        return color;
    }

    /**
     * Получает описание
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Получает поставщика
     * @return supplier
     */
    public Supplier getSupplierId() {
        return supplierId;
    }

    /**
     * Получает статус
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Получает чек
     * @return check
     */
    public Check getCheck() {
        return check;
    }

    /**
     * Устанавливает идентификатор
     * @param id - идентификатор
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Устанавливает артикул
     * @param articul - артикул
     */
    public void setArticul(Integer articul) {
        this.articul = articul;
    }

    /**
     * Устанавливает имя
     * @param name - имя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Устанавливает цену закупки
     * @param tradePrice - цена закупки
     */
    public void setTradePrice(Integer tradePrice) {
        this.tradePrice = tradePrice;
    }

    /**
     * Устанавливает цену продажи
     * @param retailPrice - цена продажи
     */
    public void setRetailPrice(Integer retailPrice) {
        this.retailPrice = retailPrice;
    }

    /**
     * Устанавливает тип
     * @param type - тип
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Устанавливает размер
     * @param size - размер
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * Устанавливает цвет
     * @param color - цвет
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Устанавливает описание
     * @param description - описание
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Устанавливает статус
     * @param status - статус
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    /**
     * Преобразует объект в строку
     * @return String - преобразованный объект
     */
    @Override
    public String toString() {
        return "StoreProduct{" +
                "id=" + id +
                ", articul=" + articul +
                ", name='" + name + '\'' +
                ", tradePrice=" + tradePrice +
                ", retailPrice=" + retailPrice +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", description='" + description + '\'' +
                ", supplierId=" + supplierId +
                ", status=" + status +
                ", check=" + check +
                ", office=" + office +
                '}';
    }
}
