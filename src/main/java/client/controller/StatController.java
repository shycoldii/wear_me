package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.NoStoreProductException;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import scala.Int;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class StatController {
    private Stage stage;
    private JavaFXApplication mainApp;
    private MyAPI API;
    ObservableList<String> observableList = FXCollections.observableArrayList();

    public void setAPI(MyAPI API) {
        this.API = API;
    }

    public void setMainApp(JavaFXApplication mainApp) {
        this.mainApp = mainApp;
    }

    public void setStage(Stage dialogStage) {
        this.stage = dialogStage;
    }

    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();

    @FXML
    LineChart<String,Integer> stat = new LineChart(xAxis, yAxis);
    @FXML
    LineChart<String,Integer> stat11 = new LineChart(xAxis, yAxis);
    @FXML
    LineChart<String,Integer> stat1 = new LineChart(xAxis, yAxis);
    @FXML
    public void initialize() {
        observableList.add("MONTHS");
        observableList.add("YEARS");
        observableList.add("DAYS");

    }

    public XYChart.Series<String, Integer> loadMonths() throws JSONException, NoStoreProductException {
        this.API.getProducts();
        JSONArray data = this.API.getJsonProducts();
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("JANUARY",0);
        map.put("FEBRUARY",0);
        map.put("MARCH",0);
        map.put("APRIL",0);
        map.put("MAY",0);
        map.put("JUNE",0);
        map.put("JULY",0);
        map.put("AUGUST",0);
        map.put("SEPTEMBER",0);
        map.put("OCTOBER",0);
        map.put("NOVEMBER",0);
        map.put("DECEMBER",0);

        for (int i = 0; i < data.length(); i++) {
            try {
                String j = data.getJSONObject(i).getJSONObject("check").
                        getString("dateTime");
                LocalDateTime date = LocalDateTime.parse(j);
                if (date.getYear() == LocalDate.now().getYear()) {
                    int month = date.getMonthValue();
                    int discount = data.getJSONObject(i).getJSONObject("check").getInt("discount");
                    int value = (data.getJSONObject(i).getInt("retailPrice") * (100 - discount) / 100 -
                            data.getJSONObject(i).getInt("tradePrice"));
                    if (map.get(date.getMonth().toString()) != null) {
                        map.put(date.getMonth().toString(),
                                map.get(date.getMonth().toString()) +
                                        value);
                    } else {
                        map.put(date.getMonth().toString(),
                                value);
                    }


                }
            } catch (JSONException ignored) {
            }
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            series.getData().add(new XYChart.Data<String, Integer>(entry.getKey(), entry.getValue()));
        }
        System.out.println(map.toString());
        series.setName("income/months");
        return series;
    }

    public void loadData() throws JSONException, NoStoreProductException {
        stat.getData().add(loadMonths());
        stat.setStyle(" -fx-stroke: black;" +
                "-fx-font-family: \"Arial Black\";" +
                "    -fx-text-fill: #2d2d30;");

        stat1.getData().add(loadDays());
        stat1.setStyle(" -fx-stroke: black;" +
                "-fx-font-family: \"Arial Black\";" +
                "    -fx-text-fill: #2d2d30;");
        stat11.getData().add(loadYears());
        stat11.setStyle(" -fx-stroke: black;" +
                "-fx-font-family: \"Arial Black\";" +
                "    -fx-text-fill: #2d2d30;");
    }


    private XYChart.Series<String, Integer> loadYears()  {
        JSONArray data = this.API.getJsonProducts();
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < data.length(); i++) {
            try {
                String j = data.getJSONObject(i).getJSONObject("check").
                        getString("dateTime");
                LocalDateTime date = LocalDateTime.parse(j);
                int year = date.getYear();
                int discount = data.getJSONObject(i).getJSONObject("check").getInt("discount");
                int value = (data.getJSONObject(i).getInt("retailPrice") * (100 - discount) / 100 -
                        data.getJSONObject(i).getInt("tradePrice"));
                map.merge(Integer.toString(year),
                        value, Integer::sum);

            } catch (JSONException ignored) {
            }
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        Map<String, Integer> treeMap = new TreeMap<>(map);
        for (Map.Entry<String, Integer> entry : treeMap.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        series.setName("income/years");
        return series;
    }

    private XYChart.Series<String, Integer> loadDays() {
        JSONArray data = this.API.getJsonProducts();
        String name = "";
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < data.length(); i++) {
            try {
                String j = data.getJSONObject(i).getJSONObject("check").
                        getString("dateTime");
                LocalDateTime date = LocalDateTime.parse(j);
                if (date.getYear() == LocalDate.now().getYear() & date.getMonthValue() ==
                        LocalDate.now().getMonthValue()) {
                    name = date.getMonth().toString();
                    int discount = data.getJSONObject(i).getJSONObject("check").getInt("discount");
                    int value = (data.getJSONObject(i).getInt("retailPrice") * (100 - discount) / 100 -
                            data.getJSONObject(i).getInt("tradePrice"));
                    map.merge(date.getDayOfMonth(),
                            value, Integer::sum);


                }
            } catch (JSONException ignored) {
            }
        }
        for(int i=1;i<32;i++){
            if (!map.containsKey(i)){
                map.put(i, 0);
            }
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        Map<Integer, Integer> treeMap = new TreeMap<>(map);
        for (Map.Entry<Integer, Integer> entry : treeMap.entrySet()) {
            String entryk = entry.getKey().toString();
            series.getData().add(new XYChart.Data<>(entryk, entry.getValue()));

        }
        series.setName("income/days");
        return series;
    }

}
