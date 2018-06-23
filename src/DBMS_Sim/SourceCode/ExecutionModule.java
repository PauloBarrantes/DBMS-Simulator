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
    public boolean executeQuery(Event event, PriorityQueue<Event> tableOfEvents){
        boolean removedQuery = timedOut(event.getTime(),event.getQuery());

        if(!removedQuery) {
            event.setType(EventType.ExitExecutionModule);

            Query query = event.getQuery();
            double time = 0.0;
            if(query.getStatementType() == statementType.DDL){
                time = 0.5;
            }else{
                if(query.getStatementType() == statementType.UPDATE){
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

        return removedQuery;
    }

    /**
     * @param event, object that contains the information needed to execute each of the event types.
     * @param tableOfEvents, queue with a list of events to be executed.
     * @return  boolean that says if a query was removed, so other modules can also update their stats.
     * @function send the query to the client admin module.
     */
    public boolean processDeparture(Event event, PriorityQueue<Event> tableOfEvents) {
        boolean removedQuery = timedOut(event.getTime(),event.getQuery());

        if(!removedQuery){
            event.setType(EventType.ShowResult);
            tableOfEvents.add(event);
            --occupiedFields;

//            System.out.println("Exec departure event");
//            System.out.println(event.toString());
        }

        processNextInQueue(event.getTime(),tableOfEvents,EventType.ExecuteQuery);
        addDurationInModule(event.getTime(),event.getQuery());
        return removedQuery;
    }

    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------

    /*
    public static void main(String[] args){
        ExecutionModule executionModule = new ExecutionModule(1,1);

        PriorityQueue<Event> tableOfEvents = new PriorityQueue<>(10,new Comparator<Event>() {
            public int compare(Event event1, Event event2) {
                int cmp = 0;
                if(event1.getTime() < event2.getTime()){
                    cmp = -1;
                }else{
                    if(event1.getTime() > event2.getTime()){
                        cmp = 1;
                    }
                }
                return cmp;
            }
        });

        QueryGenerator generator = new QueryGenerator();
        Query query = generator.generate(0);
        System.out.println(query.toString());
        Event event = new Event(EventType.ArriveToExecutionModule,query.getSubmissionTime(),query);
        tableOfEvents.add(event);
        System.out.println(tableOfEvents.peek().toString());

        query = generator.generate(0);
        System.out.println(query.toString());
        event = new Event(EventType.ArriveToExecutionModule,query.getSubmissionTime(),query);
        tableOfEvents.add(event);
        System.out.println(tableOfEvents.peek().toString());

        query = generator.generate(0);
        System.out.println(query.toString());
        event = new Event(EventType.ArriveToExecutionModule,query.getSubmissionTime(),query);
        tableOfEvents.add(event);
        System.out.println(tableOfEvents.peek().toString());

        while(tableOfEvents.size() > 0 && tableOfEvents.peek().getType() != EventType.ShowResult){
            if(tableOfEvents.peek().getType() == EventType.ArriveToExecutionModule){
                executionModule.processArrival(tableOfEvents.remove(),tableOfEvents,EventType.ExecuteQuery);
            }else{
                if(tableOfEvents.peek().getType() == EventType.ExecuteQuery){
                    executionModule.executeQuey(tableOfEvents.remove(),tableOfEvents);
                }else{
                    if(tableOfEvents.peek().getType() == EventType.ExitExecutionModule){
                        executionModule.processDeparture(tableOfEvents.remove(),tableOfEvents);
                    }else{
                        System.out.println("Unknown event type.");
                    }
                }
            }

            if(tableOfEvents.size() > 0){
                System.out.println("Top priority");
                System.out.println(tableOfEvents.peek().toString());
            }
        }

        System.out.println(executionModule.toString());
    }
    */
}
