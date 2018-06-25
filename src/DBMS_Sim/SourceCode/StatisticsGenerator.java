package DBMS_Sim.SourceCode;
/**
 * Esta clase consiste en el cuerpo de la simulación del
 * DBMS, donde simulamos el paso de las consultas por todos los módulos.
 * Guardando los datos estadísticos para generar mejores resultados a la hora
 * de optimizar el sistema.
 *
 * @author  Paulo Barrantes
 * @author  André Flasterstein
 * @author  Fabián Álvarez
 */
public class StatisticsGenerator {

    private int amountOfDoneSimulations;

    private int accumulatedDiscardedConnections;
    private int accumulatedTimeoutsConnections;
    private double[] accumulatedModuleQueueLength;
    private double[][] accumulatedQueriesWaitTimeInModule;
    private double accumulatedConnectionTime;

    public StatisticsGenerator() {
        accumulatedDiscardedConnections = 0;
        accumulatedModuleQueueLength = new double[ModuleType.NUMMODULETYPES];
        accumulatedQueriesWaitTimeInModule = new double[ModuleType.NUMMODULETYPES][StatementType.NUMSTATEMENTS];
        accumulatedConnectionTime = 0.0;
        accumulatedTimeoutsConnections = 0;
    }

    public double averageConnectionTime(int finishedQueriesCounter, double accumulatedFinishedQueryTimes){
        accumulatedConnectionTime += accumulatedFinishedQueryTimes/(double)finishedQueriesCounter;
        return accumulatedFinishedQueryTimes/(double)finishedQueriesCounter;
    }

    public double queueLengthAverage(int accumulatedQueueLength, int callsToQueueLength, int moduleType){
        double queueLenghtAverage = 0;
        if(callsToQueueLength > 0){
            accumulatedModuleQueueLength[moduleType] += (double) accumulatedQueueLength/(double)callsToQueueLength;
            queueLenghtAverage = (double)accumulatedQueueLength/(double)callsToQueueLength;
        }
        return queueLenghtAverage;
    }

    public double[] averagePassedTimeByStatementInModule(int[] totalConnectionsByQueryType, double[] timeByQueryType, int moduleType){
        double[] averagePassedTime = new double[StatementType.NUMSTATEMENTS];

        for(int i = 0; i < StatementType.NUMSTATEMENTS; ++i){
            if(totalConnectionsByQueryType[i] > 0){
                averagePassedTime[i] = timeByQueryType[i]/(double)totalConnectionsByQueryType[i];
            }
        }
        accumulatedQueriesWaitTimeInModule[moduleType] = averagePassedTime;
        return averagePassedTime;
    }

    public void addDiscardedConnections(int discardedConnections){
        accumulatedDiscardedConnections += discardedConnections;
    }

    public void increaseDoneSimulations(){
        ++amountOfDoneSimulations;
    }

    public double getAverageConnectionTime() {
        return accumulatedConnectionTime /(double)amountOfDoneSimulations;
    }

    public double[] getAverageModuleQueueLength(){
        double[] average = new double[ModuleType.NUMMODULETYPES];
        for(int i = 0; i < ModuleType.NUMMODULETYPES; ++i){
            average[i] = accumulatedModuleQueueLength[i]/amountOfDoneSimulations;
        }
        return average;
    }

    public double[][] getAverageQueriesWaitedTimeInModule(){
        double[][] average = new double[ModuleType.NUMMODULETYPES][StatementType.NUMSTATEMENTS];
        for(int f = 0; f < ModuleType.NUMMODULETYPES; ++f){
            for(int c = 0; c < StatementType.NUMSTATEMENTS; ++c){
                average[f][c] = accumulatedQueriesWaitTimeInModule[f][c]/amountOfDoneSimulations;
            }
        }

        return average;
    }

    public int getAccumulatedDiscardedConnections() { return accumulatedDiscardedConnections; }

    public void setTimeoutConnections(int timeoutConnections) {
        accumulatedTimeoutsConnections += timeoutConnections;
    }

    public int getAccumulatedTimeoutsConnections() {
        return accumulatedTimeoutsConnections;
    }
}
