package DBMS_Sim.SourceCode;

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

    // ---------------------------------------------------------------------------------------------
    // ----------------------------- Beginning of constructors section -----------------------------
    // ---------------------------------------------------------------------------------------------
    public Simulator(int k, double t, int n, int p, int m){

        Comparator<Event> comparator = (event1, event2) -> {
            int cmp = 0;
            if (event1.getTime() < event2.getTime()) {
                cmp = -1;
            } else {
                if (event1.getTime() > event2.getTime()) {
                    cmp = 1;
                }
            }
            return cmp;
        };
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
    public static void main(String args[]){
        Simulator simulator = new Simulator(15,15,3,2,1);
        simulator.setRunningTime(150);
        simulator.simulate();
    }
    public void simulate(){
        appendInitialEvent();
        Event actualEvent;
        boolean queryTimeOut;
        while(clock <= runningTime){
            actualEvent = tableOfEvents.poll();
            //Revisamos todas las colas si hay consultas que ya se les paso el tiempo
            processAdminModule.checkQueue(actualEvent.getTime(),clientAdminModule);
            queryProcessingModule.checkQueue(actualEvent.getTime(),clientAdminModule);
            transactionAndStorageModule.checkQueue(actualEvent.getTime(), clientAdminModule);
            executionModule.checkQueue(actualEvent.getTime(), clientAdminModule);

            switch (actualEvent.getType()) {
                case ArriveClientToModule:
                    clientAdminModule.processArrival(actualEvent, tableOfEvents);
                    System.out.println("Llego al Client Admin");
                    break;
                case ArriveToProcessAdminModule:
                    queryTimeOut = processAdminModule.processArrival(actualEvent, tableOfEvents);
                    System.out.println("Creando Proceso");
                    if(queryTimeOut){
                        clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                    }
                    break;
                case ArriveToQueryProcessingModule:
                    queryTimeOut =  queryProcessingModule.processArrival(actualEvent, tableOfEvents);
                    System.out.println("Procesando la consulta");
                    if(queryTimeOut){
                        clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                    }
                    break;
                case ArriveToTransactionModule:
                    queryTimeOut = transactionAndStorageModule.processArrival(actualEvent, tableOfEvents);
                    if(queryTimeOut){
                        clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                    }
                    System.out.println("Transaction");
                    break;
                case ArriveToExecutionModule:
                    queryTimeOut = executionModule.processArrival(actualEvent, tableOfEvents);
                    if(queryTimeOut){
                        clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                    }
                    System.out.println("Execution");
                    break;
                case ShowResult:
                    queryTimeOut = clientAdminModule.showResult(actualEvent, tableOfEvents);
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
                    queryTimeOut = executionModule.executeQuery(actualEvent, tableOfEvents);
                    if(queryTimeOut){
                        clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                    }
                    System.out.println("Ejecuta la query");
                    break;
                case ExitTransactionModule:
                    transactionAndStorageModule.processDeparture(actualEvent, tableOfEvents);
                    System.out.println("procesa la salida de Transaction");
                    break;
                case ExitExecutionModule:
                    queryTimeOut = executionModule.processDeparture(actualEvent, tableOfEvents);
                    if(queryTimeOut){
                        clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                    }
                    System.out.println("Procesa la salida de execution");
                    break;
                case ExitQueryProcessingModule:
                    queryTimeOut = queryProcessingModule.processDeparture(actualEvent, tableOfEvents);
                    System.out.println("Hace una SALVAJE salida del QPM");
                    if(queryTimeOut){
                        clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                    }
                    break;
                case LexicalValidation:
                    queryTimeOut = queryProcessingModule.lexicalValidation(actualEvent, tableOfEvents);
                    System.out.println("Hace un SALVAJE lexicalValidation");
                    if(queryTimeOut){
                        clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                    }
                    break;
                case SintacticalValidation:
                    queryTimeOut = queryProcessingModule.sintacticalValidation(actualEvent, tableOfEvents);
                    System.out.println("Hace un SALVAJE sintacticalValidation");
                    if(queryTimeOut){
                        clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                    }
                    break;
                case SemanticValidation:
                    queryTimeOut = queryProcessingModule.semanticValidation(actualEvent, tableOfEvents);
                    System.out.println("Hace un SALVAJE semanticValidation");
                    if(queryTimeOut){
                        clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                    }

                    break;
                case PermissionVerification:
                    queryTimeOut =queryProcessingModule.permissionVerification(actualEvent, tableOfEvents);
                    System.out.println("Hace un SALVAJE permissionVerification");
                    if(queryTimeOut){
                        clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                    }

                    break;
                case QueryOptimization:
                    queryTimeOut =queryProcessingModule.queryOptimization(actualEvent, tableOfEvents);
                    if(queryTimeOut){
                        clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                    }
                    System.out.println("Hace un SALVAJE queryOptimization");

                    break;
            }
            assert tableOfEvents.peek() != null;
            clock = tableOfEvents.peek().getTime();
            double retorno [] = new double[7];
            retorno[0] = clock;
            retorno[1] = (double) clientAdminModule.queueLength();
            retorno[2] = (double) processAdminModule.queueLength();
            retorno[3] = (double) queryProcessingModule.queueLength();
            retorno[4] = (double) transactionAndStorageModule.queueLength();
            retorno[5] = (double) executionModule.queueLength();
            retorno[6] = (double) clientAdminModule.getDiscardedConnections() ;


            for (int i = 0; i < 7; i++){
                System.out.println(retorno[i]+"  ");
            }
            System.out.println("K conexiones disponibles: " + clientAdminModule.getOccupiedFields());
            System.out.println("Occupied Fields process" + processAdminModule.getOccupiedFields());
            System.out.println("Occupied Fields query" + queryProcessingModule.getOccupiedFields());
            System.out.println("transacciont conexiones disponibles: " + transactionAndStorageModule.getOccupiedFields());
            System.out.println("K execution disponibles: " + executionModule.getOccupiedFields());



        }

    }


    public double[] iterateSimulation(){
        Event actualEvent =  tableOfEvents.poll();
        processAdminModule.checkQueue(actualEvent.getTime(),clientAdminModule);
        queryProcessingModule.checkQueue(actualEvent.getTime(),clientAdminModule);
        transactionAndStorageModule.checkQueue(actualEvent.getTime(), clientAdminModule);
        executionModule.checkQueue(actualEvent.getTime(), clientAdminModule);
        
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
                queryProcessingModule.processArrival(actualEvent, tableOfEvents);
                System.out.println("Procesando la consulta");

                break;
            case ArriveToTransactionModule:
                transactionAndStorageModule.processArrival(actualEvent, tableOfEvents);

                System.out.println("Transaction");
                break;
            case ArriveToExecutionModule:
                executionModule.processArrival(actualEvent, tableOfEvents);
                System.out.println("Execution");
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
                System.out.println("Ejecuta la query");
                break;
            case ExitTransactionModule:
                transactionAndStorageModule.processDeparture(actualEvent, tableOfEvents);
                System.out.println("procesa la salida de Transaction");
                break;
            case ExitExecutionModule:
                executionModule.processDeparture(actualEvent, tableOfEvents);

                System.out.println("Procesa la salida de execution");
                break;
            case LexicalValidation:
                queryProcessingModule.lexicalValidation(actualEvent, tableOfEvents);
                System.out.println("Hace un SALVAJE lexicalValidation");

                break;
            case SintacticalValidation:
                queryProcessingModule.sintacticalValidation(actualEvent, tableOfEvents);
                System.out.println("Hace un SALVAJE sintacticalValidation");

                break;
            case SemanticValidation:
                queryProcessingModule.semanticValidation(actualEvent, tableOfEvents);
                System.out.println("Hace un SALVAJE semanticValidation");


                break;
            case PermissionVerification:
                queryProcessingModule.permissionVerification(actualEvent, tableOfEvents);
                System.out.println("Hace un SALVAJE permissionVerification");


                break;
            case QueryOptimization:
                queryProcessingModule.queryOptimization(actualEvent, tableOfEvents);
                System.out.println("Hace un SALVAJE queryOptimization");

                break;
        }
        assert tableOfEvents.peek() != null;
        clock = tableOfEvents.peek().getTime();

        double retorno [] = new double[7];
        retorno[0] = clock;
        retorno[1] = (double) clientAdminModule.queueLength();
        retorno[2] = (double) processAdminModule.queueLength();
        retorno[3] = (double) queryProcessingModule.queueLength();
        retorno[4] = (double) transactionAndStorageModule.queueLength();
        retorno[5] = (double) executionModule.queueLength();
        retorno[6] = (double) clientAdminModule.getDiscardedConnections() ;

        return retorno;
    }
    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------


}




