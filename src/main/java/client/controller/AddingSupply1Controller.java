package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.ResponceStatusException;
import client.utils.AlertInfo;
import client.utils.MyLogger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;

public class AddingSupply1Controller {
    private Stage stage;
    private JavaFXApplication mainApp;
    private MyAPI API;
    private StockController stockController;
    @FXML private TextField colorField;
    private boolean status;
    public boolean getStatus(){
        return this.status;
    }

    public void setStockController(StockController stockController) {
        this.stockController = stockController;
    }

    public void setAPI(MyAPI API) {
        this.API = API;
    }
    public void setMainApp(JavaFXApplication mainApp) {
        this.mainApp = mainApp;
    }
    public void setStage(Stage dialogStage) {
        this.stage = dialogStage;
    }

    @FXML
    public void initialize() {}
    @FXML private TextField articulField;
    @FXML
    public void continueSupply(){
        if(!this.articulField.getText().equals("")){
            try{
                int articul = Integer.parseInt(this.articulField.getText());
                if(this.API.getStoreProductByArticul(articul)){
                    this.status = true;
                }
                else{
                    this.status = false;
                }
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

    public TextField getArticulField() {
        return articulField;
    }

    public StockController getStockController() {
        return stockController;
    }
}
