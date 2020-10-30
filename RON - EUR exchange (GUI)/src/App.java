import java.util.Calendar;

import javax.swing.Action;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent; 
import javafx.event.EventHandler; 

public class App extends Application
{
    private Rates values;
    private ExchangeRateGetter rateGetter;
    private Scene mainScene;
    private SplitPane splitPane;
    private StackPane graphicArea;
    private ComboBox<String> baseChoice, symbolChoice, yearChoice;
    private Button analyzeButton;
    private LineChart<String, Number> exchangeRateChart;
    private NumberAxis valueAxis;
    private CategoryAxis dateAxis;
    private XYChart.Series<String, Number> rateSeries;
    private VBox controlArea;
    private HBox upsHBox, downsHBox;
    private Image upsIcon, downsIcon;
    private ImageView upIV, downIV;
    private Text upText, downText;
    private Tooltip baseTooltip, symbolTooltip, yearTooltip;
    private Alert errorAlert;
    private Calendar year;
    private ProgressIndicator progressIndicator;
    private static final String[] availableCurrencies = {"EUR", "USD", "JPY", "BGN", "CZK", "DKK", "GBP", "HUF", "PLN", "RON", "SEK", "CHF", "ISK",
    "NOK", "HRK", "RUB", "TRY", "AUD", "BRL", "CAD", "CNY", "HKD", "IDR", "ILS", "INR", "KRW", "MXN", "MYR", "NZD", "PHP", "SGD", "THB", "ZAR"};
    private static final String[] availableYears = {"2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019"};

    public static void main(String[] args) {
        launch(args);
    }

    private void doAnalysis()
    {
        rateSeries.getData().clear();

        while (true) 
            {
                String[] answer;
                String day = String.valueOf(year.get(Calendar.YEAR)) + '-'
                        + String.valueOf(year.get(Calendar.MONTH) + 1) + '-'
                        + String.valueOf(year.get(Calendar.DAY_OF_MONTH));
                
                try {
                    answer = rateGetter.getRate(day, values).split("_");

                    values.addValue(new Amount(answer[1], Double.parseDouble(answer[0])));
                } catch (Exception e) {
                    errorAlert.setTitle("Error while fetching data");
                    errorAlert.setContentText(e.getMessage());
                    errorAlert.show();
                }

                year.roll(Calendar.DAY_OF_MONTH, true);

                if (year.get(Calendar.DAY_OF_MONTH) == 1)
                    year.roll(Calendar.MONTH, true);

                if ((year.get(Calendar.MONTH) == 11) && (year.get(Calendar.DAY_OF_MONTH) == 31)) 
                {
                    try {
                        answer = rateGetter.getRate(String.valueOf(year.get(Calendar.YEAR)) + "-12-31", values).split("_");

                        values.addValue(new Amount(answer[1], Double.parseDouble(answer[0])));
                        progressIndicator.setProgress(progressIndicator.getProgress() + 0.002);
                        year.set(Integer.parseInt(yearChoice.getSelectionModel().getSelectedItem()), 0, 1);
                    } catch (Exception e) {
                        errorAlert.setTitle("Error while fetching data");
                        errorAlert.setContentText(e.getMessage());
                        errorAlert.show();
                    }

                    break;
                }
            }
            
            if(values.isEmpty() == false)
            {
                double[] answer = values.analyze(rateSeries);
                valueAxis.setUpperBound(answer[3] + 0.1);
                valueAxis.setLowerBound(answer[2] - 0.1);
                exchangeRateChart.getData().add(rateSeries);
                upText.setText(String.valueOf(answer[0]));
                downText.setText(String.valueOf(answer[1]));
            }
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("Exchange Rate Analyzer");

        values = new Rates();
        rateGetter = new ExchangeRateGetter();
        errorAlert = new Alert(AlertType.ERROR);
        year = Calendar.getInstance();
        rateSeries = new XYChart.Series<>();

        EventHandler<ActionEvent> baseChanged = event -> values.setBase(baseChoice.getSelectionModel().getSelectedItem());
        EventHandler<ActionEvent> symbolChanged = event -> values.setSymbol(symbolChoice.getSelectionModel().getSelectedItem());
        EventHandler<ActionEvent> yearChanged = event -> year.set(Integer.parseInt(yearChoice.getSelectionModel().getSelectedItem()), 0, 1);
        EventHandler<ActionEvent> doAnalysis = event -> doAnalysis();
        
        splitPane = new SplitPane();

        controlArea = new VBox(25);
        baseTooltip = new Tooltip("Select the base currency.");
        baseChoice = new ComboBox<>();
        baseChoice.setTooltip(baseTooltip);
        baseChoice.getItems().addAll(availableCurrencies);
        baseChoice.setOnAction(baseChanged);
        baseChoice.getSelectionModel().selectFirst();
        values.setBase(availableCurrencies[0]);

        symbolTooltip = new Tooltip("Select the symbol currency.");
        symbolChoice = new ComboBox<>();
        symbolChoice.setTooltip(symbolTooltip);
        symbolChoice.getItems().addAll(availableCurrencies);
        symbolChoice.setOnAction(symbolChanged);
        symbolChoice.getSelectionModel().selectFirst();
        values.setSymbol(availableCurrencies[0]);

        yearTooltip = new Tooltip("Select the year to analyze.");
        yearChoice = new ComboBox<>();
        yearChoice.setTooltip(yearTooltip);
        yearChoice.getItems().addAll(availableYears);
        yearChoice.setOnAction(yearChanged);
        yearChoice.getSelectionModel().selectFirst();
        year.set(Integer.parseInt(availableYears[0]), 0, 1);

        analyzeButton = new Button("Analyze");
        analyzeButton.setOnAction(doAnalysis);

        upsIcon = new Image("arrow.png", 20, 40, true, true);
        upIV = new ImageView(upsIcon);
        upText = new Text("0");
        upsHBox = new HBox(15);
        upsHBox.setAlignment(Pos.CENTER);
        upsHBox.setMouseTransparent(true);
        upsHBox.getChildren().addAll(upIV, upText);

        downsIcon = new Image("arrow.png", 20, 40, true, true);
        downIV = new ImageView(downsIcon);
        downText = new Text("0");
        downIV.setRotate(180);
        downsHBox = new HBox(15);
        downsHBox.setAlignment(Pos.CENTER);
        downsHBox.setMouseTransparent(true);
        downsHBox.getChildren().addAll(downIV, downText);   
        
        controlArea.setAlignment(Pos.CENTER);
        controlArea.getChildren().addAll(baseChoice, symbolChoice, yearChoice, analyzeButton, upsHBox, downsHBox);

        progressIndicator = new ProgressIndicator(0);


        dateAxis = new CategoryAxis();
        dateAxis.setLabel("Date");
        valueAxis = new NumberAxis();
        valueAxis.setLabel("Value");
        valueAxis.setAutoRanging(false);
        valueAxis.setTickUnit(0.01);
        exchangeRateChart = new LineChart<>(dateAxis, valueAxis);

        graphicArea = new StackPane();
        graphicArea.setAlignment(Pos.CENTER);
        graphicArea.getChildren().addAll(exchangeRateChart);

        splitPane.getItems().addAll(controlArea, graphicArea);
        splitPane.setDividerPosition(0, 0.10f);
        
        mainScene = new Scene(splitPane, 1600, 900);

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}