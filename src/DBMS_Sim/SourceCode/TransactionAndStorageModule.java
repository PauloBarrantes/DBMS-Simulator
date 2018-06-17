package DBMS_Sim.SourceCode;

import java.util.PriorityQueue;

public class TransactionAndStorageModule implements Module{
    private int maxQueries;
    private int busyAttendants;
    private PriorityQueue<Query> queriesInLine;
    private boolean ddlStatementFlag;
    private int[] totalConnectionsByQueryType;
    private double[] timeByQueryType;

    public TransactionAndStorageModule(){}

    @Override
    public void processArrival(Query query) {

    }

    @Override
    public void processDeparture(Query query) {

    }

    @Override
    public void resetVariables() {

    }

    public void setMaxQueries(int maxQueries) {
        this.maxQueries = maxQueries;
    }

    public void setBusyAttendants(int busyAttendants) {
        this.busyAttendants = busyAttendants;
    }

    public void setQueriesInLine(PriorityQueue<Query> queriesInLine) {
        this.queriesInLine = queriesInLine;
    }

    public void setDdlStatementFlag(boolean ddlStatementFlag) {
        this.ddlStatementFlag = ddlStatementFlag;
    }

    public void setTotalConnectionsByQueryType(int[] totalConnectionsByQueryType) {
        this.totalConnectionsByQueryType = totalConnectionsByQueryType;
    }

    public void setTimeByQueryType(double[] timeByQueryType) {
        this.timeByQueryType = timeByQueryType;
    }

    public int getMaxQueries() {
        return maxQueries;
    }

    public int getBusyAttendants() {
        return busyAttendants;
    }

    public PriorityQueue<Query> getQueriesInLine() {
        return queriesInLine;
    }

    public boolean isDdlStatementFlag() {
        return ddlStatementFlag;
    }

    public int[] getTotalConnectionsByQueryType() {
        return totalConnectionsByQueryType;
    }

    public double[] getTimeByQueryType() {
        return timeByQueryType;
    }
    
}
