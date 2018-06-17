package DBMS_Sim.SourceCode;

import java.util.PriorityQueue;

public class QueryProcessingModule implements Module{
    private int maxProcesses;
    private int busyAttendants;
    private PriorityQueue<Query> queriesInLine;
    private int[] totalConnectionsByQueryType;
    private double[] timeByQueryType;
    private UniformDistributionGenerator sintacticalDistribution;
    private UniformDistributionGenerator semanticalDistribution;
    private ExpDistributionGenerator permissionVerifyDistribution;

    public QueryProcessingModule(){}

    @Override
    public void processArrival(Query query){

    }

    public void lexicalValidation(Query query){

    }

    public void sintacticalValidation(Query query){

    }

    public void semanticValidation(Query query){

    }

    public void permissionVerification(Query query){

    }

    public void queryOptimization(Query query){

    }

    @Override
    public void processDeparture(Query query) {

    }

    @Override
    public void resetVariables() {

    }

    @Override
    public void checkQueues(double clock){}

    public void setMaxProcesses(int maxProcesses) {
        this.maxProcesses = maxProcesses;
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

    public void setSintacticalDistribution(UniformDistributionGenerator sintacticalDistribution) {
        this.sintacticalDistribution = sintacticalDistribution;
    }

    public void setSemanticalDistribution(UniformDistributionGenerator semanticalDistribution) {
        this.semanticalDistribution = semanticalDistribution;
    }

    public void setPermissionVerifyDistribution(ExpDistributionGenerator permissionVerifyDistribution) {
        this.permissionVerifyDistribution = permissionVerifyDistribution;
    }

    public int getMaxProcesses() {
        return maxProcesses;
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

    public UniformDistributionGenerator getSintacticalDistribution() {
        return sintacticalDistribution;
    }

    public UniformDistributionGenerator getSemanticalDistribution() {
        return semanticalDistribution;
    }

    public ExpDistributionGenerator getPermissionVerifyDistribution() {
        return permissionVerifyDistribution;
    }
}
