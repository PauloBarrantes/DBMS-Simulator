package DBMS_Sim.SourceCode;

import java.util.LinkedList;
import java.util.PriorityQueue;

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
     * @function if there is space validates the query that arrived and send it to the exit window, else it is place on hold.
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
     * @function if there is space validates the query that arrived and send it to the exit window, else it is place on hold.
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
//            System.out.println("Exec query");
//            System.out.println(event.toString());
        }else{
            addDurationInModule(event.getTime(),event.getQuery());
            processNextInQueue(event.getTime(),tableOfEvents,EventType.ExecuteQuery);
        }

        return timedOut;
    }

    /**
     * @param event, object that contains the information needed to execute each of the event types.
     * @param tableOfEvents, queue with a list of events to be executed.
     * @return  boolean that says if a query was removed, so other modules can also update their stats.
     * @function send the query to the client admin module.
     */
    public boolean processDeparture(Event event, PriorityQueue<Event> tableOfEvents) {
        boolean timedOut = timedOut(event.getTime(),event.getQuery());

        if(!timedOut){
            event.setType(EventType.ShowResult);
            tableOfEvents.add(event);
            --occupiedFields;

//            System.out.println("Exec departure event");
//            System.out.println(event.toString());
        }

        processNextInQueue(event.getTime(),tableOfEvents,EventType.ExecuteQuery);
        addDurationInModule(event.getTime(),event.getQuery());
        return timedOut;
    }
    /**
     * @param clock, current clock time.
     * @function checks if any query in the queue needs to be removed.
     */

    public void checkQueue(double clock, ClientAdminModule clientAdminModule){
        for(Query query : queriesInLine){
            if(timedOut (clock,query)){
                clientAdminModule.timedOutConnection(clock, query);
                queriesInLine.remove(query);
            }
        }
    }
    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------


}
