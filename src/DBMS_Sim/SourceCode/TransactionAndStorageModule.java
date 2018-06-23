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
        boolean timedOut = timedOut(event.getTime(),event.getQuery());

        if(!timedOut){
            if(occupiedFields < maxFields) {
                if(occupiedFields == 0) {
                    occupiedFields++;
                    event.setType(EventType.ExitTransactionModule);
                    event.setTime(event.getTime() + calculateDuration(event.getQuery()));
                    tableOfEvents.add(event);
                }else{
                    if (event.getQuery().getStatementType() == StatementType.DDL) {
                        queriesInLine.add(event.getQuery());
                    } else {
                        if (isDdlStatementFlag()) {
                            queriesInLine.add(event.getQuery());
                        } else {
                            occupiedFields++;
                            event.setType(EventType.ExitTransactionModule);
                            event.setTime(event.getTime() + calculateDuration(event.getQuery()));
                            tableOfEvents.add(event);
                        }
                    }
                }
            }else{
                queriesInLine.add(event.getQuery());
            }
        }

        return timedOut;
    }

    public boolean processDeparture(Event event, PriorityQueue<Event> tableOfEvents) {
        boolean timedOut = timedOut(event.getTime(),event.getQuery());
        Query nextQuery;
        Event newEvent;
        if(queriesInLine.size() > 0 && occupiedFields < maxFields){
            nextQuery = queriesInLine.poll();
            newEvent = new Event(EventType.ExitTransactionModule,event.getTime() + calculateDuration(nextQuery), nextQuery);
            tableOfEvents.add(newEvent);
        }else{
            --occupiedFields;
        }
        if(!timedOut) {
            event.setType(EventType.ArriveToExecutionModule);
            tableOfEvents.add(event);
        }else{
            --occupiedFields;
        }

        //Statistics
        addDurationInModule(event.getTime(),event.getQuery());

        return timedOut;
    }

    @Override
    public void checkQueue(double clock, ClientAdminModule clientAdminModule){
        for(Query query : queriesInLine){
            if(timedOut (clock,query)){
                clientAdminModule.timedOutConnection(clock, query);
                queriesInLine.remove(query);
            }
        }
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
        int blocksLoaded = 1;

        switch(beingProcessed.getStatementType()){
            case StatementType.JOIN:
                blocksLoaded = (int)(uniformDistributionGenerator.generate() + 0.5); //We add 0.5 so that we can get the value 64 when rounded in cast
                duration += (0.1 * blocksLoaded);
                beingProcessed.setLoadedBlocks(blocksLoaded);
                break;
            case StatementType.SELECT:
                duration += 1/10;
                beingProcessed.setLoadedBlocks(blocksLoaded);
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
