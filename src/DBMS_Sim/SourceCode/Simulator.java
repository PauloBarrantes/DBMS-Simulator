package DBMS_Sim.SourceCode;

import DBMS_Sim.Controller;

import java.util.Comparator;
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
    private Comparator<Event> comparator = new Comparator<Event>() {
        @Override
        public int compare(Event event1, Event event2) {
            int cmp = 0;
            if(event1.getTime() < event2.getTime()){
                cmp = -1;
            }else{
                if(event1.getTime() > event2.getTime()){
                    cmp = 1;
                }
            }
            return cmp;
        }
    };
    // ---------------------------------------------------------------------------------------------
    // ----------------------------- Beginning of constructors section -----------------------------
    // ---------------------------------------------------------------------------------------------
    public Simulator(int k, double t, int n, int p, int m){

        tableOfEvents = new PriorityQueue<>(100, comparator);
        clientAdminModule =  new ClientAdminModule(k, t);
        processAdminModule = new ProcessAdminModule(1,t);
        queryProcessingModule =  new QueryProcessingModule(n,t);
        transactionAndStorageModule = new TransactionAndStorageModule(p,t);
        executionModule = new ExecutionModule(m,t);
        queryGenerator = new QueryGenerator();
    }


    // ---------------------------------------------------------------------------------------------
    // ------------------------------ End of the constructors section ------------------------------
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // ----------------------- Beginning of the setters and getters section -----------------------
    // ---------------------------------------------------------------------------------------------
    public void setRunningTime(double runningTime) {
        this.runningTime = runningTime;
    }

    public double getRunningTime() {
        return runningTime;
    }

    public void setClock(double clock) {
        this.clock = clock;
    }


    public double getClock() {
        return clock;
    }
    // ---------------------------------------------------------------------------------------------
    // ------------------------------ End of the constructors section ------------------------------
    // ---------------------------------------------------------------------------------------------


    // ---------------------------------------------------------------------------------------------
    // ------------------------------- Beginning of methods section -------------------------------
    // ---------------------------------------------------------------------------------------------



    public Simulator(double clock) {

    }



//    public void simulate(){
//
//
//        Event actualEvent;
//
//        while(runningTime <= clock){
//            actualEvent =  tableOfEvents.poll();
//            switch (actualEvent.getType()) {
//                case ArriveClientToModule:
//                    System.out.println("GG");
//                    break;
//                case ExitClientModule:
//                    System.out.println("GG");
//                    break;
//                case QueryArrival:
//                    System.out.println("GG");
//                    break;
//            }
//            assert tableOfEvents.peek() != null;
//            clock = tableOfEvents.peek().getTime();
//
//            }
//
//
//            //Movemos el reloj
//
//            assert tableOfEvents.peek() != null;
//            clock = tableOfEvents.peek().getTime();
//        }
//    }


    public void appendInitialEvent(){
        Query initialQuery = queryGenerator.generate(0);
        initialQuery.setSubmissionTime(0);
        Event initialArrive = new Event(EventType.ArriveClientToModule,0, initialQuery);
        tableOfEvents.add(initialArrive);
    }
    public void iterateSimulation(){
        Event actualEvent =  tableOfEvents.poll();
        switch (actualEvent.getType()) {
            case ArriveClientToModule:
                clientAdminModule.processArrival(actualEvent,tableOfEvents);
                System.out.println("Llego al Client Admin");
                break;
            case ArriveToProcessAdminModule:
                processAdminModule.processArrival(actualEvent,tableOfEvents);
                System.out.println("Creando Proceso");
                break;
            case ArriveToQueryProcessingModule:
                queryProcessingModule.processArrival(actualEvent, tableOfEvents, EventType.LexicalValidation);
                System.out.println("Procesando la consulta");

                break;
            case ArriveToTransactionModule:
                System.out.println("Transaction");
                break;
            case ArriveToExecutionModule:
                executionModule.processArrival(actualEvent, tableOfEvents, EventType.ExecuteQuery);
                System.out.println("GG");
                break;
            case ShowResult:
                clientAdminModule.showResult(actualEvent, tableOfEvents);
                System.out.println("Show Result");
                break;
            case ExitClientModule:
                clientAdminModule.processDeparture(actualEvent, tableOfEvents);
                System.out.println("salida del client module");
                break;
            case ExitProcessAdminModule:
                processAdminModule.processDeparture(actualEvent, tableOfEvents);
                System.out.println("Salidita del process moduls");
                break;
            case ExecuteQuery:
                executionModule.executeQuery(actualEvent,tableOfEvents);
                System.out.println("GG");
                break;
            case ExitTransactionModule:
                transactionAndStorageModule.processDeparture(actualEvent, tableOfEvents);
                System.out.println("GG");
                break;
            case ExitExecutionModule:
                executionModule.processDeparture(actualEvent, tableOfEvents);

                System.out.println("GG");
                break;
            case LexicalValidation:
                queryProcessingModule.lexicalValidation(actualEvent, tableOfEvents);
                break;
            case SintacticalValidation:
                queryProcessingModule.sintacticalValidation(actualEvent, tableOfEvents);
                break;
            case SemanticValidation:
                queryProcessingModule.semanticValidation(actualEvent, tableOfEvents);

                break;
            case PermissionVerification:
                queryProcessingModule.permissionVerification(actualEvent, tableOfEvents);

                break;
            case QueryOptimization:
                queryProcessingModule.queryOptimization(actualEvent, tableOfEvents);

                break;
        }
        assert tableOfEvents.peek() != null;
        clock = tableOfEvents.peek().getTime();

    }
    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------


}




