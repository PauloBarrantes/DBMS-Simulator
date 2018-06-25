package DBMS_Sim.SourceCode;

import java.util.ArrayList;

public class SimulationStatistics {


    private int accumulatedDiscardedConnections;
    private int timeoutConnections;
    private int accumulatedArrivals;
    private double[] accumulatedModuleQueueLength;
    private double[][] accumulatedQueriesWaitTimeInModule;
    private double accumulatedConnectionTime;
    private double upperLimit;
    private double lowerLimit;

    public SimulationStatistics(){
        accumulatedDiscardedConnections = 0;
        accumulatedModuleQueueLength = new double[ModuleType.NUMMODULETYPES];
        accumulatedQueriesWaitTimeInModule = new double[ModuleType.NUMMODULETYPES][StatementType.NUMSTATEMENTS];
        accumulatedConnectionTime = 0.0;
        upperLimit = 0.0;
        lowerLimit = 0.0;
    }

    public int getTimeoutConnections() { return timeoutConnections;}

    public void setTimeoutConnections(int timeoutConnections) {
        this.timeoutConnections = timeoutConnections;
    }

    public void calculateConfidenceInterval(ArrayList<Double> stats){
        double avg = accumulatedConnectionTime / stats.size();
        double variance = 0.0;
        double standardDeviation = 0.0;


        for (int i = 0; i < stats.size(); ++i) {
            variance += (stats.get(i) - avg) * (stats.get(i) - avg);
        }

        variance = variance / (stats.size() - 1);

        standardDeviation= Math.sqrt(variance);
        //We use 1.96 for 95% confidence interval
        upperLimit = (avg + 1.96 * standardDeviation) / Math.sqrt(stats.size());
        lowerLimit = (avg - 1.96 * standardDeviation) / Math.sqrt(stats.size());
    }

    public double getLowerLimit() { return lowerLimit; }

    public double getUpperLimit() { return upperLimit; }

    public int getAccumulatedArrivals() {
        return accumulatedArrivals;
    }

    public void setAccumulatedArrivals(int accumulatedArrivals) {
        this.accumulatedArrivals = accumulatedArrivals;
    }

    public int getAccumulatedDiscardedConnections() {
        return accumulatedDiscardedConnections;
    }
    public void setAccumulatedDiscardedConnections(int accumulatedDiscardedConnections) {
        this.accumulatedDiscardedConnections = accumulatedDiscardedConnections;
    }

    public double[] getAccumulatedModuleQueueLength() {
        return accumulatedModuleQueueLength;
    }
    public void setAccumulatedModuleQueueLength(double[] accumulatedModuleQueueLength) {
        this.accumulatedModuleQueueLength = accumulatedModuleQueueLength;
    }

    public double[][] getAccumulatedQueriesWaitTimeInModule() {
        return accumulatedQueriesWaitTimeInModule;
    }
    public void setAccumulatedQueriesWaitTimeInModule(double[][] accumulatedQueriesWaitTimeInModule) {
        this.accumulatedQueriesWaitTimeInModule = accumulatedQueriesWaitTimeInModule;
    }

    public double getAccumulatedConnectionTime() {
        return accumulatedConnectionTime;
    }
    public void setAccumulatedConnectionTime(double accumulatedConnectionTime) {
        this.accumulatedConnectionTime = accumulatedConnectionTime;
    }
}
