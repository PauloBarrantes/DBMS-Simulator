package DBMS_Sim.SourceCode;

import java.util.PriorityQueue;

public class ClientAdminModule extends Module{
    private int discardedConnections;
    private QueryGenerator queryGenerator;
    private int finishedQueriesCounter;
    private double accumulatedFinishedQueryTimes;
    private int numberOfArrivalToTheSystem;
    private int timeOutConnections;

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
    public int getTimeOutConnections() {
        return timeOutConnections;
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
    public boolean processArrival(Event event, PriorityQueue<Event> tableOfEvents){

        if(occupiedFields < maxFields){
            //Generate a new arrival to next module

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


    public boolean processDeparture(Event event, PriorityQueue<Event> tableOfEvents) {
        addDurationInModule(event.getTime(),event.getQuery());
        countNewQuery(event.getQuery());
        addDurationInModule(event.getTime(),event.getQuery());
        --occupiedFields;
        finishedQueriesCounter++;
        accumulatedFinishedQueryTimes += (event.getTime() - event.getQuery().getSubmissionTime());
        return false;
    }

    public void timedOutConnection(double clock, Query query){

        --occupiedFields;
        ++timeOutConnections;
        //++discardedConnections;
        finishedQueriesCounter++;
        accumulatedFinishedQueryTimes += (clock - query.getSubmissionTime());

    }

    public boolean showResult(Event event, PriorityQueue<Event> tableOfEvents) {
        boolean timedOut = timedOut(event.getTime(),event.getQuery());
        if(!timedOut){
            event.getQuery().setModuleEntryTime(event.getTime());
            event.setTime(event.getTime() +( event.getQuery().getLoadedBlocks()));
            event.setType(EventType.ExitClientModule);
            tableOfEvents.add(event);
        }
        countNewQuery(event.getQuery());
        addDurationInModule(event.getTime(),event.getQuery());
        // No hace falta porque timedOutConnection va restar el occupiedField
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
        timeOutConnections = 0;
    }






    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------
}
