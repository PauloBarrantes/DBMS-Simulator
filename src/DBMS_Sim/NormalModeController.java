package DBMS_Sim;

import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class NormalModeController  {
    @FXML
    JFXProgressBar pbProgress ;
    @FXML
    JFXTextField Clock1;
    @FXML
    Label lblClock1,  lblMac, lblPAM ,lblQPM,lblTASM,lblEM,lblDiscarded;

    private double timeRunning;
    public void initialize(URL arg1, ResourceBundle arg2){
    }

    public void setTimeRunning(int timeRunning){
        this.timeRunning = (double)timeRunning;
    }



    public void refreshScreen(double clock, int MAC, int PAM, int QPM, int TASM, int EM, int discardedConnections){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String formattedClock = String.format("%.02f", clock);

                lblClock1.setText(formattedClock);
                lblMac.setText(""+ MAC);
                lblPAM.setText(""+ PAM);
                lblQPM.setText(""+ QPM);
                lblTASM.setText(""+ TASM);
                lblEM.setText(""+ EM);
                lblDiscarded.setText(""+ discardedConnections);

            }
        });

    }

}
