package DBMS_Sim.SourceCode;

import java.util.PriorityQueue;

public class ExecutionModule implements Module{
    private int busyAttendants;
    private int maxFields;
    private PriorityQueue<Query> queriesInLine;
    private double[] timeByQueryType;
    private int[] totalConnectionsByQueryType;

    public ExecutionModule(int maxFields, double timeout){
        setBusyAttendants(0);
        setMaxFields(maxFields);
        setQueriesInLine(new PriorityQueue<>());
        setTotalConnectionsByQueryType(new int[4]);
        setTimeByQueryType(new double[4]);
    }


    // ---------------------------------------------------------------------------------------------
    // ----------------------- Beginning of the setters and getters section -----------------------
    // ---------------------------------------------------------------------------------------------


    public void setBusyAttendants(int busyAttendants) {
        this.busyAttendants = busyAttendants;
    }
    public void setMaxFields(int maxFields) {
        this.maxFields = maxFields;
    }
    public void setQueriesInLine(PriorityQueue<Query> queriesInLine) {
        this.queriesInLine = queriesInLine;
    }
    public void setTimeByQueryType(double[] timeByQueryType) {
        this.timeByQueryType = timeByQueryType;
    }
    public void setTotalConnectionsByQueryType(int[] totalConnectionsByQueryType) { this.totalConnectionsByQueryType = totalConnectionsByQueryType; }

    public int getBusyAttendants() {
        return busyAttendants;
    }
    public int getMaxFields() {
        return maxFields;
    }
    public double[] getTimeByQueryType() {
        return timeByQueryType;
    }
    public int[] getTotalConnectionsByQueryType() {
        return totalConnectionsByQueryType;
    }
    public PriorityQueue<Query> getQueriesInLine() {
        return queriesInLine;
    }


    // ---------------------------------------------------------------------------------------------
    // -------------------------- End of the setters and getters section --------------------------
    // ---------------------------------------------------------------------------------------------



    @Override
    public void processArrival(Query query){

    }

    @Override
    public void processDeparture(Query query) {

    }

    @Override
    public void resetVariables() {

    }

    @Override
    public void checkQueues(double clock){}

    @Override
    public boolean timeout(Query query){return true;}
}
