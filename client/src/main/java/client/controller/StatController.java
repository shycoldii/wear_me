package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.NoStoreProductException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Контроллер статистики
 */
public class StatController {
    private Stage stage;
    private JavaFXApplication mainApp;
    private MyAPI API;
    ObservableList<String> observableList = FXCollections.observableArrayList();
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

    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();

    @FXML
    LineChart<String,Integer> stat = new LineChart(xAxis, yAxis);
    @FXML
    LineChart<String,Integer> stat11 = new LineChart(xAxis, yAxis);
    @FXML
    LineChart<String,Integer> stat1 = new LineChart(xAxis, yAxis);

    /**
     * Инициализирует контроллер
     */
    @FXML
    public void initialize() {
        observableList.add("MONTHS");
        observableList.add("YEARS");
        observableList.add("DAYS");

    }

    /**
     * Загружает статистику отделения по месяцам
     * @return статистика по отделениям
     * @throws JSONException - при работе с JSON-объектом
     * @throws NoStoreProductException -при отсутствии товаров
     */
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
        LocalDateTime date = null;
        for (int i = 0; i < data.length(); i++) {
            try {
                String j = data.getJSONObject(i).getJSONObject("check").
                        getString("dateTime");
                date = LocalDateTime.parse(j);
                if (date.getYear() == LocalDate.now().getYear() &
                        data.getJSONObject(i).getJSONObject("office").getLong("id") ==
                this.API.getOfficeId()) {
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
                if (date!=null & data.getJSONObject(i).getJSONObject("office").getLong("id") ==
                        this.API.getOfficeId()){
                    map.merge(date.getMonth().toString(),
                            -data.getJSONObject(i).getInt("tradePrice"),Integer::sum);
                }

            }
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            series.getData().add(new XYChart.Data<String, Integer>(entry.getKey(), entry.getValue()));
        }
        series.setName("months");
        return series;
    }

    /**
     * Загружает данные статистик отделения
     * @throws JSONException  - при работе с JSON-объектом
     * @throws NoStoreProductException - при отсутствии товаров
     */
    public void loadData() throws JSONException, NoStoreProductException {
        stat.getData().add(loadMonths());
        Set<Node> nodes = stat.lookupAll(".series" + 0);
        for (Node n : nodes) {
            n.setStyle("-fx-stroke: #7f7182;\n"+"-fx-background-color: black, white;"
                    );
        }
        stat.setStyle("-fx-font-family: 'Arial Black'");
        stat1.setStyle("-fx-font-family: 'Arial Black'");
        stat11.setStyle("-fx-font-family: 'Arial Black'");
        stat1.getData().add(loadDays());
        stat11.getData().add(loadYears());
        nodes = stat11.lookupAll(".series" + 0);
        Platform.runLater(()->{
            Node ns = stat11.lookup(".default-color0.chart-legend-item-symbol");
            ns.setStyle("-fx-background-color: black, white");
            ns = stat.lookup(".default-color0.chart-legend-item-symbol");
            ns.setStyle("-fx-background-color: black, white");
        });
        for (Node n : nodes) {
            n.setStyle("-fx-stroke: #9bb1ba;\n"+"-fx-background-color: black, white;\n"+"" +
                    "chart-legend-item-symbol: #9bb1ba"
            );
        }
    }

    /**
     * Загружает статистику отделения по годам
     * @return XYChart.Series<String, Integer>
     * @throws JSONException - при работе с JSON-объектом
     */
    private XYChart.Series<String, Integer> loadYears() throws JSONException {
        JSONArray data = this.API.getJsonProducts();
        HashMap<String, Integer> map = new HashMap<>();
        int year1 = 0;
        for (int i = 0; i < data.length(); i++) {
            try {
                if(  data.getJSONObject(i).getJSONObject("office").getLong("id") ==
                        this.API.getOfficeId()){
                    String j = data.getJSONObject(i).getJSONObject("check").
                            getString("dateTime");
                    LocalDateTime date = LocalDateTime.parse(j);
                    int year = date.getYear();
                    year1 = year;
                    int discount = data.getJSONObject(i).getJSONObject("check").getInt("discount");
                    int value = (data.getJSONObject(i).getInt("retailPrice") * (100 - discount) / 100 -
                            data.getJSONObject(i).getInt("tradePrice"));
                    map.merge(Integer.toString(year),
                            value, Integer::sum);
                }


            }catch (JSONException ignored) {
                if (year1!=0 & data.getJSONObject(i).getJSONObject("office").getLong("id") ==
                        this.API.getOfficeId()){
                    map.merge(Integer.toString(year1),
                            -data.getJSONObject(i).getInt("tradePrice"),Integer::sum);
                }

            }
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        Map<String, Integer> treeMap = new TreeMap<>(map);
        for (Map.Entry<String, Integer> entry : treeMap.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        series.setName("years");
        return series;
    }

    /**
     * Загружает статистику отделения по дням
     * @return XYChart.Series<String, Integer>
     * @throws JSONException - при работе с JSON-объектом
     */
    private XYChart.Series<String, Integer> loadDays() throws JSONException {
        JSONArray data = this.API.getJsonProducts();
        String name = "";
        HashMap<Integer, Integer> map = new HashMap<>();
        LocalDateTime date1 = null;
        for (int i = 0; i < data.length(); i++) {
            try {
                String j = data.getJSONObject(i).getJSONObject("check").
                        getString("dateTime");
                LocalDateTime date = LocalDateTime.parse(j);
                date1=date;
                if (date.getYear() == LocalDate.now().getYear() & date.getMonthValue() ==
                        LocalDate.now().getMonthValue() & data.getJSONObject(i).getJSONObject("office").getLong("id") ==
                        this.API.getOfficeId()
                ) {
                    name = date.getMonth().toString();
                    int discount = data.getJSONObject(i).getJSONObject("check").getInt("discount");
                    int value = (data.getJSONObject(i).getInt("retailPrice") * (100 - discount) / 100 -
                            data.getJSONObject(i).getInt("tradePrice"));
                    map.merge(date.getDayOfMonth(),
                            value, Integer::sum);


                }
            } catch (JSONException ignored) {
                if (date1!=null & data.getJSONObject(i).getJSONObject("office").getLong("id") ==
                        this.API.getOfficeId()){
                    map.merge(date1.getDayOfMonth(),
                            -data.getJSONObject(i).getInt("tradePrice"),Integer::sum);
                }

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
        series.setName(name.toLowerCase());
        return series;
    }

}
