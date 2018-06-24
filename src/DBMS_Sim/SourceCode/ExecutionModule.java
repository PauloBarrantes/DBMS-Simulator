package DBMS_Sim.SourceCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * This class simulates the Execution Module, this module executes all statements to display results afterwards.
 *
 * @author  Paulo Barrantes
 * @author  André Flasterstein
 * @author  Fabián Álvarez
 */

public class ExecutionModule extends Module{

    // ---------------------------------------------------------------------------------------------
    // ----------------------------- Beginning of constructors section -----------------------------
    // ---------------------------------------------------------------------------------------------

    public ExecutionModule(int maxFields, double timeout){
        super(maxFields,0,new LinkedList<Query>(),timeout);
    }

    // ---------------------------------------------------------------------------------------------
    // ------------------------------ End of the constructors section ------------------------------
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // ------------------------------- Beginning of methods section -------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     * @param event, object that contains the information needed to execute each of the event types.
     * @param tableOfEvents, queue with a list of events to be executed.
     * @return  boolean that says if a query was removed, so other modules can also update their stats.
     * If there is space validates the query that arrived and send it to the exit window, else it is place on hold.
     */

    public boolean processArrival(Event event, PriorityQueue<Event> tableOfEvents) {
        boolean timedOut = timedOut(event.getTime(),event.getQuery());

        if(!timedOut) {
            if (occupiedFields < maxFields) {
                occupiedFields++;
                event.setType(EventType.ExecuteQuery);
                event.setTime(event.getTime());
                tableOfEvents.add(event);
            } else {
                queriesInLine.add(event.getQuery());
            }
        }
        return timedOut;
    }

    /**
     * @param event, object that contains the information needed to execute each of the event types.
     * @param tableOfEvents, queue with a list of events to be executed.
     * @return  boolean that says if a query was removed, so other modules can also update their stats.
     * If there is space validates the query that arrived and send it to the exit window, else it is place on hold.
     */
    public boolean executeQuery(Event event, PriorityQueue<Event> tableOfEvents){
        boolean timedOut = timedOut(event.getTime(),event.getQuery());

        if(!timedOut) {
            event.setType(EventType.ExitExecutionModule);

            Query query = event.getQuery();
            double time = 0.0;
            if(query.getStatementType() == StatementType.DDL){
                time = 0.5;
            }else{
                if(query.getStatementType() == StatementType.UPDATE){
                    time = 1.0;
                }else{
                    time = query.getLoadedBlocks()*query.getLoadedBlocks()*0.001;
                }
            }
            event.setTime(event.getTime() + time);

            tableOfEvents.add(event);

        }else{
            processNextInQueue(event.getTime(),tableOfEvents,EventType.ExecuteQuery);
            //Statistics
            addDurationInModule(event.getTime(),event.getQuery());
            countNewQuery(event.getQuery());
        }

        return timedOut;
    }

    /**
     * @param event, object that contains the information needed to execute each of the event types.
     * @param tableOfEvents, queue with a list of events to be executed.
     * @return  boolean that says if a query was removed, so other modules can also update their stats.
     * Send the query to the client admin module.
     */
    public boolean processDeparture(Event event, PriorityQueue<Event> tableOfEvents) {
        boolean timedOut = timedOut(event.getTime(),event.getQuery());

        if(!timedOut){
            event.setType(EventType.ShowResult);
            tableOfEvents.add(event);
        }

        processNextInQueue(event.getTime(),tableOfEvents,EventType.ExecuteQuery);
        //Statistics
        addDurationInModule(event.getTime(),event.getQuery());
        countNewQuery(event.getQuery());

        return timedOut;
    }
    /**
     * @param clock, current clock time.
     * Checks if any query in the queue needs to be removed.
     */

    public void checkQueue(double clock, ClientAdminModule clientAdminModule){
        ArrayList<Query> queriesToRemove = new ArrayList<Query>();
        for(Query query : queriesInLine){
            if(timedOut (clock,query)){
                clientAdminModule.timedOutConnection(clock, query);
                queriesToRemove.add(query);
            }
        }

        for (Query query: queriesToRemove){
            queriesInLine.remove(query);
        }
    }
    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------

}
