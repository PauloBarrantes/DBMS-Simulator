package DBMS_Sim.SourceCode;

import DBMS_Sim.Controller;

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

    private double clock;
    private double runningTime;
    private QueryGenerator queryGenerator;
    private ClientAdminModule clientAdminModule;
    private ProcessAdminModule processAdminModule;
    private QueryProcessingModule queryProcessingModule;
    private TransactionAndStorageModule transactionAndStorageModule;
    private ExecutionModule executionModule;
    private StatisticsGenerator statisticsGenerator;
    private int[] queueLengths;
    private int queueLengthsCounted;

    /**
     * Inserta un título en la clase descripción.
     * Al ser el título obligatorio, si es nulo o vacío se lanzará
     * una excepción.
     *
     * @param
     */
    public Simulator(double clock) {
        this.clock = clock;
    }

    /**
     *
     */
    public Simulator(int k, double t, int n, int p, int m){
        queryGenerator = new QueryGenerator();
        clientAdminModule = new ClientAdminModule(k);
        processAdminModule = new ProcessAdminModule();
        queryProcessingModule = new QueryProcessingModule(n);
        transactionAndStorageModule = new TransactionAndStorageModule(p);
        executionModule = new ExecutionModule(m);
        statisticsGenerator = new StatisticsGenerator();
    }


    /**
     * Inserta un título en la clase descripción.
     * Al ser el título obligatorio, si es nulo o vacío se lanzará
     * una excepción.
     *
     * @param

     */
    public void simulate(){

    }

    public void setClock(double clock) {
        this.clock = clock;
    }


    public double getClock() {
        return clock;
    }

}




