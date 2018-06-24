package DBMS_Sim.SourceCode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;



public class TransactionAndStorageModule extends Module{
    private boolean ddlStatementFlag;
    private UniformDistributionGenerator uniformDistributionGenerator;

    // ---------------------------------------------------------------------------------------------
    // ----------------------------- Beginning of constructors section -----------------------------
    // ---------------------------------------------------------------------------------------------

    public TransactionAndStorageModule(int maxFields, double timeout){
        super(maxFields,0,new PriorityQueue<Query>(100, new Comparator<Query>() {
            public int compare(Query arriving, Query queueHead) {
                int cmp = 0;
                if(arriving.getStatementType() > queueHead.getStatementType()){
                    cmp = -1;
                }else{
                    if(arriving.getStatementType() < queueHead.getStatementType()){
                        cmp = 1;
                    }
                }
                return cmp;
            }
        }),timeout);
        this.uniformDistributionGenerator = new UniformDistributionGenerator(1.0,64.0);
    }

    // ---------------------------------------------------------------------------------------------
    // ------------------------------ End of the constructors section ------------------------------
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // ----------------------- Beginning of the setters and getters section -----------------------
    // ---------------------------------------------------------------------------------------------

    public void setDdlStatementFlag(boolean ddlStatementFlag) {
        this.ddlStatementFlag = ddlStatementFlag;
    }

    private boolean isDdlStatementFlag() {
        return ddlStatementFlag;
    }

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
     * @function Creates an exit event from this module or adds a query to the queue, depending on specified module restrictions.
     */
    public boolean processArrival(Event event, PriorityQueue<Event> tableOfEvents) {
        boolean timedOut = timedOut(event.getTime(),event.getQuery());
        //We ask if the query that is arriving has timed out.
        if(!timedOut){
            //We ask if there's enough space to serve the event.
            if(occupiedFields < maxFields) {
                event.getQuery().setModuleEntryTime(event.getTime());
                //If no servers are occupied, proceed to be attended.
                if(occupiedFields == 0) {
                    occupiedFields++;
                    event.setType(EventType.ExitTransactionModule);
                    event.setTime(event.getTime() + calculateDuration(event.getQuery()));
                    tableOfEvents.add(event);
                }else{

                    //If there's occupied servers and the query arriving is a DDL statement, it waits in queue.
                    if (event.getQuery().getStatementType() == StatementType.DDL) {
                        queueLength();
                        queriesInLine.add(event.getQuery());
                    } else {
                        //If there is a DDL statement being processed, no query can be served at the moment, so arriving query waits in queue.
                        if (isDdlStatementFlag()) {
                            queueLength();
                            queriesInLine.add(event.getQuery());
                        } else {
                            //If there's no DDL statement being processed, arriving query is not a DDL, and there's enough servers, proceed to attend query.
                            occupiedFields++;
                            event.setType(EventType.ExitTransactionModule);
                            event.setTime(event.getTime() + calculateDuration(event.getQuery()));
                            tableOfEvents.add(event);
                        }
                    }
                }
            }else{
                queueLength();
                queriesInLine.add(event.getQuery());
            }
        }

        return timedOut;
    }

    /**
     * @param event, object that contains the information and the object needed to execute each of the simulation events.
     * @param tableOfEvents, queue with a list of events to be executed.
     * @return  boolean that says if a query was removed as a result of a timeout, so other modules can also update their stats.
     * @function Creates an arrival event for next module if query hasn't timed out and if there's someone in line, creates en exit event from this module.
     * If not, decrements occupiedFields. Calls another method for statistic purposes.
     */
    public boolean processDeparture(Event event, PriorityQueue<Event> tableOfEvents) {
        boolean timedOut = timedOut(event.getTime(),event.getQuery());
        Query nextQuery;
        Event newEvent;
        if(queriesInLine.size() > 0 && occupiedFields < maxFields){
            queueLength();
            nextQuery = queriesInLine.poll();
            newEvent = new Event(EventType.ExitTransactionModule,event.getTime() + calculateDuration(nextQuery), nextQuery);
            tableOfEvents.add(newEvent);
        }else{
            --occupiedFields;
        }
        if(!timedOut) {
            event.setType(EventType.ArriveToExecutionModule);
            tableOfEvents.add(event);
        }

        //Statistics
        addDurationInModule(event.getTime(),event.getQuery());
        countNewQuery(event.getQuery());

        return timedOut;
    }

    /**
     * @param clock, current clock time.
     * @param clientAdminModule, module where timeouts will be handled.
     * @function checks if a query in queue has timed out, if so, removes this query from queue and updates statistic variables.
     */
    @Override
    public void checkQueue(double clock, ClientAdminModule clientAdminModule){
        ArrayList<Query> queriesToRemove = new ArrayList<Query>();
        for(Query query : queriesInLine){
            if(timedOut(clock,query)){
                clientAdminModule.timedOutConnection(clock, query);
                queriesToRemove.add(query);
            }
        }
        for (Query query: queriesToRemove){

            queriesInLine.remove(query);
        }
    }

    /**
     * @param beingProcessed, query that is currently being processed.
     * @return  The duration of the query being processed within this module.
     * @function Defines how long will this query last being attended and how many blocks are loaded from disk.
     */
    private double calculateDuration(Query beingProcessed){
        double duration = maxFields * 0.03;
        int blocksLoaded = 1;

        switch(beingProcessed.getStatementType()){
            case StatementType.JOIN:
                blocksLoaded = (int)(uniformDistributionGenerator.generate() + 0.5); //We add 0.5 so that we can get the value 64 when rounded in cast
                duration += (0.1 * blocksLoaded);
                beingProcessed.setLoadedBlocks(blocksLoaded);
                break;
            case StatementType.SELECT:
                duration += 1/10;
                beingProcessed.setLoadedBlocks(blocksLoaded);
                break;
            default:
                break;
        }

        return duration;
    }

    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------
}
