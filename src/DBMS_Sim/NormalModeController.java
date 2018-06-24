package DBMS_Sim;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NormalModeController  implements Initializable {
    @FXML StatisticController statisticController;
    @FXML ApplicationController controller;
    @FXML
    public JFXProgressBar pbProgress ;

    @FXML
    public JFXButton stats;
    @FXML
    private Label lblClock1, lblPAM ,lblQPM,lblTASM,lblEM,lblDiscarded , lblTimeout;
    @FXML
    private Label lblNcorrida;
    public void initialize(URL arg1, ResourceBundle arg2){
    }
    public void setAppController(ApplicationController applicationController){
        this.controller = applicationController;
    }
    @FXML
    void statistics(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/Stats.fxml"));
            Parent root = (Parent) loader.load();
            statisticController = loader.getController();
            statisticController.setIterationNumber(lblNcorrida.getText());
            statisticController.setAppController(controller);
            Scene normalMode = new Scene(root);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.hide();
            appStage.setScene(normalMode);
            appStage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }

        statisticController.fillScreen();
    }



    public void refreshScreen(double clock, double PAM, double QPM, double TASM, double EM, double discardedConnections,double timeoutCons){
        Platform.runLater(() -> {
            String formattedClock = String.format("%.02f", clock);

            lblClock1.setText(formattedClock);
            lblPAM.setText(""+ (int)PAM);
            lblQPM.setText(""+ (int)QPM);
            lblTASM.setText(""+ (int)TASM);
            lblEM.setText(""+ (int)EM);
            lblDiscarded.setText(""+ (int)discardedConnections);
            lblTimeout.setText(""+ (int)timeoutCons);

        });

    }

    public void setNumberOfSimulation(int numberOfSimulation) {
        lblNcorrida.setText(numberOfSimulation+"");
    }
}
