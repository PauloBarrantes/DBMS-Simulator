
    package DBMS_Sim;

    import DBMS_Sim.SourceCode.Simulator;
    import com.jfoenix.controls.*;
    import javafx.application.Application;
    import javafx.concurrent.Service;
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
    import javafx.concurrent.Task;

    import java.io.IOException;
    import java.net.URL;
    import java.util.Random;
    import java.util.ResourceBundle;

public class ApplicationController implements Initializable {

    private Simulator simulator;
    private int numberOfSimulation;

    private Random rnd = new Random(System.currentTimeMillis());
    @FXML NormalModeController normalModeController;
    @FXML StatisticController statisticController;


    // UI Variables - Home

    @FXML StackPane stackPane1;
    @FXML JFXButton btnIniciar;
    @FXML JFXToggleButton mode;
    @FXML JFXTextField txt_ntimes,txt_time,k,p,n,m,t;

    private int nTimes, runTime, kConnections, pProcesses, nProcesses, mProcesses, timeout;
    private Service<Void> backgroundThread;
    private boolean validator;
    private long modeSim;

    public void run(String[] args){
        Application.launch(Initializer.class,args);
    }

    /**
     * Controlador del butón start, hacemos validaciones
     * Al ser el título obligatorio, si es nulo o vacío se lanzará
     * una excepción.
     *
     * @param event
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
            numberOfSimulation=1;
            //We store in the variables the values ​​that the user placed in the text-fields
            nTimes = Integer.parseInt(txt_ntimes.getText());
            runTime = Integer.parseInt(txt_time.getText());
            kConnections = Integer.parseInt(k.getText());
            timeout = Integer.parseInt(t.getText());
            mProcesses = Integer.parseInt(m.getText());
            pProcesses = Integer.parseInt( p.getText());
            nProcesses = Integer.parseInt(n.getText());
            //Cargamos la vista de normal mode

            normalModeScene(event);
            if(!mode.isSelected()){
                modeSim=1;
                runASimulation(event);
            }else{
                modeSim=1000;
                runASimulation(event);
            }

        }else{
            JFXDialogLayout content = new JFXDialogLayout();
            JFXDialog dialog1 = new JFXDialog(stackPane1,content , JFXDialog.DialogTransition.CENTER,false);

            Label header = new Label("Warning");
            header.setTextFill(Color.RED);
            header.setFont(Font.font(20));
            content.setHeading(header);
            content.setBody(new Text("Missing Hace falta un espacio de llenar o escribió un paramétro \n"+
                    "al igual que Clarita inválido" +
                    " para así poder iniciar con la simulación." ));
            JFXButton button = new JFXButton("Okey");
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


    public void runASimulation(ActionEvent event) throws InterruptedException {

        simulator = new Simulator(kConnections, timeout, nProcesses, pProcesses, mProcesses);
        simulator.setRunningTime((double) runTime);
        simulator.appendInitialEvent();
        normalModeController.setNumberOfSimulation(numberOfSimulation);
        final Task<Void> task = new Task<Void>() {
            final double runningTime = runTime;
            double previousClock;
            @Override
            protected Void call() throws Exception {
                double data [] = new double[7];
                data [0] = 0;
                previousClock =0;
                while(data[0] <= runTime){
                    data = simulator.iterateSimulation();
                    updateProgress(data[0], runTime);
                    normalModeController.refreshScreen(data[0],(int)data[1],(int)data[2],(int)data[3], (int)data[4],(int)data[5],(int)data[6]);

                    if(previousClock != data[0]){
                        Thread.sleep(modeSim);
                    }

                    previousClock = data[0];

                }
                //simulator.getSimulationStatistics();
                normalModeController.stats.setVisible(true);
                numberOfSimulation++;
                return null;
            }
        };

        normalModeController.pbProgress.progressProperty().bind(
                task.progressProperty()
        );


        final Thread thread = new Thread(task, "task-thread");
        thread.setDaemon(true);
        thread.start();
    }

    /**
     *  Valida todos los text fields de la ventana de inicio, se asegura que haya algo y que sean números.
     *
     *
     * @param textField Recibe como parámetro el textfield ha validar.
     */
    private void validate(JFXTextField textField) {

        if (textField.getText().isEmpty()) {
            textField.setUnFocusColor(Color.rgb(244, 101, 66));
            validator = false;
        } else {
            if (!textField.getText().matches("[0-9]*")) {
                validator = false;
                textField.setUnFocusColor(Color.rgb(244, 101, 66));
            }
        }

    }
    /**
     * Despliega el scene en pantalla del normal mode, además guardamos el controller de esa escena.
     *
     * @param event
     */
    public void normalModeScene(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/simulationRunningNormalMode.fxml"));
            Parent root = (Parent) loader.load();
            normalModeController = loader.getController();
            normalModeController.setAppController(this);
            Scene normalMode = new Scene(root);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.hide();
            appStage.setScene(normalMode);
            appStage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void homeScene(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/home.fxml"));
            Parent root = (Parent) loader.load();
            Scene normalMode = new Scene(root);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.hide();
            appStage.setScene(normalMode);
            appStage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }





    @Override
    public void initialize(URL arg1, ResourceBundle arg2){}

    public int getNumberOfIterations() {
        return nTimes;
    }
    public int getNumberOfSimulation(){
        return numberOfSimulation;
    }
}

