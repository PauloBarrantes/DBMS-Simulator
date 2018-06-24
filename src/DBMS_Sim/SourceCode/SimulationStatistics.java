package DBMS_Sim.SourceCode;

public class SimulationStatistics {


    private int acumulatedDiscardedConnections;
    private int timeoutConnections;
    private int acumulatedArrivals;
    private double[] acumulatedModuleQueueLength;
    private double[][] acumulatedQueriesWaitTimeInModule;
    private double acumulatedConnectionTime;

    public SimulationStatistics(){
        acumulatedDiscardedConnections = 0;
        acumulatedModuleQueueLength = new double[ModuleType.NUMMODULETYPES];
        acumulatedQueriesWaitTimeInModule = new double[ModuleType.NUMMODULETYPES][StatementType.NUMSTATEMENTS];
        acumulatedConnectionTime = 0.0;
    }

    public int getTimeoutConnections() { return timeoutConnections;}

    public void setTimeoutConnections(int timeoutConnections) {
        this.timeoutConnections = timeoutConnections;
    }




    public int getAcumulatedArrivals() {
        return acumulatedArrivals;
    }

    public void setAcumulatedArrivals(int acumulatedArrivals) {
        this.acumulatedArrivals = acumulatedArrivals;
    }

    public int getAcumulatedDiscardedConnections() {
        return acumulatedDiscardedConnections;
    }
    public void setAcumulatedDiscardedConnections(int acumulatedDiscardedConnections) {
        this.acumulatedDiscardedConnections = acumulatedDiscardedConnections;
    }

    public double[] getAcumulatedModuleQueueLength() {
        return acumulatedModuleQueueLength;
    }
    public void setAcumulatedModuleQueueLength(double[] acumulatedModuleQueueLength) {
        this.acumulatedModuleQueueLength = acumulatedModuleQueueLength;
    }

    public double[][] getAcumulatedQueriesWaitTimeInModule() {
        return acumulatedQueriesWaitTimeInModule;
    }
    public void setAcumulatedQueriesWaitTimeInModule(double[][] acumulatedQueriesWaitTimeInModule) {
        this.acumulatedQueriesWaitTimeInModule = acumulatedQueriesWaitTimeInModule;
    }

    public double getAcumulatedConnectionTime() {
        return acumulatedConnectionTime;
    }
    public void setAcumulatedConnectionTime(double acumulatedConnectionTime) {
        this.acumulatedConnectionTime = acumulatedConnectionTime;
    }
}
