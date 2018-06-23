package DBMS_Sim;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class NormalModeController  {
    @FXML
    public JFXProgressBar pbProgress ;

    @FXML
    public JFXButton stats;
    @FXML
    private Label lblClock1, lblPAM ,lblQPM,lblTASM,lblEM,lblDiscarded , lblTimeout;

    public void initialize(URL arg1, ResourceBundle arg2){
    }

    @FXML
    void statistics(ActionEvent event){

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

}
