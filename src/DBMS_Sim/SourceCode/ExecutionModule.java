package DBMS_Sim.SourceCode;

import java.util.PriorityQueue;

public class ExecutionModule extends Module{

    // ---------------------------------------------------------------------------------------------
    // ----------------------------- Beginning of constructors section -----------------------------
    // ---------------------------------------------------------------------------------------------

    public ExecutionModule(int maxFields, double timeout){
        super(maxFields,0,new PriorityQueue<Query>(),new double[NUMSTATEMENTS],timeout,new int[NUMSTATEMENTS]);
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
    public boolean processArrival(Event event, PriorityQueue<Event> tableOfEvents){
        boolean removedQuery = removeQuery(event.getTime(),event.getQuery());

        if(!removedQuery) {
            Query query = event.getQuery();
            query.setModuleEntryTime(event.getTime());
            countNewQuery(query);

            if (occupiedFields < maxFields) {
                ++occupiedFields;

                event.setType(EventType.ExitExecutionModule);
                event.setQuery(query);

                if(query.getStatementType() == StatementType.UPDATE){
                    event.setTime(event.getTime() + 1.0);
                }else{
                    if(query.getStatementType() == StatementType.DDL){
                        event.setTime(event.getTime() + (1/2));
                    }else{
                        event.setTime(event.getTime() + ((event.getQuery().getLoadedBlocks()*event.getQuery().getLoadedBlocks())/100));
                    }
                }

                tableOfEvents.add(event);
            } else {
                queriesInLine.add(query);
            }
        }else{
            addQueryInQueue(event.getTime(),tableOfEvents);
        }

        return removedQuery;
    }

    /**
     * @param event, object that contains the information needed to execute each of the event types.
     * @param tableOfEvents, queue with a list of events to be executed.
     * @return  boolean that says if a query was removed, so other modules can also update their stats.
     * @function send the query to the client admin module.
     */
    public boolean processDeparture(Event event, PriorityQueue<Event> tableOfEvents) {
        boolean removedQuery = removeQuery(event.getTime(),event.getQuery());

        if(!removedQuery){
            event.setType(EventType.ShowResult);
            tableOfEvents.add(event);
            --occupiedFields;
        }

        addQueryInQueue(event.getTime(),tableOfEvents);
        countStayedTime(event.getTime(),event.getQuery());
        return removedQuery;
    }

    protected boolean addQueryInQueue(double clock, PriorityQueue<Event> tableOfEvents){
        boolean success = false;
        if(queriesInLine.size() > 0  && occupiedFields < maxFields){
            Event newArrival = new Event(EventType.ArriveToExecutionModule,clock,queriesInLine.remove());
            tableOfEvents.add(newArrival);
            ++occupiedFields;
            success = true;
        }

        return success;
    }

    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------
}
