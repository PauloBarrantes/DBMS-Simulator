package DBMS_Sim.SourceCode;

import java.util.Comparator;
import java.util.PriorityQueue;

public class ProcessAdminModule extends Module {
    private NormalDistributionGenerator distribution;


    // ---------------------------------------------------------------------------------------------
    // ----------------------------- Beginning of constructors section -----------------------------
    // ---------------------------------------------------------------------------------------------

    public ProcessAdminModule(int maxFields, double timeout) {
        super(maxFields,0,new PriorityQueue<Query>(100, new Comparator<Query>() {
            public int compare(Query arriving, Query queueHead) {
                return 0;
            }
        }),timeout);
        distribution = new NormalDistributionGenerator(1,0.01);
    }

    // ---------------------------------------------------------------------------------------------
    // ------------------------------ End of the constructors section ------------------------------
    // ---------------------------------------------------------------------------------------------


    // ---------------------------------------------------------------------------------------------
    // ------------------------------- Beginning of methods section -------------------------------
    // ---------------------------------------------------------------------------------------------

    public boolean processArrival(Event event, PriorityQueue<Event> tableOfEvents) {
        boolean timedOut = removeQuery(event.getTime(),event.getQuery());

        if(!timedOut) {
            if (occupiedFields == 0) {
                occupiedFields++;
                event.setType(EventType.ExitProcessAdminModule);
                event.setTime(event.getTime() + distribution.generate());
                tableOfEvents.add(event);
            } else {
                queriesInLine.add(event.getQuery());
            }
        }
        return timedOut;
    }

    public void processDeparture(Event event, PriorityQueue<Event> tableOfEvents) {
        Query nextQuery;
        Event newEvent;
        if(queriesInLine.size() > 0){
            nextQuery = queriesInLine.poll();
            newEvent = new Event(EventType.ExitProcessAdminModule,event.getTime() + distribution.generate(), nextQuery);
            tableOfEvents.add(newEvent);
        }else{
            --occupiedFields;
        }

        event.setType(EventType.ArriveToQueryProcessingModule);
        tableOfEvents.add(event);

        //Statistics
        countStayedTime(event.getTime(),event.getQuery());
    }

    protected boolean addQueryInQueue(double clock, PriorityQueue<Event> tableOfEvents){
        boolean success = false;
        if(queriesInLine.size() > 0  && occupiedFields < maxFields){
            Event newArrival = new Event(EventType.ArriveToProcessAdminModule,clock,queriesInLine.remove());
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
