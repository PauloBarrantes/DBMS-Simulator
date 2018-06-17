package DBMS_Sim;

import DBMS_Sim.SourceCode.Simulator;
import com.jfoenix.controls.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements Initializable {
    // UI Variables - Home
    
    @FXML StackPane stackPane1;
    @FXML JFXButton btnIniciar;
    @FXML JFXToggleButton mode;
    @FXML JFXToggleButton graphic;
    @FXML JFXTextField txt_ntimes,txt_time,k,p,n,m,t;
    @FXML JFXTextField number;

    // UI Variables - Normal Mode
    @FXML JFXProgressBar pbProgress;
    @FXML Label lblClock;
    //UI Variables - Slow Mode

    //UI Variables -
    //UI
    int ntimes, time, kCon, pProcess, nProcess, mProcess, timeout;

    private boolean validator;

    private Simulator simulator;
    private Initializer initializer;

    public Controller(){
        simulator = new Simulator();
        initializer = new Initializer();
    }

    public void run(String[] args){
        Application.launch(Initializer.class,args);
    }

    /**
     * Controlador de la
     *
     *
     *
     * @param
     */
    @FXML
    void modeSlow(ActionEvent event){
        if(mode.isSelected() == false){
            graphic.setSelected(false);
            graphic.setDisable(true);
        }else {
            System.out.println("GG");
            graphic.setDisable(false);
        }
    }
    /**
     * Controlador del butón start, hacemos validaciones
     * Al ser el título obligatorio, si es nulo o vacío se lanzará
     * una excepción.
     *
     * @param
     */
    @FXML
    void start(ActionEvent event) throws IOException, InterruptedException {

        validator = true;
        validate(txt_time);
        validate(txt_ntimes);
        validate(k);
        validate(t);
        validate(m);
        validate(p);
        validate(n);
        if(validator){
            Parent home_parent = FXMLLoader.load(getClass().getResource("Views/simulationRunningNormalMode.fxml"));

            //We store in the variables the values ​​that the user placed in the text-fields
            String temporal = txt_ntimes.getText();
            ntimes = Integer.parseInt(temporal);
            temporal = txt_time.getText();
            time = Integer.parseInt(temporal);
            temporal = k.getText();
            kCon = Integer.parseInt(temporal);
            temporal = t.getText();
            timeout = Integer.parseInt(temporal);
            temporal = m.getText();
            mProcess = Integer.parseInt(temporal);
            temporal = p.getText();
            pProcess = Integer.parseInt(temporal);
            temporal = n.getText();
            nProcess = Integer.parseInt(temporal);


            Scene inicio = new Scene(home_parent);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.hide();
            appStage.setScene(inicio);
            appStage.show();
            controlSimulationNormalMode();
            lblClock.setText("GG");

        }else{
            JFXDialogLayout content = new JFXDialogLayout();
            JFXDialog dialog1 = new JFXDialog(stackPane1,content , JFXDialog.DialogTransition.CENTER,false);

            Label header = new Label("Warning");
            header.setTextFill(Color.RED);
            header.setFont(Font.font(20));
            content.setHeading(header);
            //NOTE FOR FLASTERSTEIN-> CHANGE THIS PLEASE
            content.setBody(new Text("Hace falta un espacio de llenar o escribió un paramétro \n"+
                    "al igual que Clarita inválido" +
                    " para así poder iniciar con la simulación." ));
            JFXButton button = new JFXButton("Okay");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialog1.close();
                }
            });
            content.setActions(button);

            dialog1.show();
        }


    }


    @Override
    public void initialize(URL arg1, ResourceBundle arg2){
    }

    private boolean validate(JFXTextField textField){

        if(textField.getText().isEmpty()){
            textField.setUnFocusColor(Color.rgb(244,101,66));
            validator = false;
        }else{
            if(!textField.getText().matches("[0-9]*")){
                validator = false;
                textField.setUnFocusColor(Color.rgb(244,101,66));

            }
        }

        return validator;

    }
    private void controlSimulationNormalMode(){

        //simulator = new Simulator(ntimes, time, kCon, timeout, nProcess, pProcess, mProcess);
    }
}
