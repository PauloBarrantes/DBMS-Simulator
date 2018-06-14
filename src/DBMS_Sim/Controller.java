package DBMS_Sim;

import DBMS_Sim.Backend.Simulator;
import DBMS_Sim.Frontend.Initializer;
import javafx.application.Application;

public class Controller {
    private Simulator simulator;
    private Initializer initializer;

    public Controller(){
        simulator = new Simulator();
        initializer = new Initializer();
    }

    public void run(String[] args){
        Application.launch(Initializer.class,args);
    }
}
