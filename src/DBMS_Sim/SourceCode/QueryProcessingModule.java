package DBMS_Sim.SourceCode;

import java.util.Comparator;
import java.util.PriorityQueue;

public class QueryProcessingModule extends Module{
    private ExpDistributionGenerator permissionVerifyDistribution;
    private UniformDistributionGenerator semanticalDistribution;
    private UniformDistributionGenerator sintacticalDistribution;


    // ---------------------------------------------------------------------------------------------
    // ----------------------------- Beginning of constructors section -----------------------------
    // ---------------------------------------------------------------------------------------------

    public QueryProcessingModule(int maxFields, double timeout){
        super(maxFields,0,new PriorityQueue<Query>(),new double[NUMSTATEMENTS],timeout,new int[NUMSTATEMENTS]);
        setPermissionVerifyDistribution(new ExpDistributionGenerator(10/7));
        setSemanticalDistribution(new UniformDistributionGenerator(0,2));
        setSintacticalDistribution(new UniformDistributionGenerator(0,1));

    }

    // ---------------------------------------------------------------------------------------------
    // ------------------------------ End of the constructors section ------------------------------
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // ----------------------- Beginning of the setters and getters section -----------------------
    // ---------------------------------------------------------------------------------------------

    public void setSintacticalDistribution(UniformDistributionGenerator sintacticalDistribution) { this.sintacticalDistribution = sintacticalDistribution; }
    public void setSemanticalDistribution(UniformDistributionGenerator semanticalDistribution) { this.semanticalDistribution = semanticalDistribution; }
    public void setPermissionVerifyDistribution(ExpDistributionGenerator permissionVerifyDistribution) { this.permissionVerifyDistribution = permissionVerifyDistribution; }

    public UniformDistributionGenerator getSintacticalDistribution() {
        return sintacticalDistribution;
    }
    public UniformDistributionGenerator getSemanticalDistribution() {
        return semanticalDistribution;
    }
    public ExpDistributionGenerator getPermissionVerifyDistribution() {
        return permissionVerifyDistribution;
    }


    // ---------------------------------------------------------------------------------------------
    // -------------------------- End of the setters and getters section --------------------------
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // ------------------------------- Beginning of methods section -------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     * @param event, object that contains the information needed to execute each of the event types.
     * @param tableOfEvents, queue with a list of events to be executed.
     * @return  boolean that says if a query was removed, so other modules can also update their stats.
     * @function if there is space validates the query that arrived and send it to the sintactical validation, else it is place on hold.
     */
    public boolean lexicalValidation(Event event, PriorityQueue<Event> tableOfEvents){
        boolean removedQuery = removeQuery(event.getTime(),event.getQuery());

        if(!removedQuery) {
            Query query = event.getQuery();
            query.setModuleEntryTime(event.getTime());
            countNewQuery(query);

            if (occupiedFields < maxFields) {
                ++occupiedFields;

                event.setType(EventType.SintacticalValidation);
                event.setQuery(query);
                event.setTime(event.getTime() + (1 / 10));

                tableOfEvents.add(event);
            } else {
                queriesInLine.add(query);
            }
        }else{
            addQueryInQueue(event.getTime(),tableOfEvents,EventType.LexicalValidation);
        }

        return removedQuery;
    }

    /**
     * @param event, object that contains the information needed to execute each of the event types.
     * @param tableOfEvents, queue with a list of events to be executed.
     * @return  boolean that says if a query was removed, so other modules can also update their stats.
     * @function validates the query that arrived and send it to the semantic validation.
     */
    public boolean sintacticalValidation(Event event, PriorityQueue<Event> tableOfEvents){
        boolean removedQuery = removeQuery(event.getTime(),event.getQuery());

        if(!removedQuery){
            event.setType(EventType.SemanticValidation);
            event.setTime(event.getTime() + sintacticalDistribution.generate());
            tableOfEvents.add(event);
        }else{
            countStayedTime(event.getTime(),event.getQuery());
            addQueryInQueue(event.getTime(),tableOfEvents,EventType.LexicalValidation);
        }

        return removedQuery;
    }

