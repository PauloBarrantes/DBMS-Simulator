package DBMS_Sim.SourceCode;

import java.util.Comparator;
import java.util.PriorityQueue;

public class TransactionAndStorageModule extends Module{
    private boolean ddlStatementFlag;
    private UniformDistributionGenerator uniformDistributionGenerator;
/*    private Comparator<Query> comparator = new Comparator<Query>() {
        public int compare(Query arriving, Query queueHead) {
            int cmp = 0;
            if(arriving.getStatementType() > queueHead.getStatementType()){
                cmp = 1;
            }else{
                if(arriving.getStatementType() < queueHead.getStatementType()){
                    cmp = -1;
                }
            }
            return cmp;
        }
    };
*/
    // ---------------------------------------------------------------------------------------------
    // ----------------------------- Beginning of constructors section -----------------------------
    // ---------------------------------------------------------------------------------------------

    public TransactionAndStorageModule(int maxFields, double timeout){
        super(maxFields,0,new PriorityQueue<Query>(100, new Comparator<Query>() {
            public int compare(Query arriving, Query queueHead) {
                int cmp = 0;
                if(arriving.getStatementType() > queueHead.getStatementType()){
                    cmp = -1;
                }else{
                    if(arriving.getStatementType() < queueHead.getStatementType()){
                        cmp = 1;
                    }
                }
                return cmp;
            }
        }),timeout);
        this.uniformDistributionGenerator = new UniformDistributionGenerator(1.0,64.0);
    }

    // ---------------------------------------------------------------------------------------------
    // ------------------------------ End of the constructors section ------------------------------
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // ----------------------- Beginning of the setters and getters section -----------------------
    // ---------------------------------------------------------------------------------------------

    public void setDdlStatementFlag(boolean ddlStatementFlag) {
        this.ddlStatementFlag = ddlStatementFlag;
    }

    private boolean isDdlStatementFlag() {
        return ddlStatementFlag;
    }

    // ---------------------------------------------------------------------------------------------
    // -------------------------- End of the setters and getters section --------------------------
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // ------------------------------- Beginning of methods section -------------------------------
    // ---------------------------------------------------------------------------------------------

    public boolean processArrival(Event event, PriorityQueue<Event> tableOfEvents) {
        boolean timedOut = removeQuery(event.getTime(),event.getQuery());

        if(!timedOut){
            if(occupiedFields < maxFields) {
                if(occupiedFields == 0) {
                    calculateDuration(event.getQuery());
                }else{
                    if (event.getQuery().getStatementType() == StatementType.DDL) {
                        queriesInLine.add(event.getQuery());
                    } else {
                        if (isDdlStatementFlag()) {
                            queriesInLine.add(event.getQuery());
                        } else {
                            calculateDuration(event.getQuery());
                        }
                    }
                }
            }else{
                queriesInLine.add(event.getQuery());
            }
        }

        return timedOut;
    }

    public void processDeparture(Event event, PriorityQueue<Event> tableOfEvents) {

    }

    protected boolean addQueryInQueue(double clock, PriorityQueue<Event> tableOfEvents){
        boolean success = false;
        if(queriesInLine.size() > 0  && occupiedFields < maxFields){
            Event newArrival = new Event(EventType.ArriveToTransactionModule,clock,queriesInLine.remove());
            tableOfEvents.add(newArrival);
            ++occupiedFields;
            success = true;
        }

        return success;
    }

    private double calculateDuration(Query beingProcessed){
        double duration = maxFields * 0.03;

        switch(beingProcessed.getStatementType()){
            case StatementType.JOIN:
                duration += 1/10 * (int)(uniformDistributionGenerator.generate() + 0.5); //We add 0.5 so that we can get the value 64 hen rounded in cast
                break;
            case StatementType.SELECT:
                duration += 1/10;
                break;
            default:
                System.out.println("Unknown Statement Type");
                break;
        }

        return duration;
    }

    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------
}
