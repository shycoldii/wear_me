package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.NoClientException;
import client.utils.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;
import java.util.Optional;

public class ClientController {
    private Stage stage;
    @FXML private TableView<ClientStructure> clientTable;
    @FXML private TextField textField;
    @FXML private TableColumn<ClientStructure, Long> idColumn;
    @FXML private TableColumn<ClientStructure, Integer> bonusesColumn;
    @FXML private TableColumn<ClientStructure,String> firstNameColumn;
    @FXML private TableColumn<ClientStructure,String> secondNameColumn;
    @FXML private TableColumn<ClientStructure,String> birthdayColumn;
    @FXML private TableColumn<ClientStructure,String> phoneNumberColumn;
    @FXML private TableColumn<ClientStructure,String> emailColumn;
    private JavaFXApplication mainApp;

    private ObservableList<ClientStructure> clientData = FXCollections.observableArrayList();
    private FilteredList<ClientStructure> filteredData = new FilteredList<>(clientData);
    private SortedList<ClientStructure> sortableData = new SortedList<>(filteredData);
    private MyAPI API;
    public void setStage(Stage dialogStage) {
        this.stage = dialogStage;
    }
    public void setAPI(MyAPI API) {
        this.API = API;
    }
    public void setMainApp(JavaFXApplication mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        bonusesColumn.setCellValueFactory(cellData -> cellData.getValue().bonusesProperty().asObject());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        secondNameColumn.setCellValueFactory(cellData -> cellData.getValue().secondNameProperty());
        birthdayColumn.setCellValueFactory(cellData -> cellData.getValue().birthdayProperty());
        phoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        textField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(p -> p.getFirstName().toLowerCase().contains(textField.getText().toLowerCase()) |
                        p.getSecondName().toLowerCase().contains(textField.getText().toLowerCase()) |
                        p.getPhoneNumber().toLowerCase().contains(textField.getText().toLowerCase()) |
                        p.getEmail().toLowerCase().contains(textField.getText().toLowerCase()))

        );
    }

    public void loadData() throws JSONException, NoClientException {
        this.clientData = this.API.getClients();
        this.filteredData = new FilteredList<>(clientData);
        this.sortableData = new SortedList<>(this.filteredData);
        this.clientTable.setItems(this.sortableData);
        this.sortableData.comparatorProperty().bind(this.clientTable.comparatorProperty());
    }
    @FXML
    public void addClient() throws JSONException, NoClientException {
          this.mainApp.initAddingClient(this);
          this.loadData();
    }
    @FXML
    public void removeClient()  {
      if(this.API.getPosition().toLowerCase().equals("менеджер") | this.API.getPosition().toLowerCase().equals("программист")){
          int selectedIndex = this.clientTable.getSelectionModel().getSelectedIndex();
          if (selectedIndex >= 0) {
              Long id = this.clientTable.getItems().get(selectedIndex).getId();
              Alert alert = AlertInfo.getConfirmationAlert(mainApp);
              alert.setHeaderText("Delete the client");
              alert.setContentText("Are you ready to complete the operation?");
              Optional<ButtonType> option = alert.showAndWait();
              if (option.get() == ButtonType.OK){
                  try{
                      this.API.deleteClient(id);
                      MyLogger.logger.info("Удален клиент из таблицы");
                      this.loadData();
                  }
                  catch (Exception e){
                      MyLogger.logger.error(e.getMessage());
                      Alert a = AlertInfo.getWarningAlert(this.mainApp);
                      a.setHeaderText("Failed to delete client");
                      a.show();
                  }
              }


          } else {
              Alert alert = AlertInfo.getWarningAlert(mainApp);
              alert.initOwner(this.mainApp.getPrimaryStage());
              alert.setTitle("No selection");
              alert.setHeaderText("No client selected");
              alert.setContentText("Please, select the line in the table");
              alert.show();
              MyLogger.logger.error("Ошибка при удалении клиента. Не выделено!");
          }
      }
      else{
          Alert alert = AlertInfo.getWarningAlert(mainApp);
          alert.setHeaderText("You don't have permission for this operation");
          MyLogger.logger.error("Попытка удалить клиента без доступа");
          alert.show();
      }
    }

}
