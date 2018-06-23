package DBMS_Sim.SourceCode;

import java.sql.Statement;

public class SimulationStatistics {


    private int acumulatedDiscardedConnections;
    private double[] acumulatedModuleQueueLength;
    private double[][] acumulatedQueriesWaitTimeInModule;
    private double acumulatedConnectionTime;

    public SimulationStatistics(){
        acumulatedDiscardedConnections = 0;
        acumulatedModuleQueueLength = new double[ModuleType.NUMMODULETYPES];
        acumulatedQueriesWaitTimeInModule = new double[ModuleType.NUMMODULETYPES][StatementType.NUMSTATEMENTS];
        acumulatedConnectionTime = 0.0;
    }

    public int getAcumulatedDiscardedConnections() { return acumulatedDiscardedConnections; }
    public void setAcumulatedDiscardedConnections(int acumulatedDiscardedConnections) { this.acumulatedDiscardedConnections = acumulatedDiscardedConnections; }

    public double[] getAcumulatedModuleQueueLength() { return acumulatedModuleQueueLength; }
    public void setAcumulatedModuleQueueLength(double[] acumulatedModuleQueueLength) { this.acumulatedModuleQueueLength = acumulatedModuleQueueLength; }

    public double[][] getAcumulatedQueriesWaitTimeInModule() { return acumulatedQueriesWaitTimeInModule; }
    public void setAcumulatedQueriesWaitTimeInModule(double[][] acumulatedQueriesWaitTimeInModule) { this.acumulatedQueriesWaitTimeInModule = acumulatedQueriesWaitTimeInModule; }

    public double getAcumulatedConnectionTime() { return acumulatedConnectionTime; }
    public void setAcumulatedConnectionTime(double acumulatedConnectionTime) { this.acumulatedConnectionTime = acumulatedConnectionTime; }
}
