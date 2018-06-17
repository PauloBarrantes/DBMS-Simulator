package DBMS_Sim.SourceCode;

import java.util.PriorityQueue;

public class ClientAdminModule implements Module{
    private int maxConnections;
    private int busyAttendants;
    private PriorityQueue<Query> queriesInLine;
    private int[] totalConnectionsByQueryType;
    private double[] timeByQueryType;
    private double timeInTheSystem;
    private  double timeout;
    private int discardedConnections;

    public ClientAdminModule(){

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

    public void showResult(Query query) {

    }

    public void processFinalization(Query query) {

    }

    public boolean isItTimeout(Query query) {
        return true;
    }



    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
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

    public void setTimeout(double timeout) {
        this.timeout = timeout;
    }

    public void setTimeInTheSystem(double timeInTheSystem) {
        this.timeInTheSystem = timeInTheSystem;
    }

    public void setDiscardedConnections(int discardedConnections) {
        this.discardedConnections = discardedConnections;
    }

    public int getMaxConnections() {
        return maxConnections;
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

    public double getTimeout() {
        return timeout;
    }

    public double getTimeInTheSystem() {
        return timeInTheSystem;
    }

    public int getDiscardedConnections() {
        return discardedConnections;
    }
}
