package DBMS_Sim.SourceCode;

import java.util.PriorityQueue;
import java.util.Queue;

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


    public void processArrival(Event event, Queue<Event> tableOfEvents){
        if(occupiedFields < maxFields){
            ++occupiedFields;
            Event newEvent = new Event(EventType.LexicalValidation,event.getTime(),event.getQuery());
            tableOfEvents.add(event);
        }else{
            queriesInLine.add(event.getQuery());
        }
    }

    public void lexicalValidation(Event event, Queue<Event> tableOfEvents){
        Event newEvent = new Event(EventType.LexicalValidation,event.getTime(),event.getQuery());
    }

    public void sintacticalValidation(Event event, Queue<Event> tableOfEvents){

    }

    public void semanticValidation(Event event, Queue<Event> tableOfEvents){

    }

    public void permissionVerification(Event event, Queue<Event> tableOfEvents){

    }

    public void queryOptimization(Event event, Queue<Event> tableOfEvents){

    }

    public void processDeparture() {

    }
}
