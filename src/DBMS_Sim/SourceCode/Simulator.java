package DBMS_Sim.SourceCode;

import java.util.*;

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

    private ClientAdminModule clientAdminModule;
    private double clock;
    private ExecutionModule executionModule;
    private ProcessAdminModule processAdminModule;
    private QueryGenerator queryGenerator;
    private QueryProcessingModule queryProcessingModule;
    private double runningTime;
    private StatisticsGenerator statisticsGenerator;
    private PriorityQueue<Event> tableOfEvents;
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
        statisticsGenerator = new StatisticsGenerator();
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

    public ClientAdminModule getClientAdminModule() {
        return clientAdminModule;
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



    public void appendInitialEvent(){
        Query initialQuery = queryGenerator.generate(0);
        initialQuery.setSubmissionTime(0);
        Event initialArrive = new Event(EventType.ArriveClientToModule,0, initialQuery);
        tableOfEvents.add(initialArrive);
    }
    public static void main(String args[]){
        Simulator simulator = new Simulator(15,15,3,2,1);
        simulator.setRunningTime(15000);
        simulator.simulate();
        simulator.getSimulationStatistics();
    }
    public void simulate(){
        appendInitialEvent();
        Event actualEvent;

        while(clock <= runningTime){
            actualEvent = tableOfEvents.poll();

            checkQueues(actualEvent.getTime());
            checkEventType(actualEvent);

            assert tableOfEvents.peek() != null;
            clock = tableOfEvents.peek().getTime();

        }
        getSimulationStatistics();

    }


    public double[] iterateSimulation(){
        double data []= new double[7];

        Event actualEvent =  tableOfEvents.poll();

        checkQueues(actualEvent.getTime());
        checkEventType(actualEvent);

        assert tableOfEvents.peek() != null;
        clock = tableOfEvents.peek().getTime();


        data[0] = clock;
        data[1] = (double) processAdminModule.queueSize();
        data[2] = (double) queryProcessingModule.queueSize();
        data[3] = (double) transactionAndStorageModule.queueSize();
        data[4] = (double) executionModule.queueSize();
        data[5] = (double) clientAdminModule.getDiscardedConnections();
        data[6] = (double) clientAdminModule.getTimedOutConnections();

        return data;
    }

    private void checkQueues(double clock){
        processAdminModule.checkQueue(clock,clientAdminModule);
        queryProcessingModule.checkQueue(clock,clientAdminModule);
        transactionAndStorageModule.checkQueue(clock, clientAdminModule);
        executionModule.checkQueue(clock, clientAdminModule);
    }

    private void checkEventType(Event actualEvent){
        boolean queryTimeOut;
        switch (actualEvent.getType()) {
            case ArriveClientToModule:
                clientAdminModule.processArrival(actualEvent, tableOfEvents);
                break;
            case ArriveToProcessAdminModule:
                queryTimeOut = processAdminModule.processArrival(actualEvent, tableOfEvents);
                if(queryTimeOut){
                    clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                }
                break;
            case ArriveToQueryProcessingModule:
                queryTimeOut =  queryProcessingModule.processArrival(actualEvent, tableOfEvents);
                if(queryTimeOut){
                    clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                }
                break;
            case ArriveToTransactionModule:
                queryTimeOut = transactionAndStorageModule.processArrival(actualEvent, tableOfEvents);
                if(queryTimeOut){
                    clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                }
                break;
            case ArriveToExecutionModule:
                queryTimeOut = executionModule.processArrival(actualEvent, tableOfEvents);
                if(queryTimeOut){
                    clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                }
                break;
            case ShowResult:
                queryTimeOut = clientAdminModule.showResult(actualEvent, tableOfEvents);
                if(queryTimeOut){
                    clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                }
                break;
            case ExitClientModule:
                queryTimeOut = clientAdminModule.processDeparture(actualEvent, tableOfEvents);
                if(queryTimeOut){
                    clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                }
                break;
            case ExitProcessAdminModule:
                queryTimeOut= processAdminModule.processDeparture(actualEvent, tableOfEvents);
                if(queryTimeOut){
                    clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                }
                break;
            case ExecuteQuery:
                queryTimeOut = executionModule.executeQuery(actualEvent, tableOfEvents);
                if(queryTimeOut){
                    clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                }
                break;
            case ExitTransactionModule:
                queryTimeOut = transactionAndStorageModule.processDeparture(actualEvent, tableOfEvents);
                if(queryTimeOut){
                    clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                }
                break;
            case ExitExecutionModule:
                queryTimeOut = executionModule.processDeparture(actualEvent, tableOfEvents);
                if(queryTimeOut){
                    clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                }
                break;
            case ExitQueryProcessingModule:
                queryTimeOut = queryProcessingModule.processDeparture(actualEvent, tableOfEvents);
                if(queryTimeOut){
                    clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                }
                break;
            case LexicalValidation:
                queryTimeOut = queryProcessingModule.lexicalValidation(actualEvent, tableOfEvents);
                if(queryTimeOut){
                    clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                }
                break;
            case SintacticalValidation:
                queryTimeOut = queryProcessingModule.sintacticalValidation(actualEvent, tableOfEvents);
                if(queryTimeOut){
                    clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                }
                break;
            case SemanticValidation:
                queryTimeOut = queryProcessingModule.semanticValidation(actualEvent, tableOfEvents);
                if(queryTimeOut){
                    clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                }

                break;
            case PermissionVerification:
                queryTimeOut =queryProcessingModule.permissionVerification(actualEvent, tableOfEvents);
                if(queryTimeOut){
                    clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                }

                break;
            case QueryOptimization:
                queryTimeOut =queryProcessingModule.queryOptimization(actualEvent, tableOfEvents);
                if(queryTimeOut){
                    clientAdminModule.timedOutConnection(actualEvent.getTime(), actualEvent.getQuery());
                }
                break;
        }
    }

    // ---------------------------------------------------------------------------------------------
    // ------------------------------- Statistical--------------------------------------------------
    // ---------------------------------------------------------------------------------------------



    public SimulationStatistics getSimulationStatistics(){

        SimulationStatistics simulationStatistics = new SimulationStatistics();

        simulationStatistics.setAccumulatedDiscardedConnections(clientAdminModule.getDiscardedConnections());
        simulationStatistics.setTimeoutConnections(clientAdminModule.getTimedOutConnections());

        statisticsGenerator.addDiscardedConnections(simulationStatistics.getAccumulatedDiscardedConnections());
        statisticsGenerator.setTimeoutConnections(simulationStatistics.getTimeoutConnections());
        double[] accumulatedModuleQueueLength = new double[ModuleType.NUMMODULETYPES];
        accumulatedModuleQueueLength[ModuleType.CLIENTADMIN] = 0;

        accumulatedModuleQueueLength[ModuleType.PROCESSADMIN] = statisticsGenerator.queueLengthAverage(processAdminModule.getAccumulatedQueueLength(),processAdminModule.getCallsToQueueLength(),ModuleType.PROCESSADMIN);
        accumulatedModuleQueueLength[ModuleType.QUERYPROCESSING] = statisticsGenerator.queueLengthAverage(queryProcessingModule.getAccumulatedQueueLength(),queryProcessingModule.getCallsToQueueLength(),ModuleType.QUERYPROCESSING);
        accumulatedModuleQueueLength[ModuleType.TRANSACTIONANDSTORAGE] = statisticsGenerator.queueLengthAverage(transactionAndStorageModule.getAccumulatedQueueLength(),transactionAndStorageModule.getCallsToQueueLength(),ModuleType.TRANSACTIONANDSTORAGE);
        accumulatedModuleQueueLength[ModuleType.EXECUTION] = statisticsGenerator.queueLengthAverage(executionModule.getAccumulatedQueueLength(),executionModule.getCallsToQueueLength(),ModuleType.EXECUTION);
        simulationStatistics.setAccumulatedModuleQueueLength(accumulatedModuleQueueLength);

        double[][] accumulatedQueriesWaitTimeInModule = new double[ModuleType.NUMMODULETYPES][StatementType.NUMSTATEMENTS];
        accumulatedQueriesWaitTimeInModule[ModuleType.CLIENTADMIN] = statisticsGenerator.averagePassedTimeByStatementInModule(clientAdminModule.getTotalConnectionsByQueryType(),clientAdminModule.getTimeByQueryType(),ModuleType.CLIENTADMIN);
        accumulatedQueriesWaitTimeInModule[ModuleType.PROCESSADMIN] = statisticsGenerator.averagePassedTimeByStatementInModule(processAdminModule.getTotalConnectionsByQueryType(),processAdminModule.getTimeByQueryType(),ModuleType.PROCESSADMIN);
        accumulatedQueriesWaitTimeInModule[ModuleType.QUERYPROCESSING] = statisticsGenerator.averagePassedTimeByStatementInModule(queryProcessingModule.getTotalConnectionsByQueryType(),queryProcessingModule.getTimeByQueryType(),ModuleType.QUERYPROCESSING);
        accumulatedQueriesWaitTimeInModule[ModuleType.TRANSACTIONANDSTORAGE] = statisticsGenerator.averagePassedTimeByStatementInModule(transactionAndStorageModule.getTotalConnectionsByQueryType(),transactionAndStorageModule.getTimeByQueryType(),ModuleType.TRANSACTIONANDSTORAGE);
        accumulatedQueriesWaitTimeInModule[ModuleType.EXECUTION] = statisticsGenerator.averagePassedTimeByStatementInModule(executionModule.getTotalConnectionsByQueryType(),executionModule.getTimeByQueryType(),ModuleType.EXECUTION);
        simulationStatistics.setAccumulatedQueriesWaitTimeInModule(accumulatedQueriesWaitTimeInModule);

        simulationStatistics.setAccumulatedConnectionTime(statisticsGenerator.averageConnectionTime(clientAdminModule.getFinishedQueriesCounter(),clientAdminModule.getAccumulatedFinishedQueryTimes()));

        statisticsGenerator.increaseDoneSimulations();


        return simulationStatistics;
    }

    public SimulationStatistics finalStatistics(){
        SimulationStatistics simulationStatistics = new SimulationStatistics();
        simulationStatistics.setAccumulatedConnectionTime(statisticsGenerator.getAverageConnectionTime());
        simulationStatistics.setAccumulatedQueriesWaitTimeInModule(statisticsGenerator.getAverageQueriesWaitedTimeInModule());
        simulationStatistics.setAccumulatedModuleQueueLength(statisticsGenerator.getAverageModuleQueueLength());
        simulationStatistics.setAccumulatedDiscardedConnections(statisticsGenerator.getAccumulatedDiscardedConnections());
        simulationStatistics.setTimeoutConnections(statisticsGenerator.getAccumulatedTimeoutsConnections());
        simulationStatistics.calculateConfidenceInterval(clientAdminModule.getFinishedQueryTimes());

        return simulationStatistics;
    }

    public void reset() {
        tableOfEvents.clear();
        clock=0;
        runningTime = 0;
        clientAdminModule.resetVariables();
        processAdminModule.resetVariables();
        queryProcessingModule.resetVariables();
        transactionAndStorageModule.resetVariables();
        executionModule.resetVariables();
    }
    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------

}