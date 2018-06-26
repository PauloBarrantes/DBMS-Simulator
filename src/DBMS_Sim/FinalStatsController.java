
package DBMS_Sim;

        import DBMS_Sim.SourceCode.SimulationStatistics;
        import com.jfoenix.controls.JFXButton;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.chart.PieChart;
        import javafx.scene.control.Label;
        import javafx.scene.layout.Pane;

        import java.net.URL;
        import java.util.ResourceBundle;

public class FinalStatsController implements Initializable {

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
    @FXML
    private Label lblporcentaje;
    @FXML
    private Label lblconfidence;


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

    @FXML
    private ApplicationController appController;
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
        appController.homeScene(event);

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



    public void setAppController(ApplicationController applicationController){
        this.appController = applicationController;
    }
    public void fillScreen(SimulationStatistics statistics){
        //General Pane
        String formattedDouble = String.format("%.02f", statistics.getAccumulatedConnectionTime());

        lblgenerallifetime.setText(formattedDouble);
        double avgDiscardedConnections = statistics.getAccumulatedDiscardedConnections()/(double)appController.getNumberOfIterations();
        lblgeneralDiscarded.setText(""+avgDiscardedConnections);
        double avgTimeOutConnection = statistics.getTimeoutConnections()/(double)appController.getNumberOfIterations();
        lblTimeOut.setText(""+ avgTimeOutConnection);

        lblporcentaje.setText(""+ statistics.);
        lblconfidence.setText("[ "+ statistics.getLowerLimit() + ", " + statistics.getUpperLimit()+" ]");

        //Client Admin Pane
        double ddl, select, join, update;
        select = statistics.getAccumulatedQueriesWaitTimeInModule()[0][0];
        update = statistics.getAccumulatedQueriesWaitTimeInModule()[0][1];
        join = statistics.getAccumulatedQueriesWaitTimeInModule()[0][2];
        ddl = statistics.getAccumulatedQueriesWaitTimeInModule()[0][3];

        formattedDouble = String.format("%.02f", ddl);
        lbl1DDL.setText(formattedDouble);
        formattedDouble = String.format("%.02f", select);
        lbl1SELECT.setText(formattedDouble);
        formattedDouble = String.format("%.02f", join);
        lbl1JOIN.setText(formattedDouble);
        formattedDouble = String.format("%.02f", update);
        lbl1UPDATE.setText(formattedDouble);
        ObservableList<PieChart.Data> piechartData = FXCollections.observableArrayList(
                new PieChart.Data("DDL",ddl),
                new PieChart.Data("SELECT",select),
                new PieChart.Data("JOIN",join),
                new PieChart.Data("UPDATE",update));
        pcClientAdmin.setData(piechartData);

        //Process Admin Pane

        select = statistics.getAccumulatedQueriesWaitTimeInModule()[1][0];
        update = statistics.getAccumulatedQueriesWaitTimeInModule()[1][1];
        join = statistics.getAccumulatedQueriesWaitTimeInModule()[1][2];
        ddl = statistics.getAccumulatedQueriesWaitTimeInModule()[1][3];

        double queue = statistics.getAccumulatedModuleQueueLength()[1];
        formattedDouble = String.format("%.02f", ddl);
        lbl2DDL.setText(formattedDouble);
        formattedDouble = String.format("%.02f", select);
        lbl2SELECT.setText(formattedDouble);
        formattedDouble = String.format("%.02f", join);
        lbl2JOIN.setText(formattedDouble);
        formattedDouble = String.format("%.02f", update);
        lbl2UPDATE.setText(formattedDouble);
        formattedDouble = String.format("%.02f", queue);

        lbl2QUEUE.setText(formattedDouble);

        piechartData = FXCollections.observableArrayList(
                new PieChart.Data("DDL",ddl),
                new PieChart.Data("SELECT",select),
                new PieChart.Data("JOIN",join),
                new PieChart.Data("UPDATE",update));
        pcProcessAdmin.setData(piechartData);

        //Query Module Pane
        select = statistics.getAccumulatedQueriesWaitTimeInModule()[2][0];
        update = statistics.getAccumulatedQueriesWaitTimeInModule()[2][1];
        join = statistics.getAccumulatedQueriesWaitTimeInModule()[2][2];
        ddl = statistics.getAccumulatedQueriesWaitTimeInModule()[2][3];

        queue = statistics.getAccumulatedModuleQueueLength()[2];
        formattedDouble = String.format("%.02f", ddl);
        lbl3DDL.setText(formattedDouble);
        formattedDouble = String.format("%.02f", select);
        lbl3SELECT.setText(formattedDouble);
        formattedDouble = String.format("%.02f", join);
        lbl3JOIN.setText(formattedDouble);
        formattedDouble = String.format("%.02f", update);
        lbl3UPDATE.setText(formattedDouble);
        formattedDouble = String.format("%.02f", queue);

        lbl3QUEUE.setText(formattedDouble);

        piechartData = FXCollections.observableArrayList(
                new PieChart.Data("DDL",ddl),
                new PieChart.Data("SELECT",select),
                new PieChart.Data("JOIN",join),
                new PieChart.Data("UPDATE",update));
        pcQueryModule.setData(piechartData);

        //Transaction Module Pane
        select = statistics.getAccumulatedQueriesWaitTimeInModule()[3][0];
        update = statistics.getAccumulatedQueriesWaitTimeInModule()[3][1];
        join = statistics.getAccumulatedQueriesWaitTimeInModule()[3][2];
        ddl = statistics.getAccumulatedQueriesWaitTimeInModule()[3][3];

        queue = statistics.getAccumulatedModuleQueueLength()[3];
        formattedDouble = String.format("%.02f", ddl);
        lbl4DDL.setText(formattedDouble);
        formattedDouble = String.format("%.02f", select);
        lbl4SELECT.setText(formattedDouble);
        formattedDouble = String.format("%.02f", join);
        lbl4JOIN.setText(formattedDouble);
        formattedDouble = String.format("%.02f", update);
        lbl4UPDATE.setText(formattedDouble);
        formattedDouble = String.format("%.02f", queue);

        lbl4QUEUE.setText(formattedDouble);

        piechartData = FXCollections.observableArrayList(
                new PieChart.Data("DDL",ddl),
                new PieChart.Data("SELECT",select),
                new PieChart.Data("JOIN",join),
                new PieChart.Data("UPDATE",update));
        pcTransaction.setData(piechartData);
        //Execution Module Pane
        select = statistics.getAccumulatedQueriesWaitTimeInModule()[4][0];
        update = statistics.getAccumulatedQueriesWaitTimeInModule()[4][1];
        join = statistics.getAccumulatedQueriesWaitTimeInModule()[4][2];
        ddl = statistics.getAccumulatedQueriesWaitTimeInModule()[4][3];

        queue = statistics.getAccumulatedModuleQueueLength()[4];
        formattedDouble = String.format("%.02f", ddl);
        lbl5DDL.setText(formattedDouble);
        formattedDouble = String.format("%.02f", select);
        lbl5SELECT.setText(formattedDouble);
        formattedDouble = String.format("%.02f", join);
        lbl5JOIN.setText(formattedDouble);
        formattedDouble = String.format("%.02f", update);
        lbl5UPDATE.setText(formattedDouble);
        formattedDouble = String.format("%.02f", queue);

        lbl5QUEUE.setText(formattedDouble);

        piechartData = FXCollections.observableArrayList(
                new PieChart.Data("DDL",ddl),
                new PieChart.Data("SELECT",select),
                new PieChart.Data("JOIN",join),
                new PieChart.Data("UPDATE",update));
        pcExecution.setData(piechartData);
    }
}
