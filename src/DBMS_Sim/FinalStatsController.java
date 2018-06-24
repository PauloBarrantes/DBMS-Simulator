
package DBMS_Sim;

        import com.jfoenix.controls.JFXButton;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.chart.PieChart;
        import javafx.scene.layout.Pane;
        import javafx.scene.paint.Color;

        import java.net.URL;
        import java.util.Observable;
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
    public void fillScreen(){
        //General Pane


        //Client Admin Pane


        //Process Admin Pane

        //Query Module Pane


        //Transaction Module Pane

        //Execution Module Pane
    }
}
