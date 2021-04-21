package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.NoOfficeException;
import client.utils.OfficeStructure;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONException;

/**
 * Контроллер офиса
 */
public class OfficeController {
    private Stage stage;
    @FXML private TableView<OfficeStructure> officeTable;
    @FXML private TextField textField;
    @FXML private TableColumn<OfficeStructure, Long> idColumn;
    @FXML private TableColumn<OfficeStructure, String> nameColumn;
    @FXML private TableColumn<OfficeStructure,String> phoneColumn;
    @FXML private TableColumn<OfficeStructure,String> addressColumn;

    private JavaFXApplication mainApp;

    private ObservableList<OfficeStructure> officeData = FXCollections.observableArrayList();
    private FilteredList<OfficeStructure> filteredData = new FilteredList<>(officeData);
    private SortedList<OfficeStructure> sortableData = new SortedList<>(filteredData);
    private MyAPI API;
    /**
     * Устанавливает значение сцены
     * @param dialogStage - сцена
     */
    public void setStage(Stage dialogStage) {
        this.stage = dialogStage;
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
     * Инициализация первичных объектов контроллера
     */
    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        textField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(p -> p.getName().toLowerCase().contains(textField.getText().toLowerCase()) |
                        p.getAddress().toLowerCase().contains(textField.getText().toLowerCase()) |
                        p.getPhone().toLowerCase().contains(textField.getText().toLowerCase())
                       )
        );
    }

    /**
     * Загружает данные об офисах
     * @throws JSONException
     * @throws NoOfficeException
     */
    public void loadData() throws JSONException, NoOfficeException {
        this.officeData = this.API.getOffices();
        this.filteredData = new FilteredList<>(officeData);
        this.sortableData = new SortedList<>(this.filteredData);
        this.officeTable.setItems(this.sortableData);
        this.sortableData.comparatorProperty().bind(this.officeTable.comparatorProperty());
    }
}
