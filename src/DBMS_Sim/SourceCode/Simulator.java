package DBMS_Sim.SourceCode;

import DBMS_Sim.Controller;

import java.util.PriorityQueue;

/**
 * Esta clase consiste en el cuerpo de la simulación del
 * DBMS, donde simulamos el paso de las consultas por todos los módulos.
 * Guardando los datos estadísticos para generar mejores resultados a la hora
 * de optimizar el sistema.
 *
 * @author  Paulo Barrantes
 * @author  André Flasterstein
 * @author  Fabián Álvarez
 */
public class Simulator {
    public final static int NUMSTATEMENTS = 4;

    private ClientAdminModule clientAdminModule;
    private double clock;
    private ExecutionModule executionModule;
    private ProcessAdminModule processAdminModule;
    private QueryGenerator queryGenerator;
    private QueryProcessingModule queryProcessingModule;
    private int[] queueLengths;
    private int[] queueLengthsCounted;
    private double runningTime;
    private StatisticsGenerator statisticsGenerator;
    private PriorityQueue<Event> tableOfEvents;
    private double timeInTheSystem;
    private TransactionAndStorageModule transactionAndStorageModule;


    public Simulator(double clock) {

    }

    public Simulator(int k, double t, int n, int p, int m){

    }

    public void simulate(){

    }

    public void setClock(double clock) {
        this.clock = clock;
    }


    public double getClock() {
        return clock;
    }

}




