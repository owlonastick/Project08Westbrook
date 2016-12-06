import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: Gregory Westbrook
 * Class: Advanced Java
 * Project: 03
 * Date Due: 2016.10.12
 * Created by Greg on 11/30/2016 at 7:16 PM.
 */
public class ChartMaker extends Application {

    private Label lbl;
    private List<DataModel> datalist = new ArrayList<>();
    private BarChart<String, Number> myChart = null;

    public static void main( String[] args ) {
        launch( args );
    }

    public void start( Stage myStage ){
        //Get things set up
        myStage.setTitle( "Greg's ChartMaker" );
        BorderPane rootNode = new BorderPane();
        Scene myScene =  new Scene(rootNode, 600, 600);
        myStage.setScene( myScene );

        //Add a menubar
        MenuBar mb = new MenuBar();
        //Add file menu stuff to it
        Menu fileMenu = new Menu( "_File" );
        MenuItem menuOpen = new MenuItem("Open");
        MenuItem menuExit = new MenuItem("Exit");
        menuExit.setAccelerator(KeyCombination.keyCombination("shortcut+q"));
        fileMenu.getItems().addAll(menuOpen, menuExit);
        mb.getMenus().add( fileMenu );

        //Add chart menu stuff to it
        Menu chartMenu = new Menu( "_Chart" );
        RadioMenuItem menuBarChart = new RadioMenuItem("Bar chart");
        RadioMenuItem menuAreaChart = new RadioMenuItem("Area chart");
        RadioMenuItem menuLineChart = new RadioMenuItem("Line chart");
        RadioMenuItem menuStackedChart = new RadioMenuItem("Stacked chart");
        ToggleGroup tg = new ToggleGroup();
        menuBarChart.setToggleGroup( tg );
        menuAreaChart.setToggleGroup( tg );
        menuLineChart.setToggleGroup( tg );
        menuStackedChart.setToggleGroup( tg );
        chartMenu.getItems().addAll(menuBarChart, menuAreaChart, menuLineChart, menuStackedChart);
        mb.getMenus().add( chartMenu );

        //Selecting the 'open' menu item opens a file selection dialog
        menuOpen.setOnAction( (ae) -> {
            FileChooser f = new FileChooser();
            f.setTitle( "Select a file" );
            File selectedFile = f.showOpenDialog( myStage );
            if( selectedFile != null ){
                //read the data from the file
                datalist = readData(selectedFile);
            }
        });

        //Selecting the Chart>BarChart menu item builds a bar chart
        menuBarChart.setOnAction( (ae) -> {
            if (null != datalist) {
                doBarChart(datalist);
            }
        });

        //Selecting the exit menu item exits the application
        menuExit.setOnAction( (ae)-> {
            Platform.exit();
        });
        //Add it to the top of the BorderPane
        rootNode.setTop(mb);

        //Draw the chart in the window
        rootNode.setCenter(myChart);

        //Draw the window
        myStage.show();
    }

    public List readData( File selectedFile) {
        //Now do some of that lambda stuff to get the file contents
        try {
            datalist =
                    Files.lines( Paths.get(selectedFile.toString())). //Stream of each line
                            map(l -> new DataModel(l)). // DataModel object for each line
                            collect(Collectors.toList()); //put the Datamodel objects in a list
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datalist;
    }

    public void doBarChart( List datalist){
        //Build the chart
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Languages");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Program Files");

        myChart = new BarChart<String, Number>(xAxis, yAxis);
        myChart.setTitle("My Chart");

        XYChart.Series<String, Number> spring = new XYChart.Series<>();
        XYChart.Series<String, Number> summer = new XYChart.Series<>();
        XYChart.Series<String, Number> fall = new XYChart.Series<>();
        XYChart.Series<String, Number> winter = new XYChart.Series<>();

        for( Object o : datalist) {
            DataModel d = (DataModel)o;
            if ("Spring" == d.getQuarter()) {
                spring.setName(d.getQuarter());
                spring.getData().add(new XYChart.Data<String, Number>(d.getLanguage(), d.getCount()));
            } else if ("Summer" == d.getQuarter()) {
                summer.setName(d.getQuarter());
                summer.getData().add(new XYChart.Data<String, Number>(d.getLanguage(), d.getCount()));
            } else if ("Fall" == d.getQuarter()) {
                fall.setName(d.getQuarter());
                fall.getData().add(new XYChart.Data<String, Number>(d.getLanguage(), d.getCount()));
            } else {
                winter.setName(d.getQuarter());
                winter.getData().add(new XYChart.Data<>(d.getLanguage(), d.getCount()));
            }
        }

        myChart.getData().add(spring);
        myChart.getData().add(summer);
        myChart.getData().add(fall);
        myChart.getData().add(winter);
        myChart.setAnimated(true);
    }

}
