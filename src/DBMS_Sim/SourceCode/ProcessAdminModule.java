package DBMS_Sim.SourceCode;

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
                queriesInLine.add(event.getQuery());
            }
        }
        return timedOut;
    }

    public boolean processDeparture(Event event, PriorityQueue<Event> tableOfEvents) {
        boolean timedOut = timedOut(event.getTime(), event.getQuery());
        Query nextQuery;
        Event newEvent;
        if(queriesInLine.size() > 0){
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

        return timedOut;
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
