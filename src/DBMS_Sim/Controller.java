package DBMS_Sim;

import DBMS_Sim.SourceCode.Simulator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements Initializable {
    // UI Variables
    @FXML
    StackPane stackPane1;
    @FXML
    JFXButton btnIniciar;
    @FXML
    JFXToggleButton mode;
    @FXML
    JFXToggleButton graphic;
    @FXML
    JFXTextField txt_ntimes,txt_time,k,p,n,m,t;
    @FXML
    JFXTextField number;

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
        Timer timer = new Timer();
        int counter = 0;

        TimerTask tarea = new TimerTask() {
            int counter = 0;
            @Override
            public void run() {
                if(counter > 5){
                    timer.cancel();
                }else {


                    number.setText("" + counter);

                    ++counter;
                }
            }
        };

        timer.schedule(tarea, 0, 1000);

/*
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
            Scene inicio = new Scene(home_parent);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.hide();
            appStage.setScene(inicio);
            appStage.show();
        }else{
            JFXDialogLayout content = new JFXDialogLayout();
            JFXDialog dialog1 = new JFXDialog(stackPane1,content , JFXDialog.DialogTransition.CENTER,false);

            Label header = new Label("Warning");
            header.setTextFill(Color.RED);
            header.setFont(Font.font(20));
            content.setHeading(header);
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
*/

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
}
