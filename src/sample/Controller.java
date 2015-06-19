package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import sample.VisitInformation.FromType;
import sample.VisitInformation.SiteAction;
import sample.VisitInformation.SiteActions.SiteActionCall;
import sample.VisitInformation.SiteActions.SiteActionProductPageEnter;
import sample.VisitInformation.SiteActions.SiteActionPurchase;

import java.io.*;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    private ArrayList<Visit> visits;
    private final ObservableList<TableVisit> data = FXCollections.observableArrayList();
    private double callConv;
    private double purchaseConv;
    private double enterConv;
    private double[][] typeConversions;
    private double[][] siteConversions;
    HashMap<String, Double> prices = new HashMap<String, Double>();
    private double[] averageSiteTime;
    private ArrayList<String> siteNames;
    @FXML
    TableView<TableVisit> tableVisits;
    @FXML
    TableColumn<TableVisit, String> columnID;
    @FXML
    TableColumn<TableVisit, String> columnSourceSite;
    @FXML
    TableColumn<TableVisit, String> columnSourceType;
    @FXML
    TableColumn<TableVisit, String> columnDate;
    @FXML
    TableColumn<TableVisit, String> columnTime;
    @FXML
    TableColumn<TableVisit, String> columnActions;
    @FXML
    LineChart<String,Integer> lineChartDate;
    @FXML
    LineChart<Integer,Integer> lineChartTime;
    @FXML
    PieChart pieChartSite;
    @FXML
    PieChart pieChartSiteType;
    @FXML
    TextArea textAreaWhen;
    @FXML
    ListView listViewConversionTypes;
    @FXML
    ChoiceBox choiceBoxTypes;
    @FXML
    ToggleButton toggleGlobal;
    @FXML
    ToggleButton toggleType;
    @FXML
    ToggleButton toggleSite;
    @FXML
    ListView listViewConversions;
    @FXML
    ListView listViewIndexTypes;
    @FXML
    ListView listViewIndex;
    @FXML
    BarChart<String, Double> barChartAvTimes;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnID.setCellValueFactory(new PropertyValueFactory<TableVisit, String>("ID"));
        columnSourceSite.setCellValueFactory(new PropertyValueFactory<TableVisit, String>("sourceSite"));
        columnSourceType.setCellValueFactory(new PropertyValueFactory<TableVisit, String>("sourceType"));
        columnDate.setCellValueFactory(new PropertyValueFactory<TableVisit, String>("date"));
        columnTime.setCellValueFactory(new PropertyValueFactory<TableVisit, String>("timeAfterBannerDisplay"));
        columnActions.setCellValueFactory(new PropertyValueFactory<TableVisit, String>("siteActions"));
        listViewConversionTypes.getItems().add("Звонки");
        listViewConversionTypes.getItems().add("Просмотры");
        listViewConversionTypes.getItems().add("Покупки к просмотрам");
        listViewIndexTypes.getItems().add("Звонки");
        listViewIndexTypes.getItems().add("Просмотры");
        listViewIndexTypes.getItems().add("Покупки к просмотрам");
        choiceBoxTypes.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (toggleType.isSelected()) {
                    listViewConversions.getItems().clear();
                    listViewIndex.getItems().clear();
                    int index = newValue.intValue();
                    if (index != -1) {
                        listViewConversions.getItems().add("" + typeConversions[index][0] * 100 + "%");
                        listViewConversions.getItems().add("" + typeConversions[index][1] * 100 + "%");
                        listViewConversions.getItems().add("" + typeConversions[index][2] * 100 + "%");
                    }
                } else if (toggleSite.isSelected()) {
                    listViewConversions.getItems().clear();
                    listViewIndex.getItems().clear();
                    int index = newValue.intValue();
                    if (index != -1) {
                        listViewConversions.getItems().add("" + siteConversions[index][0] * 100 + "%");
                        listViewConversions.getItems().add("" + siteConversions[index][1] * 100 + "%");
                        listViewConversions.getItems().add("" + siteConversions[index][2] * 100 + "%");
                        listViewIndex.getItems().add(siteConversions[index][0] * 100 / prices.get(siteNames.get(newValue.intValue())));
                        listViewIndex.getItems().add(siteConversions[index][1] * 100 / prices.get(siteNames.get(newValue.intValue())));
                        listViewIndex.getItems().add(siteConversions[index][2] * 100 / prices.get(siteNames.get(newValue.intValue())));
                    }
                }
            }
        });
    }
    @FXML
    public void loadDataFromFS() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть файл");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Visit data sheet files (*.vds)", "*.vds");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(null);
        if (file == null)
            return;
        visits = new ArrayList<Visit>();
        StringBuilder sBData = new StringBuilder();
        try {
            Scanner s = new Scanner (file);
            while (s.hasNextLine()) {
                sBData.append(s.nextLine() + "\r\n");
            }
            String[] parts = sBData.toString().split("\t");
            String[] mapParts = parts[0].split("\r\n");
            for (int i = 0; i < mapParts.length; i++) {
                prices.put(mapParts[i].substring(0, mapParts[i].indexOf(' ')), Double.parseDouble(mapParts[i].substring(mapParts[i].indexOf(' ') + 1)));
            }
            mapParts.clone();
            String[] visitParts = parts[1].split("\r\n");
            for (int i = 0; i < visitParts.length; i++) {
                visits.add(new Visit(visitParts[i]));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        };



        for (Visit visit : visits) {
            data.add(visit.getTableVisit());
        }
        tableVisits.setItems(data);

        int[] siteTypeCount = {0, 0, 0, 0};
        int[] typeActionCount = {0, 0, 0, 0};
        typeConversions = new double[4][];
        for (int i = 0; i < typeConversions.length; i++) {
            typeConversions[i] = new double[3];
            for (int j = 0; j < typeConversions[i].length; j++) {
                typeConversions[i][j] = 0;
            }
        }
        for (Visit visit : visits) {
            switch (visit.getFromInformation().getFromType()) {
                case NEWS: {
                    siteTypeCount[0]++;
                    for (SiteAction action : visit.getSiteActions()) {
                        typeActionCount[0]++;
                        if (action instanceof SiteActionCall) {
                            typeConversions[0][0]++;
                        } else if (action instanceof SiteActionProductPageEnter) {
                            typeConversions[0][1]++;
                        } else {
                            typeConversions[0][2]++;
                        }
                    }
                }
                    break;
                case SENGINE: {
                    siteTypeCount[1]++;
                    for (SiteAction action : visit.getSiteActions()) {
                        typeActionCount[1]++;
                        if (action instanceof SiteActionCall) {
                            typeConversions[1][0]++;
                        } else if (action instanceof SiteActionProductPageEnter) {
                            typeConversions[1][1]++;
                        } else {
                            typeConversions[1][2]++;
                        }
                    }
                }
                break;
                case SITE: {
                    siteTypeCount[2]++;
                    for (SiteAction action : visit.getSiteActions()) {
                        typeActionCount[2]++;
                        if (action instanceof SiteActionCall) {
                            typeConversions[2][0]++;
                        } else if (action instanceof SiteActionProductPageEnter) {
                            typeConversions[2][1]++;
                        } else {
                            typeConversions[2][2]++;
                        }
                    }
                }
                break;
                case OTHER: {
                    siteTypeCount[3]++;
                    for (SiteAction action : visit.getSiteActions()) {
                        typeActionCount[3]++;
                        if (action instanceof SiteActionCall) {
                            typeConversions[3][0]++;
                        } else if (action instanceof SiteActionProductPageEnter) {
                            typeConversions[3][1]++;
                        } else {
                            typeConversions[3][2]++;
                        }
                    }
                }
                break;
            }
        }
        for (int i = 0; i < typeConversions.length; i++) {
            for (int j = 0; j < typeConversions[i].length; j++) {
                typeConversions[i][j] /= typeActionCount[i];
            }
            typeConversions[i][2] /= typeConversions[i][1];
        }
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Новости", siteTypeCount[0]),
                        new PieChart.Data("Поисковики", siteTypeCount[1]),
                        new PieChart.Data("Другие сайты", siteTypeCount[2]),
                        new PieChart.Data("Другое", siteTypeCount[3]));
        pieChartSite.setData(pieChartData);
        choiceBoxTypes.getItems().clear();
        siteNames = new ArrayList<String>();
        int[] siteCounts = new int[1000];
        for (Visit visit : visits) {
            if (visit.getFromInformation().getFromType() == FromType.OTHER)
                continue;
            if (!siteNames.contains(visit.getFromInformation().getSiteName())) {
                siteNames.add(visit.getFromInformation().getSiteName());
                siteCounts[siteNames.indexOf(visit.getFromInformation().getSiteName())] = 1;
            } else {
                siteCounts[siteNames.indexOf(visit.getFromInformation().getSiteName())] ++;
            }
        }
        siteConversions = new double[siteNames.size()][];
        for (int i = 0; i < siteConversions.length; i++) {
            siteConversions[i] = new double[3];
            for (int j = 0; j < siteConversions[i].length; j++) {
                siteConversions[i][j] = 0;
            }
        }
        int[] siteConvCount = new int[siteCounts.length];
        for (int i = 0; i < siteConvCount.length; i++) {
            siteConvCount[i] = 0;
        }
        for (Visit visit : visits) {
            for (SiteAction action : visit.getSiteActions()) {
                int index = siteNames.indexOf(visit.getFromInformation().getSiteName());
                if (index != -1) {
                    siteConvCount[index]++;
                    if (action instanceof SiteActionCall) {
                        siteConversions[index][0]++;
                    } else if (action instanceof SiteActionProductPageEnter) {
                        siteConversions[index][1]++;
                    } else {
                        siteConversions[index][2]++;
                    }
                }
            }
        }
        for (int i = 0; i < siteConversions.length; i++) {
            for (int j = 0; j < siteConversions[i].length; j++) {
                siteConversions[i][j] /= siteConvCount[i];
            }
            siteConversions[i][2] /= siteConversions[i][1];
        }

        ObservableList<PieChart.Data> pieChartData2 = FXCollections.observableArrayList();
        for (int i = 0 ; i < siteNames.size(); i++) {
            String name = siteNames.get(i);
            if (prices.containsKey(siteNames.get(i))) {
                name += " " + prices.get(siteNames.get(i)) + " $";
                if (name.lastIndexOf('.') == name.length() - 4) {
                    name = name.substring(0, name.lastIndexOf(' ')) + "0 $";
                }
            }
            pieChartData2.add(new PieChart.Data(name, siteCounts[i]));
        }
        pieChartSiteType.setData(pieChartData2);


        long[] timeArray = new long[visits.size()];
        long minTime = visits.get(0).getWhenInformation().getTimeAfterBannerDisplay().getTime();
        long maxTime = minTime;
        int count = 0;
        int[] avTimeCounts = new int[siteNames.size()];
        for (int i = 0; i < avTimeCounts.length; i++) {
            avTimeCounts[i] = 0;
        }
        averageSiteTime = new double[siteNames.size()];
        for (Visit visit : visits) {
            timeArray[count ++] = visit.getWhenInformation().getTimeAfterBannerDisplay().getTime();
            if (minTime > timeArray[count - 1])
                minTime = timeArray[count - 1];
            else if (maxTime < timeArray[count - 1])
                maxTime = timeArray[count - 1];
            if (siteNames.contains(visit.getFromInformation().getSiteName())) {
                averageSiteTime[siteNames.indexOf(visit.getFromInformation().getSiteName())] += visit.getWhenInformation().getTimeAfterBannerDisplay().getTime();
                avTimeCounts[siteNames.indexOf(visit.getFromInformation().getSiteName())] ++;
            }
        }
        for (int i = 0; i < averageSiteTime.length; i++) {
            averageSiteTime[i] /= avTimeCounts[i];
        }
        ObservableList<XYChart.Data<String, Double>> avTimeList = FXCollections.observableArrayList();
        for (int i = 0; i < siteNames.size(); i++) {
            avTimeList.add(new XYChart.Data<String, Double>(siteNames.get(i), averageSiteTime[i]));
        }
        barChartAvTimes.getData().add(new XYChart.Series<String, Double>("Среднее время перехода после показа баннера",avTimeList));
        int[] counts = new int[20];
        for (int i = 0; i < counts.length; i++) {
            counts[i] = 0;
        }
        for (int i = 0; i < timeArray.length; i++) {
            counts[((int) ((timeArray[i] - minTime - 1) * counts.length / ((maxTime - minTime))))]++;
        }

        ArrayList<Long> timeList = new ArrayList<Long>();
        for (int i = 0; i < timeArray.length; i++) {
            timeList.add(timeArray[i]);
        }
        Collections.sort(timeList);
        textAreaWhen.setText("Медиана распределения времени перехода после показа баннера равна " + timeList.get(timeList.size() / 2) + " мс, что говорит о " + (timeList.get(timeList.size() / 2) > 5000 ? "не" : "") + "достаточной привлекательности баннера для пользователей.");

        XYChart.Series seriesTime = new XYChart.Series();
        for (int i = 0; i < counts.length; i++) {
            seriesTime.getData().add(new XYChart.Data<Integer, Integer>((int) (i * (maxTime - minTime) / counts.length), counts[i], ""));
        }
        seriesTime.setName("Распределение времени перехода после показа баннера, мс");
        lineChartTime.getData().add(seriesTime);

        counts = new int[24];
        for (int i = 0; i < counts.length; i++) {
            counts[i] = 0;
        }
        Calendar calendar = Calendar.getInstance();
        for (Visit visit : visits) {
            calendar.setTime(visit.getWhenInformation().getDate());
            counts[calendar.get(Calendar.HOUR_OF_DAY)]++;
        }
        ObservableList<XYChart.Data<String, Integer>> dateList = FXCollections.observableArrayList();
        for (int i = 0; i < counts.length; i++) {
            dateList.add(new XYChart.Data<String, Integer>(i + ":00", counts[i]));
        }
        lineChartDate.getData().add(new XYChart.Series<String, Integer>("Распределение времение перехода",dateList));

        callConv = 0;
        purchaseConv = 0;
        enterConv = 0;
        int actionCount = 0;
        for (Visit visit : visits) {
            for (SiteAction action : visit.getSiteActions()) {
                if (action instanceof SiteActionCall) {
                    callConv++;
                } else if (action instanceof SiteActionPurchase) {
                    purchaseConv++;
                } else {
                    enterConv++;
                }
                actionCount++;
            }
        }
        callConv /= actionCount;
        purchaseConv /= actionCount;
        enterConv /= actionCount;
    }
    @FXML
    private void globalPressed() {
        choiceBoxTypes.setDisable(true);
        listViewConversions.getItems().clear();
        choiceBoxTypes.getItems().clear();
        listViewIndex.getItems().clear();
        listViewConversions.getItems().add("" + callConv * 100 + "%");
        listViewConversions.getItems().add("" + enterConv * 100 + "%");
        listViewConversions.getItems().add("" + purchaseConv * 100 + "%");
        listViewIndexTypes.setDisable(true);
        listViewIndex.setDisable(true);
    }
    @FXML
    private void typePressed() {
        choiceBoxTypes.setDisable(false);
        listViewConversions.getItems().clear();
        choiceBoxTypes.getItems().clear();
        listViewIndex.getItems().clear();
        choiceBoxTypes.getItems().add("Новости");
        choiceBoxTypes.getItems().add("Поисковики");
        choiceBoxTypes.getItems().add("Другие сайты");
        choiceBoxTypes.getItems().add("Другое");
        listViewIndexTypes.setDisable(true);
        listViewIndex.setDisable(true);
    }
    @FXML
    private void sitePressed() {
        choiceBoxTypes.setDisable(false);
        listViewConversions.getItems().clear();
        choiceBoxTypes.getItems().clear();
        listViewIndex.getItems().clear();
        for (String siteName : siteNames) {
            choiceBoxTypes.getItems().add(siteName);
        }
        listViewIndexTypes.setDisable(false);
        listViewIndex.setDisable(false);
    }
}
