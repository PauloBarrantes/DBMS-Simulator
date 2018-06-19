package DBMS_Sim.SourceCode;

import java.util.PriorityQueue;

public class TransactionAndStorageModule extends Module{
    private boolean ddlStatementFlag;

    // ---------------------------------------------------------------------------------------------
    // ----------------------------- Beginning of constructors section -----------------------------
    // ---------------------------------------------------------------------------------------------

    public TransactionAndStorageModule(int maxFields, double timeout){
        super(maxFields,0,new PriorityQueue<Query>(),new double[NUMSTATEMENTS],timeout,new int[NUMSTATEMENTS]);
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

    public boolean isDdlStatementFlag() {
        return ddlStatementFlag;
    }

    // ---------------------------------------------------------------------------------------------
    // -------------------------- End of the setters and getters section --------------------------
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // ------------------------------- Beginning of methods section -------------------------------
    // ---------------------------------------------------------------------------------------------

    public boolean processArrival(Event event, PriorityQueue<Event> tableOfEvents,EventType nextType) {
        boolean timedOut = removeQuery(event.getTime(),event.getQuery());

        if(!timedOut){
            if(occupiedFields == maxFields){
                
            }
        }

        return timedOut;
    }

    public void processDeparture(Query query) {

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

    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------
}
