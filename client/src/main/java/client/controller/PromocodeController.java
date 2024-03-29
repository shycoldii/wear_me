package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.NoPromocodeException;
import client.utils.PromocodeStructure;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.json.JSONException;

import java.time.LocalDate;

/**
 * Контроллер промокодов
 */
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
     * Инициализирует первичные данные контролера
     */
    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        discountColumn.setCellValueFactory(cellData -> cellData.getValue().discountProperty().asObject());
        startColumn.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
        endColumn.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
        textField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(p -> p.getName().toLowerCase().contains(textField.getText().toLowerCase()))
        );
        promocodeTable.setRowFactory(new Callback<>()
        {
            @Override public TableRow<PromocodeStructure> call(TableView<PromocodeStructure> personTableView)
            {
                return new TableRow<>()
                {
                    @Override protected void updateItem(PromocodeStructure person, boolean b)
                    {
                        super.updateItem(person, b);


                        if (person == null){
                            getStyleClass().clear();
                            return;
                        }
                        if(!LocalDate.now().isBefore(LocalDate.parse(person.getStartDate()))&
                                !LocalDate.now().isAfter(LocalDate.parse(person.getEndDate()))&
                                (person.getName().toLowerCase().contains(textField.getText().toLowerCase())| textField.getText().isEmpty()))
                        {
                            getStyleClass().add("priorityLow");
                        }
                        else{
                            getStyleClass().clear();
                        }


                    }
                };
            }
        });

    }

    /**
     * Загружает и отображает данные промокодов
     * @throws JSONException - при работе с JSON-объектом
     * @throws NoPromocodeException - при работе с промокодами
     */
    public void loadData() throws JSONException, NoPromocodeException {
        this.promocodeTable.refresh();
        this.promocodeData = this.API.getPromocodes();
        this.filteredData = new FilteredList<>(promocodeData);
        this.sortableData = new SortedList<>(this.filteredData);
        this.promocodeTable.setItems(this.sortableData);
        this.sortableData.comparatorProperty().bind(this.promocodeTable.comparatorProperty());
    }

    /**
     * Добавляет промокод при нажатии кнопки
     */
    @FXML void addPromocode() {
        this.mainApp.initAddingPromocodeNew(this);
    }
}
