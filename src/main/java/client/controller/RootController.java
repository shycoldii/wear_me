package client.controller;
import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.NoAppDataException;
import client.utils.AlertInfo;
import client.utils.CheckStructure;
import client.utils.MyLogger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.JSONException;

import java.util.Optional;

public class RootController {
    @FXML
    private Label address;
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

    public void setMainApp(JavaFXApplication mainApp) {
        this.mainApp = mainApp;
    }

    public void setAPI(MyAPI API) {
        this.API = API;
    }

    @FXML
    public void initialize() {
        articulColumn.setCellValueFactory(cellData -> cellData.getValue().articulProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colorColumn.setCellValueFactory(cellData -> cellData.getValue().colorProperty());
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
    }
    @FXML
    public void logout(){
        this.mainApp.getPrimaryStage().close();
        this.mainApp.initSignIn();
        MyLogger.logger.info("Выход из учетной записи");
    }

    public Label getAddress() {
        return address;
    }

    public void setView() throws JSONException {
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
    @FXML
    public void addCheck(){
        this.mainApp.initAddingToCheck();
    }
    public void updateCheck(){

        this.checkTable.setItems(this.API.getCheckData());
        this.withoutDiscount.setText(String.valueOf(this.API.getTotalPrice()));
        int discount = this.API.getPriceWithDiscount();
        this.discount.setText(Integer.toString(discount));
        this.withDiscount.setText(String.valueOf(this.API.getTotalPrice()-Integer.parseInt(this.discount.getText())));
        this.API.setCurrentBonuses(Integer.parseInt(this.withoutDiscount.getText())/100);
        this.bonuses.setText(String.valueOf(this.API.getCurrentBonuses()));
        MyLogger.logger.info("Обновлен чек");
        }

    @FXML
    private void deleteProduct() {

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

    @FXML
    private void createCheck(){
        this.API.deleteAllProducts();
        this.checkTable.setItems(this.API.getCheckData());
        this.withoutDiscount.setText("0");
        this.withDiscount.setText("0");
        this.discount.setText("0");
        this.bonuses.setText("0");
        MyLogger.logger.info("Создан новый чек");
    }
    @FXML
    private void addPromocode(){
        if(this.API.getLoyaltyDiscount() ==0){
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
    @FXML
    private void addLoyaltyCard(){
        if (this.API.getPromocodeDiscount() == 0){
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
    @FXML public void removePromocode(){
        if(this.API.getPromocode().isEmpty()){
            Alert alert = AlertInfo.getWarningAlert(mainApp);
            alert.setHeaderText("No promocode");
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
    @FXML public void removeLoaltyCard(){
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
    @FXML public void sell(){
        MyLogger.logger.info("Запущено окно расчета");
        if(this.API.getEmployeeId() ==null || this.API.getCheckData().isEmpty() ||
        this.API.getListOfProducts().isEmpty()){
            Alert alert = AlertInfo.getWarningAlert(mainApp);
            alert.setHeaderText("Unable to complete the operation");
            alert.setContentText("Make sure the products are listed in the table");
            alert.show();
        }
        else{
            Alert alert = AlertInfo.getConfirmationAlert(mainApp);
            alert.setHeaderText("Selling the product");
            alert.setContentText("Are you ready to complete the operation?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK){
                boolean response = this.API.sellProducts();
                if(response){
                    System.out.println("успешно");
                    //если уверены, то отправляем данные
                    //успешно - печатаем чек и удаляем данные
                    //иначе алерт
                }
                else{
                    System.out.println("не успешно");
                }

            }
        }

    }

}
