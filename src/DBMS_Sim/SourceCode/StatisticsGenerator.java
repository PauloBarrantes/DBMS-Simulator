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
    private ModuleType moduleTypes;
    private StatementType statementType;
    private int amountOfDoneSimulations;

    private int acumulatedDiscardedConnections;
    private double[] acumulatedModuleQueueLength;
    private double[][] acumulatedQueriesWaitTimeInModule;
    private double acumulatedConnectionTime;

    public StatisticsGenerator() {
        acumulatedDiscardedConnections = 0;
        acumulatedModuleQueueLength = new double[moduleTypes.NUMMODULETYPES];
        acumulatedQueriesWaitTimeInModule = new double[moduleTypes.NUMMODULETYPES][statementType.NUMSTATEMENTS];
        acumulatedConnectionTime = 0.0;
    }

    public double averageConnectionTime(int finishedQueriesCounter, double accumulatedFinishedQueryTimes){
        acumulatedConnectionTime += finishedQueriesCounter/accumulatedFinishedQueryTimes;
        return finishedQueriesCounter/accumulatedFinishedQueryTimes;
    }

    public double queueLengthAverage(int acumulatedQueueLength, int callsToQueueLength, int moduleType){
        acumulatedModuleQueueLength[moduleType] += acumulatedQueueLength/callsToQueueLength;
        return acumulatedQueueLength/callsToQueueLength;
    }

    public double[] averagePassedTimeByStatementInModule(int[] totalConnectionsByQueryType, double[] timeByQueryType, int moduleType){
        double[] averagePassedTime = new double[statementType.NUMSTATEMENTS];

        for(int i = 0; i < statementType.NUMSTATEMENTS; ++i){
            averagePassedTime[i] = timeByQueryType[i]/totalConnectionsByQueryType[i];
        }
        acumulatedQueriesWaitTimeInModule[moduleType] = averagePassedTime;
        return averagePassedTime;
    }

    public void addDiscardedConnections(int discardedConnections){
        acumulatedDiscardedConnections += discardedConnections;
    }

    public void increaseDonSimulations(){
        ++amountOfDoneSimulations;
    }

    public double getdAverageConnectionTime() {
        return acumulatedConnectionTime/amountOfDoneSimulations;
    }

    public double[] getAverageModuleQueueLength(){
        double[] average = new double[moduleTypes.NUMMODULETYPES];
        for(int i = 0; i < moduleTypes.NUMMODULETYPES; ++i){
            average[i] = acumulatedModuleQueueLength[i]/amountOfDoneSimulations;
        }
        return average;
    }

    public double[][] getAverageQueriesWaitedTimeInModule(){
        double[][] average = new double[moduleTypes.NUMMODULETYPES][statementType.NUMSTATEMENTS];
        for(int f = 0; f < moduleTypes.NUMMODULETYPES; ++f){
            for(int c = 0; c < statementType.NUMSTATEMENTS; ++c){
                average[f][c] = acumulatedQueriesWaitTimeInModule[f][c]/amountOfDoneSimulations;
            }
        }

        return average;
    }

    public int getAcumulatedDiscardedConnections() { return acumulatedDiscardedConnections; }
}
