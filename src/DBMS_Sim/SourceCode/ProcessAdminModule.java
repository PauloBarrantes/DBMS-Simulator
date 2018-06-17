package DBMS_Sim.SourceCode;

import java.util.PriorityQueue;

public class ProcessAdminModule implements Module{
    private NormalDistributionGenerator distribution;
    private boolean busyAttendant;
    private PriorityQueue<Query> queriesInLine;
    private int[] totalConnectionsByQueryType;
    private double[] timeByQueryType;

    public ProcessAdminModule() {

    }

    @Override
    public void processArrival(Query query) {

    }

    @Override
    public void processDeparture(Query query) {

    }

    @Override
    public void resetVariables() {

    }

    public void setDistribution(NormalDistributionGenerator distribution) {
        this.distribution = distribution;
    }

    public void setBusyAttendant(boolean busyAttendant) {
        this.busyAttendant = busyAttendant;
    }

    public void setQueriesInLine(PriorityQueue<Query> queriesInLine) {
        this.queriesInLine = queriesInLine;
    }

    public void setTimeByQueryType(double[] timeByQueryType) {
        this.timeByQueryType = timeByQueryType;
    }

    public void setTotalConnectionsByQueryType(int[] totalConnectionsByQueryType) {
        this.totalConnectionsByQueryType = totalConnectionsByQueryType;
    }

    public NormalDistributionGenerator getDistribution() {
        return distribution;
    }

    public boolean isBusyAttendant() {
        return busyAttendant;
    }

    public PriorityQueue<Query> getQueriesInLine() {
        return queriesInLine;
    }

    public double[] getTimeByQueryType() {
        return timeByQueryType;
    }

    public int[] getTotalConnectionsByQueryType() {
        return totalConnectionsByQueryType;
    }
}
