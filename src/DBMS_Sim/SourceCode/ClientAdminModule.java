package DBMS_Sim.SourceCode;

import java.util.PriorityQueue;

public class ClientAdminModule extends Module{
    private int discardedConnections;
    private QueryGenerator queryGenerator;
    private int finishedQueriesCounter;
    private double accumulatedFinishedQueryTimes;
    private int numberOfArrivalToTheSystem;

    // ---------------------------------------------------------------------------------------------
    // ----------------------------- Beginning of constructors section -----------------------------
    // ---------------------------------------------------------------------------------------------

    public ClientAdminModule(int maxFields, double timeout){
        super(maxFields,0,new PriorityQueue<Query>(),timeout);
        queryGenerator = new QueryGenerator();
        finishedQueriesCounter = 0;
        accumulatedFinishedQueryTimes = 0;
        numberOfArrivalToTheSystem = 0;
    }

    // ---------------------------------------------------------------------------------------------
    // ------------------------------ End of the constructors section ------------------------------
    // ---------------------------------------------------------------------------------------------

    ///ELIMINAR
    public int getNumberOfArrivalToTheSystem(){return numberOfArrivalToTheSystem;}

    // ---------------------------------------------------------------------------------------------
    // ----------------------- Beginning of the setters and getters section -----------------------
    // ---------------------------------------------------------------------------------------------

    public void setDiscardedConnections(int discardedConnections) {
        this.discardedConnections = discardedConnections;
    }
    public void setQueryGenerator(QueryGenerator queryGenerator) { this.queryGenerator = queryGenerator;}

    public int getDiscardedConnections() {
        return discardedConnections;
    }
    public QueryGenerator getQueryGenerator() { return queryGenerator;}




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
        // Acá ya la consulta paso por to-do el dbms ahora llega del execution module una salida, donde simplemente liberamos la conexion que estamos usando.
        --occupiedFields;
        finishedQueriesCounter++;
        accumulatedFinishedQueryTimes += (event.getTime() - event.getQuery().getSubmissionTime());
        return false;
    }

    public void timedOutConnection(double clock, Query query){

        System.out.println("CONEXION TIMEOUT");
        --occupiedFields;
        ++discardedConnections;
        finishedQueriesCounter++;
        accumulatedFinishedQueryTimes += (clock - query.getSubmissionTime());

    }

    public boolean showResult(Event event, PriorityQueue<Event> tableOfEvents) {
        boolean timedOut = timedOut(event.getTime(),event.getQuery());
        if(!timedOut){
            event.getQuery().setModuleEntryTime(event.getTime());
            event.setTime(event.getTime() + event.getQuery().getLoadedBlocks());
            event.setType(EventType.ExitClientModule);
            tableOfEvents.add(event);
        }
        // No hace falta porque timedOutConnection va restar el occupiedField
        return timedOut;
    }

    @Override
    public void checkQueue(double clock, ClientAdminModule clientAdminModule){

    }
    @Override
    public void resetVariables(){

    }






    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------
}
