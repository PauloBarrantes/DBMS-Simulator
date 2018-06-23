package DBMS_Sim.SourceCode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class QueryProcessingModule extends Module{
    private ExpDistributionGenerator permissionVerifyDistribution;
    private UniformDistributionGenerator semanticalDistribution;
    private UniformDistributionGenerator sintacticalDistribution;


    // ---------------------------------------------------------------------------------------------
    // ----------------------------- Beginning of constructors section -----------------------------
    // ---------------------------------------------------------------------------------------------

    public QueryProcessingModule(int maxFields, double timeout){
        super(maxFields,0,new LinkedList<Query>(),timeout);
        setPermissionVerifyDistribution(new ExpDistributionGenerator(0.7));
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

    public boolean processArrival(Event event, PriorityQueue<Event> tableOfEvents) {
        boolean timedOut = timedOut(event.getTime(),event.getQuery());

        if(!timedOut) {
            if (occupiedFields < maxFields) {
                occupiedFields++;
                event.setType(EventType.LexicalValidation);
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
     * @function if there is space validates the query that arrived and send it to the sintactical validation, else it is place on hold.
     */
    public boolean lexicalValidation(Event event, PriorityQueue<Event> tableOfEvents){
        boolean timedOut = timedOut(event.getTime(),event.getQuery());

        if(!timedOut) {
            event.setType(EventType.SintacticalValidation);
            event.setTime(event.getTime() + 0.1);
            tableOfEvents.add(event);

        }else{
            processNextInQueue(event.getTime(),tableOfEvents,EventType.LexicalValidation);
        }

        addDurationInModule(event.getTime(),event.getQuery());

        return timedOut;
    }

    /**
     * @param event, object that contains the information needed to execute each of the event types.
     * @param tableOfEvents, queue with a list of events to be executed.
     * @return  boolean that says if a query was removed, so other modules can also update their stats.
     * @function validates the query that arrived and send it to the semantic validation.
     */
    public boolean sintacticalValidation(Event event, PriorityQueue<Event> tableOfEvents){
        boolean timedOut = timedOut(event.getTime(),event.getQuery());

        if(!timedOut){
            event.setType(EventType.SemanticValidation);
            event.setTime(event.getTime() + sintacticalDistribution.generate());
            tableOfEvents.add(event);
        }else{
            addDurationInModule(event.getTime(),event.getQuery());
            processNextInQueue(event.getTime(),tableOfEvents,EventType.LexicalValidation);
        }

        return timedOut;
    }

    /**
     * @param event, object that contains the information needed to execute each of the event types.
     * @param tableOfEvents, queue with a list of events to be executed.
     * @return  boolean that says if a query was removed, so other modules can also update their stats.
     * @function validates the query that arrived and send it to be verified.
     */
    public boolean semanticValidation(Event event, PriorityQueue<Event> tableOfEvents){
        boolean timedOut = timedOut(event.getTime(),event.getQuery());

        if(!timedOut){
            event.setType(EventType.PermissionVerification);
            event.setTime(event.getTime() + semanticalDistribution.generate());
            tableOfEvents.add(event);

        }else{
            addDurationInModule(event.getTime(),event.getQuery());
            processNextInQueue(event.getTime(),tableOfEvents,EventType.LexicalValidation);
        }

        return timedOut;
    }

    /**
     * @param event, object that contains the information needed to execute each of the event types.
     * @param tableOfEvents, queue with a list of events to be executed.
     * @return  boolean that says if a query was removed, so other modules can also update their stats.
     * @function verify the query that arrived and send it to be optimized.
     */
    public boolean permissionVerification(Event event, PriorityQueue<Event> tableOfEvents){
        boolean timedOut = timedOut(event.getTime(),event.getQuery());

        if(!timedOut){
            event.setType(EventType.QueryOptimization);
            event.setTime(event.getTime() + permissionVerifyDistribution.generate());
            tableOfEvents.add(event);

        }else{
            addDurationInModule(event.getTime(),event.getQuery());
            processNextInQueue(event.getTime(),tableOfEvents,EventType.LexicalValidation);
        }

        return timedOut;
    }

    /**
     * @param event, object that contains the information needed to execute each of the event types.
     * @param tableOfEvents, queue with a list of events to be executed.
     * @return  boolean that says if a query was removed, so other modules can also update their stats.
     * @function optimize the query nd send it to the transaction module.
     */
    public boolean queryOptimization(Event event, PriorityQueue<Event> tableOfEvents){
        boolean timedOut = timedOut(event.getTime(),event.getQuery());

        if(!timedOut){
            if(event.getQuery().getReadOnly()){
                event.setTime(event.getTime() + 0.1);
            }else{
                event.setTime(event.getTime() + 0.25);
            }
            event.setType(EventType.ExitQueryProcessingModule);
            tableOfEvents.add(event);
        }else{
            addDurationInModule(event.getTime(),event.getQuery());
            processNextInQueue(event.getTime(),tableOfEvents,EventType.LexicalValidation);
        }
        return timedOut;
    }
    /**
     * @param event, object that contains the information needed to execute each of the event types.
     * @param tableOfEvents, queue with a list of events to be executed.
     * @return  boolean that says if a query was removed, so other modules can also update their stats.
     * @function optimize the query nd send it to the transaction module.
     */
    public boolean processDeparture(Event event, PriorityQueue<Event> tableOfEvents) {
        boolean timedOut = timedOut(event.getTime(), event.getQuery());

        processNextInQueue(event.getTime(), tableOfEvents, EventType.LexicalValidation);

        if(!timedOut) {
            event.setType(EventType.ArriveToTransactionModule);
            tableOfEvents.add(event);
        }

        //Statistics
        addDurationInModule(event.getTime(),event.getQuery());

        return timedOut;
    }
    /**
     * @param clock, object that contains the information needed to execute each of the event types.
     * @param clientAdminModule, queue with a list of events to be executed.
     * @return  boolean that says if a query was removed, so other modules can also update their stats.
     * @function optimize the query nd send it to the transaction module.
     */
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

    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------

}
