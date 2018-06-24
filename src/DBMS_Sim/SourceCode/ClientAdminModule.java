package DBMS_Sim.SourceCode;

import java.util.PriorityQueue;

/**
 * This class simulates the Client Administration Module, simulates the arrival of new connections and manages them.
 *
 * @author  Paulo Barrantes
 * @author  André Flasterstein
 * @author  Fabián Álvarez
 */


public class ClientAdminModule extends Module{
    private int discardedConnections;
    private QueryGenerator queryGenerator;
    private int finishedQueriesCounter;
    private double accumulatedFinishedQueryTimes;
    private int numberOfArrivalToTheSystem;
    private int timedOutConnections;

    // ---------------------------------------------------------------------------------------------
    // ----------------------------- Beginning of constructors section -----------------------------
    // ---------------------------------------------------------------------------------------------

    public ClientAdminModule(int maxFields, double timeout){
        super(maxFields,0,new PriorityQueue<Query>(),timeout);
        queryGenerator = new QueryGenerator();
        finishedQueriesCounter = 0;
        accumulatedFinishedQueryTimes = 0;
        numberOfArrivalToTheSystem = 0;
        discardedConnections = 0;
    }

    // ---------------------------------------------------------------------------------------------
    // ------------------------------ End of the constructors section ------------------------------
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // ----------------------- Beginning of the setters and getters section -----------------------
    // ---------------------------------------------------------------------------------------------


    public int getDiscardedConnections() {
        return discardedConnections;
    }
    public int getTimedOutConnections() {
        return timedOutConnections;
    }

    public QueryGenerator getQueryGenerator() { return queryGenerator;}
    public int getFinishedQueriesCounter() { return finishedQueriesCounter; }
    public double getAccumulatedFinishedQueryTimes() { return accumulatedFinishedQueryTimes; }

    // ---------------------------------------------------------------------------------------------
    // -------------------------- End of the setters and getters section --------------------------
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // ------------------------------- Beginning of methods section -------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     * @param event, object that contains the information and the object needed to execute each of the simulation events.
     * @param tableOfEvents, queue with a list of events to be executed.
     * @return  boolean that says if a query was removed as a result of a timeout, so other modules can also update their stats.
     * Creates an exit event from this module or adds a query to the queue, depending on specified module restrictions.
     */
    public boolean processArrival(Event event, PriorityQueue<Event> tableOfEvents){

        if(occupiedFields < maxFields){
            //Generate a new arrival to next module
            event.getQuery().setModuleEntryTime(event.getTime());
            event.setType(EventType.ArriveToProcessAdminModule);
            tableOfEvents.add(event);
            countNewQuery(event.getQuery());
            occupiedFields++;
        }else{
            discardedConnections++;
        }
        numberOfArrivalToTheSystem++;
        //We generate a new arrival to the system

        Query query = queryGenerator.generate(event.getTime());
        Event arrive = new Event(EventType.ArriveClientToModule,query.getSubmissionTime(), query);
        tableOfEvents.add(arrive);


        return false;
    }

    /**
     * @param event, object that contains the information and the object needed to execute each of the simulation events.
     * @param tableOfEvents, queue with a list of events to be executed.
     * @return  boolean that says if a query was removed as a result of a timeout, so other modules can also update their stats.
     * Creates an arrival event for next module if query hasn't timed out and if there's someone in line, creates en exit event from this module.
     * If not, decrements occupiedFields. Calls another method for statistic purposes.
     */
    public boolean processDeparture(Event event, PriorityQueue<Event> tableOfEvents) {
        addDurationInModule(event.getTime(),event.getQuery());
        countNewQuery(event.getQuery());
        addDurationInModule(event.getTime(),event.getQuery());
        --occupiedFields;
        finishedQueriesCounter++;
        accumulatedFinishedQueryTimes += (event.getTime() - event.getQuery().getSubmissionTime());
        return false;
    }

    /**
     * @param clock, current simulation time.
     * @param query, query that timed out.
     * Modifies statistic variables after a connection timed out
     */
    public void timedOutConnection(double clock, Query query){

        --occupiedFields;
        ++timedOutConnections;
        //++discardedConnections;
        finishedQueriesCounter++;
        accumulatedFinishedQueryTimes += (clock - query.getSubmissionTime());

    }

    /**
     * @param event, event that hold the query which results will be shown.
     * @param tableOfEvents, queue with a list of events to be executed.
     * Shows results before leaving module.
     */
    public boolean showResult(Event event, PriorityQueue<Event> tableOfEvents) {
        boolean timedOut = timedOut(event.getTime(),event.getQuery());
        if(!timedOut){
            event.setTime(event.getTime() +( event.getQuery().getLoadedBlocks()));
            event.setType(EventType.ExitClientModule);
            tableOfEvents.add(event);
        }
        countNewQuery(event.getQuery());
        addDurationInModule(event.getTime(),event.getQuery());

        return timedOut;
    }

    @Override
    public void checkQueue(double clock, ClientAdminModule clientAdminModule){
    }

    @Override
    public void resetVariables(){
        super.resetVariables();
        finishedQueriesCounter = 0;
        accumulatedFinishedQueryTimes = 0;
        numberOfArrivalToTheSystem = 0;
        discardedConnections = 0;
        timedOutConnections = 0;
    }

    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------
}
