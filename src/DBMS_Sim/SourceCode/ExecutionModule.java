package DBMS_Sim.SourceCode;

import java.util.PriorityQueue;

public class ExecutionModule implements Module{
    private int maxStatements;
    private int busyAttendants;
    private PriorityQueue<Query> queriesInLine;
    private int[] totalConnectionsByQueryType;
    private double[] timeByQueryType;

    public ExecutionModule(){

    }

    @Override
    public void processArrival(Query query){

    }

    @Override
    public void processDeparture(Query query) {

    }

    @Override
    public void resetVariables() {

    }



    public void setMaxStatements(int maxStatements) {
        this.maxStatements = maxStatements;
    }

    public void setBusyAttendants(int busyAttendants) {
        this.busyAttendants = busyAttendants;
    }

    public void setQueriesInLine(PriorityQueue<Query> queriesInLine) {
        this.queriesInLine = queriesInLine;
    }

    public void setTotalConnectionsByQueryType(int[] totalConnectionsByQueryType) {
        this.totalConnectionsByQueryType = totalConnectionsByQueryType;
    }

    public void setTimeByQueryType(double[] timeByQueryType) {
        this.timeByQueryType = timeByQueryType;
    }


    public int getMaxStatements() {
        return maxStatements;
    }

    public int getBusyAttendants() {
        return busyAttendants;
    }

    public PriorityQueue<Query> getQueriesInLine() {
        return queriesInLine;
    }

    public int[] getTotalConnectionsByQueryType() {
        return totalConnectionsByQueryType;
    }

    public double[] getTimeByQueryType() {
        return timeByQueryType;
    }
}
