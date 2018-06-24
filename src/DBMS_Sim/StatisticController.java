package DBMS_Sim;

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

            System.out.println("Acabaron");
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
        ObservableList<PieChart.Data> piecharData = FXCollections.observableArrayList(
                new PieChart.Data("DDL",10),
                new PieChart.Data("SELECT",29),
                new PieChart.Data("JOIN",30),
                new PieChart.Data("UPDATE",100));
        pcClientAdmin.setData(piecharData);
    }

    public void setIterationNumber(String text) {
        iteracion.setText(text);
    }

    public void setAppController(ApplicationController applicationController){
        this.appController = applicationController;
    }

    // We know that the matrix is ​​6x5 size
    /*
        0                   1           2           3           4
    0   Average lifetime    Discarded   Timeout     Total
    1   avgDDL              avgSELECT   avgJOIN     avgUPDATE
    2   avgDDL              avgSELECT   avgJOIN     avgUPDATE   avgQueueLength
    3   avgDDL              avgSELECT   avgJOIN     avgUPDATE   avgQueueLength
    4   avgDDL              avgSELECT   avgJOIN     avgUPDATE   avgQueueLength
    5   avgDDL              avgSELECT   avgJOIN     avgUPDATE   avgQueueLength

     */
    public void fillScreen(double [][] matriz){
        //General Pane
        lblgenerallifetime.setText(""+ matriz[0][0]);
        lblgeneralDiscarded.setText(""+matriz[0][1]);
        lblTimeOut.setText(""+ matriz[0][2]);

        //Client Admin Pane


        //Process Admin Pane

        //Query Module Pane


        //Transaction Module Pane

        //Execution Module Pane
    }
}
