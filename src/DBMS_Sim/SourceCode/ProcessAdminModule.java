package DBMS_Sim.SourceCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class ProcessAdminModule extends Module {
    private NormalDistributionGenerator distribution;


    // ---------------------------------------------------------------------------------------------
    // ----------------------------- Beginning of constructors section -----------------------------
    // ---------------------------------------------------------------------------------------------

    public ProcessAdminModule(int maxFields, double timeout) {
        super(maxFields,0,new LinkedList<Query>(),timeout);
        distribution = new NormalDistributionGenerator(1,0.01);
    }

    // ---------------------------------------------------------------------------------------------
    // ------------------------------ End of the constructors section ------------------------------
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

        if(!timedOut) {
            if (occupiedFields == 0) {
                occupiedFields++;
                event.setType(EventType.ExitProcessAdminModule);
                double gg = distribution.generate();
                event.setTime(event.getTime() + gg);
                tableOfEvents.add(event);
            } else {
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
        boolean timedOut = timedOut(event.getTime(), event.getQuery());
        Query nextQuery;
        Event newEvent;
        if(queriesInLine.size() > 0){
            queueLength();
            nextQuery = queriesInLine.poll();
            newEvent = new Event(EventType.ExitProcessAdminModule,event.getTime() + distribution.generate(), nextQuery);
            tableOfEvents.add(newEvent);
        }else{
            --occupiedFields;
        }
        if(!timedOut) {
            event.setType(EventType.ArriveToQueryProcessingModule);
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
