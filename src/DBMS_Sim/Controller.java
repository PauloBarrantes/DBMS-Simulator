package DBMS_Sim;


import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    int nProcess;
    Stage stage;
    @FXML
    JFXRippler rippler;
    @FXML
    StackPane sta1;
    @FXML
    AnchorPane root;
    @FXML
    JFXButton btnIniciar;
    @FXML
    JFXToggleButton mode;
    @FXML
    JFXToggleButton graphic;
    @FXML
    JFXTextField txt_ntimes,txt_time,k,p,n,m,t;
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
    @FXML
    void start(ActionEvent event){

        JFXDialogLayout content = new JFXDialogLayout();
        JFXDialog dialog1 = new JFXDialog(sta1,content , JFXDialog.DialogTransition.CENTER,false);

        Label header = new Label("Warning");
        header.setTextFill(Color.RED);
        header.setFont(Font.font(20));
        content.setHeading(header);
        content.setBody(new Text("Hace falta un espacio de llenar, completelo por favor,\n" +
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




        boolean validator = true;
        if(txt_ntimes.getText().isEmpty()){
            txt_ntimes.setUnFocusColor(Color.rgb(244,101,66));
            System.out.println("Es necesario colocar la cantidad de veces que desea realizar la simulación");
        }else{  //Validamos que sea un número entero positivo
            if(!txt_time.getText().matches("[0-9]*")){
                System.out.println("Esto no es un número");
            }
        }
        if(txt_time.getText().isEmpty()){
            txt_time.setUnFocusColor(Color.rgb(244,101,66));
            System.out.println("Es necesario colocar el tiempo que desea que dure la simulación");
        }else{
            if(!txt_time.getText().matches("[0-9]*")){
                System.out.println("Esto es un número");
            }
        }

/*
                if(validator){
                    StackPane rot = new StackPane();
                    Scene scene = new Scene(rot,200, 200);
                    stage.setScene(scene);
                    stage.show();
                }
*/


    }


    @Override
    public void initialize(URL arg1, ResourceBundle arg2){
    }
}
