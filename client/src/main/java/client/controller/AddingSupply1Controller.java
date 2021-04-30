package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.ResponceStatusException;
import client.utils.AlertInfo;
import client.utils.MyLogger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Контроллер добавления поставки (стадия 1)
 */
public class AddingSupply1Controller {
    private Stage stage;
    private JavaFXApplication mainApp;
    private MyAPI API;
    private StockController stockController;
    private boolean status;

    /**
     * Получает статус
     * @return status
     */
    public boolean getStatus(){
        return this.status;
    }

    /**
     * Устанавливает контроллер склада
     * @param stockController - контроллер склада
     */
    public void setStockController(StockController stockController) {
        this.stockController = stockController;
    }
    /**
     * Устанавливает значение API
     * @param API - апи
     */
    public void setAPI(MyAPI API) {
        this.API = API;
    }
    /**
     * Устанавливает значение главному приложению
     * @param mainApp - главное приложение
     */
    public void setMainApp(JavaFXApplication mainApp) {
        this.mainApp = mainApp;
    }
    /**
     * Устанавливает значение сцены
     * @param dialogStage - сцена
     */
    public void setStage(Stage dialogStage) {
        this.stage = dialogStage;
    }

    @FXML private TextField articulField;

    /**
     * Переводит состояние добавления поставки во 2 с учетом данных при нажатии кнопки
     */
    @FXML
    public void continueSupply(){
        if(!this.articulField.getText().equals("")){
            try{
                int articul = Integer.parseInt(this.articulField.getText());
                this.status = this.API.getStoreProductByArticul(articul);
                this.mainApp.initSupply2(this);
                this.stage.close();

            }
            catch(NumberFormatException e){
                Alert alert = AlertInfo.getWarningAlert(mainApp);
                alert.initOwner(this.mainApp.getPrimaryStage());
                alert.setHeaderText("Please,write number");
                alert.show();
            }
            catch(ResponceStatusException e){
                Alert a = AlertInfo.getWarningAlert(mainApp);
                a.setHeaderText("Server not responding");
                a.setContentText("Status hasn't been changed");
                a.show();
            } catch (Exception e) {
                e.printStackTrace();
                MyLogger.logger.error(e.getMessage());
                Alert a = AlertInfo.getWarningAlert(mainApp);
                a.setHeaderText("Client error");
                a.setContentText(e.getMessage());
                a.show();
            }

        }
        else{
            Alert alert = AlertInfo.getWarningAlert(mainApp);
            alert.initOwner(this.mainApp.getPrimaryStage());
            alert.setHeaderText("Please,write articul");
            alert.show();
        }
    }

    /**
     * Получает область артикула
     * @return articulField
     */
    public TextField getArticulField() {
        return articulField;
    }

    /**
     * Получает контроллер склада
     * @return stockController
     */
    public StockController getStockController() {
        return stockController;
    }
}
