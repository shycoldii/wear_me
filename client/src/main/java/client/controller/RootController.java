package client.controller;
import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.NoAppDataException;
import client.exception.SellProductException;
import client.utils.AlertInfo;
import client.utils.CheckStructure;
import client.utils.MyLogger;

import client.utils.ProductStructure;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.JSONException;

import java.io.IOException;
import java.util.Optional;

/**
 * Контроллер для главного окна приложения
 */
public class RootController {
    @FXML private Label address;
    @FXML private TableColumn<CheckStructure, Integer> articulColumn;
    @FXML private TableColumn<CheckStructure,String> nameColumn;
    @FXML private TableColumn<CheckStructure,Integer> priceColumn;
    @FXML private TableColumn<CheckStructure,Integer> amountColumn;
    @FXML private TableColumn<CheckStructure,Integer> totalColumn;
    @FXML private TableColumn<CheckStructure,String> colorColumn;
    @FXML private Label employeeLabel;
    @FXML private Label withDiscount;
    @FXML private Label withoutDiscount;
    @FXML private Label discount;
    @FXML private Label bonuses;
    @FXML private TableView<CheckStructure> checkTable;

    private JavaFXApplication mainApp;
    private MyAPI API;
    /**
     * Устанавливает значение главному приложению
     * @param mainApp - главное приложение
     */
    public void setMainApp(JavaFXApplication mainApp) {
        this.mainApp = mainApp;
    }
    /**
     * Устанавливает значение API
     * @param API - апи
     */
    public void setAPI(MyAPI API) {
        this.API = API;
    }

