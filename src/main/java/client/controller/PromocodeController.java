package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.NoClientException;
import client.exception.NoPromocodeException;
import client.utils.PromocodeStructure;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONException;

public class PromocodeController {
    private Stage stage;
    @FXML
    private TableView<PromocodeStructure> promocodeTable;
    @FXML private TextField textField;
    @FXML private TableColumn<PromocodeStructure, String> nameColumn;
    @FXML private TableColumn<PromocodeStructure, Integer> discountColumn;
    @FXML private TableColumn<PromocodeStructure, String> startColumn;
    @FXML private TableColumn<PromocodeStructure, String> endColumn;
    private JavaFXApplication mainApp;

    private ObservableList<PromocodeStructure> promocodeData = FXCollections.observableArrayList();
    private FilteredList<PromocodeStructure> filteredData = new FilteredList<>(promocodeData);
    private SortedList<PromocodeStructure> sortableData = new SortedList<>(filteredData);
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
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        discountColumn.setCellValueFactory(cellData -> cellData.getValue().discountProperty().asObject());
        startColumn.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
        endColumn.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
        textField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(p -> p.getName().toLowerCase().contains(textField.getText().toLowerCase()))
        );
    }

    public void loadData() throws JSONException, NoPromocodeException {
        this.promocodeData = this.API.getPromocodes();
        this.filteredData = new FilteredList<>(promocodeData);
        this.sortableData = new SortedList<>(this.filteredData);
        this.promocodeTable.setItems(this.sortableData);
        this.sortableData.comparatorProperty().bind(this.promocodeTable.comparatorProperty());
    }
    @FXML void addPromocode() throws JSONException, NoPromocodeException {
        this.mainApp.initAddingPromocodeNew(this);
        this.loadData();
    }
}
