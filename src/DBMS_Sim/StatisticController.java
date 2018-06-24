package DBMS_Sim;

import DBMS_Sim.SourceCode.SimulationStatistics;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StatisticController implements Initializable {

    @FXML
    private Pane pnGeneral;
    @FXML
    private Pane pnClientAdmin;
    @FXML
    private Pane pnProcessAdmin;
    @FXML
    private Pane pnQueryModule;
    @FXML
    private Pane pnTransaction;
    @FXML
    private Pane pnExecution;
    @FXML
    private JFXButton btnGeneral;
    @FXML
    private JFXButton btnClientAdmin;
    @FXML
    private JFXButton btnProcessAdmin;
    @FXML
    private JFXButton btnQueryModule;
    @FXML
    private JFXButton btnTransaction;
    @FXML
    private JFXButton btnExecution;

    //------------------Statistical Variables --------------------
    @FXML
    private PieChart pcClientAdmin;
    @FXML
    private PieChart pcExecution;
    @FXML
    private PieChart pcTransaction;
    @FXML
    private PieChart pcQueryModule;
    @FXML
    private PieChart pcProcessAdmin;
    //-- General
    @FXML
    private Label lblgenerallifetime;
    @FXML
    private Label lblgeneralDiscarded;
    @FXML
    private Label lblTimeOut;


    //-- Client Admin

    @FXML
    private Label lbl1DDL;
    @FXML
    private Label lbl1SELECT;
    @FXML
    private Label lbl1JOIN;
    @FXML
    private Label lbl1UPDATE;


    //-- Process Admin

    @FXML
    private Label lbl2DDL;
    @FXML
    private Label lbl2SELECT;
    @FXML
    private Label lbl2JOIN;
    @FXML
    private Label lbl2UPDATE;
    @FXML
    private Label lbl2QUEUE;

    //-- Query Module
    @FXML
    private Label lbl3DDL;
    @FXML
    private Label lbl3SELECT;
    @FXML
    private Label lbl3JOIN;
    @FXML
    private Label lbl3UPDATE;
    @FXML
    private Label lbl3QUEUE;

    //-- Execution Module
    @FXML
    private Label lbl4DDL;
    @FXML
    private Label lbl4SELECT;
    @FXML
    private Label lbl4JOIN;
    @FXML
    private Label lbl4UPDATE;
    @FXML
    private Label lbl4QUEUE;


    //-- Transaction Module
    @FXML
    private Label lbl5DDL;
    @FXML
    private Label lbl5SELECT;
    @FXML
    private Label lbl5JOIN;
    @FXML
    private Label lbl5UPDATE;
    @FXML
    private Label lbl5QUEUE;

    //------------------End of Statistical Variables --------------

    @FXML private Label iteracion;
    @FXML
    private ApplicationController appController;
    @FXML
    private FinalStatsController finalStats;
    @FXML
    void general(ActionEvent event){
        changeColorsAndPanes();
        btnGeneral.setStyle("-fx-background-color : #CC412A;");
        pnGeneral.setVisible(true);
    }
    @FXML
    void clientAdmin(ActionEvent event){
        changeColorsAndPanes();
        btnClientAdmin.setStyle("-fx-background-color : #CC412A;");
        pnClientAdmin.setVisible(true);
    }
    @FXML
    void processAdmin(ActionEvent event){

        changeColorsAndPanes();
        btnProcessAdmin.setStyle("-fx-background-color : #CC412A;");
        pnProcessAdmin.setVisible(true);

    }
    @FXML
    void queryModule(ActionEvent event){
        changeColorsAndPanes();

        btnQueryModule.setStyle("-fx-background-color : #CC412A;");
        pnQueryModule.setVisible(true);

    }
    @FXML
    void transaction(ActionEvent event){
        changeColorsAndPanes();

        btnTransaction.setStyle("-fx-background-color : #CC412A;");
        pnTransaction.setVisible(true);

    }
    @FXML
    void execution(ActionEvent event){
        changeColorsAndPanes();

        btnExecution.setStyle("-fx-background-color : #CC412A;");
        pnExecution.setVisible(true);

    }
    @FXML
    void next(ActionEvent event) throws InterruptedException {
        if( appController.getNumberOfSimulation() <= appController.getNumberOfIterations() ){
            appController.normalModeScene(event);
            appController.runASimulation(event);
        }else{
            finalStatsScene(event);
            finalStats.setAppController(appController);

            SimulationStatistics finalStatistics = appController.getSimulator().finalStatistics();
            finalStats.fillScreen();
        }

    }

    public void finalStatsScene(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/FinalStats.fxml"));
            Parent root = (Parent) loader.load();
            finalStats = loader.getController();
            Scene normalMode = new Scene(root);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.hide();
            appStage.setScene(normalMode);
            appStage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    void changeColorsAndPanes(){
        btnGeneral.setStyle("-fx-background-color : #F46542;");
        btnClientAdmin.setStyle("-fx-background-color : #F46542;");
        btnProcessAdmin.setStyle("-fx-background-color : #F46542;");
        btnQueryModule.setStyle("-fx-background-color : #F46542;");
        btnTransaction.setStyle("-fx-background-color : #F46542;");
        btnExecution.setStyle("-fx-background-color : #F46542;");

        pnGeneral.setVisible(false);
        pnClientAdmin.setVisible(false);
        pnProcessAdmin.setVisible(false);
        pnQueryModule.setVisible(false);
        pnTransaction.setVisible(false);
        pnExecution.setVisible(false);
    }
    @FXML
    @Override
    public void initialize(URL arg1, ResourceBundle arg2){
        changeColorsAndPanes();
        btnGeneral.setStyle("-fx-background-color : #CC412A;");
        pnGeneral.setVisible(true);

    }

    public void setIterationNumber(String text) {
        iteracion.setText(text);
    }

    public void setAppController(ApplicationController applicationController){
        this.appController = applicationController;
    }


    public void fillScreen(SimulationStatistics statistics){
        //General Pane
        String formattedLifetime = String.format("%.02f", statistics.getAcumulatedConnectionTime());

        lblgenerallifetime.setText(formattedLifetime);
        lblgeneralDiscarded.setText(""+statistics.getAcumulatedDiscardedConnections());
        lblTimeOut.setText(""+ statistics.getTimeoutConnections());

        //Client Admin Pane
        double ddl, select, join, update;
        select = statistics.getAcumulatedQueriesWaitTimeInModule()[0][0];
        update = statistics.getAcumulatedQueriesWaitTimeInModule()[0][1];
        join = statistics.getAcumulatedQueriesWaitTimeInModule()[0][2];
        ddl = statistics.getAcumulatedQueriesWaitTimeInModule()[0][3];

        formattedLifetime = String.format("%.02f", ddl);
        lbl1DDL.setText(formattedLifetime);
        formattedLifetime = String.format("%.02f", select);
        lbl1SELECT.setText(formattedLifetime);
        formattedLifetime = String.format("%.02f", join);
        lbl1JOIN.setText(formattedLifetime);
        formattedLifetime = String.format("%.02f", update);
        lbl1UPDATE.setText(formattedLifetime);
        ObservableList<PieChart.Data> piechartData = FXCollections.observableArrayList(
                new PieChart.Data("DDL",ddl),
                new PieChart.Data("SELECT",select),
                new PieChart.Data("JOIN",join),
                new PieChart.Data("UPDATE",update));
        pcClientAdmin.setData(piechartData);

        //Process Admin Pane

        select = statistics.getAcumulatedQueriesWaitTimeInModule()[1][0];
        update = statistics.getAcumulatedQueriesWaitTimeInModule()[1][1];
        join = statistics.getAcumulatedQueriesWaitTimeInModule()[1][2];
        ddl = statistics.getAcumulatedQueriesWaitTimeInModule()[1][3];

        double queue = statistics.getAcumulatedModuleQueueLength()[1];
        formattedLifetime = String.format("%.02f", ddl);
        lbl2DDL.setText(formattedLifetime);
        formattedLifetime = String.format("%.02f", select);
        lbl2SELECT.setText(formattedLifetime);
        formattedLifetime = String.format("%.02f", join);
        lbl2JOIN.setText(formattedLifetime);
        formattedLifetime = String.format("%.02f", update);
        lbl2UPDATE.setText(formattedLifetime);
        lbl2QUEUE.setText(""+ queue);

        piechartData = FXCollections.observableArrayList(
                new PieChart.Data("DDL",ddl),
                new PieChart.Data("SELECT",select),
                new PieChart.Data("JOIN",join),
                new PieChart.Data("UPDATE",update));
        pcProcessAdmin.setData(piechartData);

        //Query Module Pane
        select = statistics.getAcumulatedQueriesWaitTimeInModule()[2][0];
        update = statistics.getAcumulatedQueriesWaitTimeInModule()[2][1];
        join = statistics.getAcumulatedQueriesWaitTimeInModule()[2][2];
        ddl = statistics.getAcumulatedQueriesWaitTimeInModule()[2][3];

        queue = statistics.getAcumulatedModuleQueueLength()[2];
        formattedLifetime = String.format("%.02f", ddl);
        lbl3DDL.setText(formattedLifetime);
        formattedLifetime = String.format("%.02f", select);
        lbl3SELECT.setText(formattedLifetime);
        formattedLifetime = String.format("%.02f", join);
        lbl3JOIN.setText(formattedLifetime);
        formattedLifetime = String.format("%.02f", update);
        lbl3UPDATE.setText(formattedLifetime);
        lbl3QUEUE.setText(""+ queue);

        piechartData = FXCollections.observableArrayList(
                new PieChart.Data("DDL",ddl),
                new PieChart.Data("SELECT",select),
                new PieChart.Data("JOIN",join),
                new PieChart.Data("UPDATE",update));
        pcQueryModule.setData(piechartData);

        //Transaction Module Pane
        select = statistics.getAcumulatedQueriesWaitTimeInModule()[3][0];
        update = statistics.getAcumulatedQueriesWaitTimeInModule()[3][1];
        join = statistics.getAcumulatedQueriesWaitTimeInModule()[3][2];
        ddl = statistics.getAcumulatedQueriesWaitTimeInModule()[3][3];

        queue = statistics.getAcumulatedModuleQueueLength()[3];
        formattedLifetime = String.format("%.02f", ddl);
        lbl4DDL.setText(formattedLifetime);
        formattedLifetime = String.format("%.02f", select);
        lbl4SELECT.setText(formattedLifetime);
        formattedLifetime = String.format("%.02f", join);
        lbl4JOIN.setText(formattedLifetime);
        formattedLifetime = String.format("%.02f", update);
        lbl4UPDATE.setText(formattedLifetime);
        lbl4QUEUE.setText(""+ queue);

        piechartData = FXCollections.observableArrayList(
                new PieChart.Data("DDL",ddl),
                new PieChart.Data("SELECT",select),
                new PieChart.Data("JOIN",join),
                new PieChart.Data("UPDATE",update));
        pcTransaction.setData(piechartData);
        //Execution Module Pane
        select = statistics.getAcumulatedQueriesWaitTimeInModule()[4][0];
        update = statistics.getAcumulatedQueriesWaitTimeInModule()[4][1];
        join = statistics.getAcumulatedQueriesWaitTimeInModule()[4][2];
        ddl = statistics.getAcumulatedQueriesWaitTimeInModule()[4][3];

        queue = statistics.getAcumulatedModuleQueueLength()[4];
        formattedLifetime = String.format("%.02f", ddl);
        lbl5DDL.setText(formattedLifetime);
        formattedLifetime = String.format("%.02f", select);
        lbl5SELECT.setText(formattedLifetime);
        formattedLifetime = String.format("%.02f", join);
        lbl5JOIN.setText(formattedLifetime);
        formattedLifetime = String.format("%.02f", update);
        lbl5UPDATE.setText(formattedLifetime);
        lbl5QUEUE.setText(""+ queue);

        piechartData = FXCollections.observableArrayList(
                new PieChart.Data("DDL",ddl),
                new PieChart.Data("SELECT",select),
                new PieChart.Data("JOIN",join),
                new PieChart.Data("UPDATE",update));
        pcExecution.setData(piechartData);
    }
}