    /**
     * Инициализирует первичные значения
     */
    @FXML
    public void initialize() {
        articulColumn.setCellValueFactory(cellData -> cellData.getValue().articulProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colorColumn.setCellValueFactory(cellData -> cellData.getValue().colorProperty());
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
    }

    /**
     * Реализует выход из учетной записи при нажатии кнопки
     */
    @FXML
    public void logout(){
        this.mainApp.getRootController().createCheck();
        this.mainApp.getPrimaryStage().close();
        this.mainApp.initSignIn();
        MyLogger.logger.info("Выход из учетной записи");
    }

    /**
     * Получает адрес
     * @return address
     */
    public Label getAddress() {
        return address;
    }

    /**
     * Устанавливает данные клиентской среды сотрудника
     */
    public void setView()  {
        try{
            API.getData();
            this.address.setText(API.getAddress());
            this.employeeLabel.setText(API.getEmployeeInfo());
        }
        catch (NoAppDataException e){
            Platform.runLater(mainApp::noDataError);
        }
       catch(Exception e){
            e.printStackTrace();
           MyLogger.logger.error("Ошибка при инициализации главного слоя");
       }
    }

    /**
     * Создает добавление товара при нажатии кнопки
     */
    @FXML
    public void addCheck(){
        this.mainApp.initAddingToCheck();
    }

    /**
     * Обновляет состояние таблицы
     * @throws JSONException - при работе с JSON-объектом
     */
    public void updateCheck() throws JSONException {

        this.checkTable.setItems(this.API.getCheckData());
        this.withoutDiscount.setText(String.valueOf(this.API.getTotalPrice()));
        int discount = this.API.getPriceWithDiscount();
        this.discount.setText(Integer.toString(discount));
        this.withDiscount.setText(String.valueOf(this.API.getTotalPrice()-Integer.parseInt(this.discount.getText())));
        int summ = 0;
        for(CheckStructure s:this.checkTable.getItems()){
            summ+=s.getPrice()/100;
        }
        this.API.setCurrentBonuses(summ);
        this.bonuses.setText(String.valueOf(this.API.getCurrentBonuses()));
        MyLogger.logger.info("Обновлен чек");
        }

    /**
     * Удаляет товар из таблицы
     * @throws JSONException
     */
    @FXML
    private void deleteProduct() throws JSONException {

        int selectedIndex = this.checkTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            int articul = this.checkTable.getItems().get(selectedIndex).getArticul();
            String color = this.checkTable.getItems().get(selectedIndex).getColor();
            this.API.deleteProduct(articul,color);
            this.updateCheck();
            MyLogger.logger.info("Удален продукт из таблицы");
        } else {
            Alert alert = AlertInfo.getWarningAlert(mainApp);
            alert.initOwner(this.mainApp.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No product selected");
            alert.setContentText("Please, select the line in the table");
            alert.show();
            MyLogger.logger.error("Ошибка при удалении продукта. Не выделено!");
        }
    }

    /**
     * Создает новый чек
     */
    @FXML
    public void createCheck(){
        this.API.deleteAllProducts();
        this.checkTable.setItems(this.API.getCheckData());
        this.withoutDiscount.setText("0");
        this.withDiscount.setText("0");
        this.discount.setText("0");
        this.bonuses.setText("0");
        MyLogger.logger.info("Создан новый чек");
    }

    /**
     * Добавляет промокод
     */
    @FXML
    private void addPromocode(){
        if(this.API.getLoyaltyDiscount() ==0){
            MyLogger.logger.info("Добавление промокода");
            this.mainApp.initAddingPromocode();
        }
        else{
            Alert alert = AlertInfo.getWarningAlert(mainApp);
            alert.initOwner(this.mainApp.getPrimaryStage());
            alert.setTitle("Unable to add promocode");
            alert.setHeaderText("You have already entered a loyalty card");
            alert.show();
            MyLogger.logger.error("Попытка ввести промокод при карте лояльности");
        }
    }

    /**
     * Добавляет карту лояльности
     */
    @FXML
    private void addLoyaltyCard(){
        if (this.API.getJsonPromocode().length() == 0){
            MyLogger.logger.info("Добавление карты лояльности");
            this.mainApp.initAddingLoyaltyCard();
        }
        else{
            Alert alert = AlertInfo.getWarningAlert(mainApp);
            alert.initOwner(this.mainApp.getPrimaryStage());
            alert.setTitle("Unable to add loyalty card");
            alert.setHeaderText("You have already entered a promocode");
            alert.show();
            MyLogger.logger.error("Попытка ввести карту клиента при промокоде");
        }



    }

    /**
     * Удаляет промокод
     * @throws JSONException - при работе с JSON-объектом
     */
    @FXML public void removePromocode() throws JSONException {
        if(this.API.getJsonPromocode().length() == 0){
            Alert alert = AlertInfo.getWarningAlert(mainApp);
            alert.setHeaderText("No promocode");
            MyLogger.logger.error("Попытка удалить несуществующий промокод");
            alert.show();
        }
        else{
            this.API.removePromocode();
            this.updateCheck();
            Alert alert =AlertInfo.getOkAlert(mainApp);
            alert.setHeaderText("Successfully");
            alert.setContentText("Promocode has been removed");
            alert.show();
        }
        MyLogger.logger.info("Удаление промокода");


    }

    /**
     * Удаляет карту лояльности
     * @throws JSONException - при работе с JSON-объектом
     */
    @FXML public void removeLoaltyCard() throws JSONException {
        if(this.API.getClientId() == null){
            Alert alert = AlertInfo.getWarningAlert(mainApp);
            alert.setHeaderText("No loyalty card");
            alert.show();
            MyLogger.logger.error("Удаление карты");
        }
        else{
            this.API.removeLoyaltyCard();
            this.updateCheck();
            Alert alert = AlertInfo.getOkAlert(mainApp);
            alert.setHeaderText("Successfully");
            alert.setContentText("Loyalty card has been removed");
            alert.show();
            MyLogger.logger.info("Удаление карты клиента");
        }

    }

    /**
     * Получает информацию о карте лояльности
     */
    @FXML public void getCardInfo(){
        if(this.API.getClientId() == null){
            Alert alert = AlertInfo.getWarningAlert(mainApp);
            alert.setHeaderText("No loyalty card");
            alert.setContentText("First, enter loyalty card");
            alert.show();
            MyLogger.logger.error("Попытка узнать информацию о клиенте");
        }
        else{
            Alert alert = AlertInfo.getOkAlert(mainApp);
            alert.setHeaderText("Loyalty Card info");
            alert.setContentText("Bonuses = "+this.API.getBonuses()+" | Discount = "+this.API.getLoyaltyDiscount()+"%");
            alert.show();
            MyLogger.logger.info("Информация о клиенте");
        }
    }

    /**
     * Отправляет товары на продажу
     */
    @FXML public void sell(){
        MyLogger.logger.info("Запущено окно расчета");
        if(this.API.getEmployeeId() ==null || this.API.getCheckData().isEmpty() ||
        this.API.getListOfProducts().isEmpty()){
            Alert alert = AlertInfo.getWarningAlert(mainApp);
            alert.setHeaderText("Unable to complete the operation");
            alert.setContentText("Make sure the products are listed in the table");
            alert.show();
            MyLogger.logger.error("Попытка продать при пустом листе");
        }
        else{
            Alert alert = AlertInfo.getConfirmationAlert(mainApp);
            alert.setHeaderText("Selling the product");
            alert.setContentText("Are you ready to complete the operation?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK){
                try{
                    this.API.sellProducts();
                    this.mainApp.initCheck();
                    MyLogger.logger.info("Продажа товара");
                }
                catch (JSONException | IOException | SellProductException e){
                    alert = AlertInfo.getWarningAlert(mainApp);
                    alert.setHeaderText("An error occurred on the server");
                    MyLogger.logger.error("Ошибка при отправке данных о продаже товара");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
        }

    }

    /**
     * Реализует возврат товара
     */
    @FXML void returnProduct(){
        MyLogger.logger.info("Запущено окно возврата");
        this.mainApp.initReturn();
    }

    /**
     * Реализует отображение клиентов
     */
    @FXML void getClients(){
        MyLogger.logger.info("Запущено окно просмотра клиентов");
        this.mainApp.initClients();
    }
    /**
     * Реализует отображение промокодов
     */
    @FXML void getPromocodes(){
        if(this.API.getPosition().toLowerCase().equals("менеджер") | this.API.getPosition().toLowerCase().equals("программист")){
            MyLogger.logger.info("Запущено окно просмотра промокодов");
            this.mainApp.initPromocodes();
        }
        else{
            Alert alert = AlertInfo.getWarningAlert(mainApp);
            alert.setHeaderText("You haven't right to view this page");
            alert.setContentText("It's possible only for managers!");
            MyLogger.logger.error("Попытка просмотреть страницу промокодов");
            alert.show();
        }


    }
    /**
     * Реализует отображение офисов
     */
    @FXML void getOffices(){
        if(this.API.getPosition().toLowerCase().equals("менеджер") | this.API.getPosition().toLowerCase().equals("программист")){
            MyLogger.logger.info("Запущено окно просмотра офисов");
            this.mainApp.initOffices();
        }
        else{
            Alert alert = AlertInfo.getWarningAlert(mainApp);
            alert.setHeaderText("You haven't right to view this page");
            alert.setContentText("It's possible only for managers!");
            MyLogger.logger.error("Попытка просмотреть страницу офисов");
            alert.show();
        }
    }
    /**
     * Реализует отображение склада
     */
    @FXML void getStock(){
            MyLogger.logger.info("Запущено окно просмотра склада");
            this.mainApp.initStock();
    }
    /**
     * Реализует отображение информации сотрудникам
     */
    @FXML void getInfo(){
        MyLogger.logger.info("Запущено окно просмотра информации");
        this.mainApp.initInfo();
    }
    /**
     * Реализует отображение статистики
     */
    @FXML void getStat(){
        MyLogger.logger.info("Запущено окно просмотра статистики");
        this.mainApp.initStat();
    }
    /**
     * Реализует отображение информации об авторе
     */
    @FXML void getAboutMe(){
        MyLogger.logger.info("Запущено окно просмотра страницы об авторе");
        this.mainApp.initAboutMe();
    }


}
