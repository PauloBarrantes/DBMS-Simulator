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

    private int acumulatedDiscardedConnections;
    private int acumulatedTimeoutsConnections;
    private double[] acumulatedModuleQueueLength;
    private double[][] acumulatedQueriesWaitTimeInModule;
    private double acumulatedConnectionTime;

    public StatisticsGenerator() {
        acumulatedDiscardedConnections = 0;
        acumulatedModuleQueueLength = new double[ModuleType.NUMMODULETYPES];
        acumulatedQueriesWaitTimeInModule = new double[ModuleType.NUMMODULETYPES][StatementType.NUMSTATEMENTS];
        acumulatedConnectionTime = 0.0;
        acumulatedTimeoutsConnections = 0;
    }

    public double averageConnectionTime(int finishedQueriesCounter, double accumulatedFinishedQueryTimes){
        acumulatedConnectionTime += accumulatedFinishedQueryTimes/(double)finishedQueriesCounter;
        return accumulatedFinishedQueryTimes/(double)finishedQueriesCounter;
    }

    public double queueLengthAverage(int acumulatedQueueLength, int callsToQueueLength, int moduleType){
        double queueLenghtAverage = 0;
        if(callsToQueueLength > 0){
            acumulatedModuleQueueLength[moduleType] += (double) acumulatedQueueLength/(double)callsToQueueLength;
            queueLenghtAverage = (double)acumulatedQueueLength/(double)callsToQueueLength;
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
        acumulatedQueriesWaitTimeInModule[moduleType] = averagePassedTime;
        return averagePassedTime;
    }

    public void addDiscardedConnections(int discardedConnections){
        acumulatedDiscardedConnections += discardedConnections;
    }

    public void increaseDoneSimulations(){
        ++amountOfDoneSimulations;
    }

    public double getAverageConnectionTime() {
        return acumulatedConnectionTime/(double)amountOfDoneSimulations;
    }

    public double[] getAverageModuleQueueLength(){
        double[] average = new double[ModuleType.NUMMODULETYPES];
        for(int i = 0; i < ModuleType.NUMMODULETYPES; ++i){
            average[i] = acumulatedModuleQueueLength[i]/amountOfDoneSimulations;
        }
        return average;
    }

    public double[][] getAverageQueriesWaitedTimeInModule(){
        double[][] average = new double[ModuleType.NUMMODULETYPES][StatementType.NUMSTATEMENTS];
        for(int f = 0; f < ModuleType.NUMMODULETYPES; ++f){
            for(int c = 0; c < StatementType.NUMSTATEMENTS; ++c){
                average[f][c] = acumulatedQueriesWaitTimeInModule[f][c]/amountOfDoneSimulations;
            }
        }

        return average;
    }

    public int getAcumulatedDiscardedConnections() { return acumulatedDiscardedConnections; }

    public void setTimeoutConnections(int timeoutConnections) {
        acumulatedTimeoutsConnections += timeoutConnections;
    }

    public int getAcumulatedTimeoutsConnections() {
        return acumulatedTimeoutsConnections;
    }
}