    /**
     * @param event, object that contains the information needed to execute each of the event types.
     * @param tableOfEvents, queue with a list of events to be executed.
     * @return  boolean that says if a query was removed, so other modules can also update their stats.
     * @function validates the query that arrived and send it to be verified.
     */
    public boolean semanticValidation(Event event, PriorityQueue<Event> tableOfEvents){
        boolean removedQuery = removeQuery(event.getTime(),event.getQuery());

        if(!removedQuery){
            event.setType(EventType.PermissionVerification);
            event.setTime(event.getTime() + semanticalDistribution.generate());
            tableOfEvents.add(event);
        }else{
            countStayedTime(event.getTime(),event.getQuery());
            addQueryInQueue(event.getTime(),tableOfEvents,EventType.LexicalValidation);
        }

        return removedQuery;
    }

    /**
     * @param event, object that contains the information needed to execute each of the event types.
     * @param tableOfEvents, queue with a list of events to be executed.
     * @return  boolean that says if a query was removed, so other modules can also update their stats.
     * @function verify the query that arrived and send it to be optimized.
     */
    public boolean permissionVerification(Event event, PriorityQueue<Event> tableOfEvents){
        boolean removedQuery = removeQuery(event.getTime(),event.getQuery());

        if(!removedQuery){
            event.setType(EventType.QueryOptimization);
            event.setTime(event.getTime() + permissionVerifyDistribution.generate());
            tableOfEvents.add(event);
        }else{
            countStayedTime(event.getTime(),event.getQuery());
            addQueryInQueue(event.getTime(),tableOfEvents,EventType.LexicalValidation);
        }

        return removedQuery;
    }

    /**
     * @param event, object that contains the information needed to execute each of the event types.
     * @param tableOfEvents, queue with a list of events to be executed.
     * @return  boolean that says if a query was removed, so other modules can also update their stats.
     * @function optimize the query nd send it to the transaction module.
     */
    public boolean queryOptimization(Event event, PriorityQueue<Event> tableOfEvents){
        boolean removedQuery = removeQuery(event.getTime(),event.getQuery());

        if(!removedQuery){
            event.setType(EventType.ArriveToTransactionModule);
            if(event.getQuery().getReadOnly()){
                event.setTime(event.getTime() + 0.1);
            }else{
                event.setTime(event.getTime() + (1/4));
            }
            tableOfEvents.add(event);
            --occupiedFields;
        }

        addQueryInQueue(event.getTime(),tableOfEvents,EventType.LexicalValidation);
        countStayedTime(event.getTime(),event.getQuery());
        return removedQuery;
    }

    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------

    public static void main(String[] args){
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
        Event event = new Event(EventType.LexicalValidation,query.getSubmissionTime(),query);
        tableOfEvents.add(event);
        System.out.println(tableOfEvents.peek().toString());

        query = generator.generate(1.5);
        System.out.println(query.toString());
        event = new Event(EventType.LexicalValidation,query.getSubmissionTime(),query);
        tableOfEvents.add(event);
        System.out.println(tableOfEvents.peek().toString());

        query = generator.generate(6);
        System.out.println(query.toString());
        event = new Event(EventType.LexicalValidation,query.getSubmissionTime(),query);
        tableOfEvents.add(event);
        System.out.println(tableOfEvents.peek().toString());

        while(tableOfEvents.peek().getType() != EventType.ArriveToTransactionModule){
            if(tableOfEvents.peek().getType() == EventType.LexicalValidation){

            }else{
                if(tableOfEvents.peek().getType() == EventType.SintacticalValidation){

                }else{
                    if(tableOfEvents.peek().getType() == EventType.SemanticValidation){

                    }else{
                        if(tableOfEvents.peek().getType() == EventType.PermissionVerification){

                        }else{
                            if(tableOfEvents.peek().getType() == EventType.QueryOptimization){

                            }else{
                                System.out.println("Unknown event type.");
                            }
                        }
                    }
                }
            }
        }
    }
}
