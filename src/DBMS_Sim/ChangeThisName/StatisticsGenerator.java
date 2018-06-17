package DBMS_Sim.ChangeThisName;
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
    private int acumulatedDiscardedConections;
    private int acumulatedAverageModuleQueueLength;
    private int acumulatedAverageQueriesWaitTimeInModule;
    private int acumulatedAverageConnectionTime;
    public StatisticsGenerator() { }

    public void addDiscardedConnection(int discardedConnections){

    }

    public void increaseDoneSimulations(){

    }

    public void obtainQueueLengthAverage(int  [] queueLengths, int callsToModules){

    }

    public void obtainAverageWaitTime(int[] amountOfQueries, int[] queryTimes, int module){

    }

    public void obtainAverageConnectionTime(double time, int amountOfQueries){

    }

    public void setAcumulatedAverageConnectionTime(int acumulatedAverageConnectionTime) {
        this.acumulatedAverageConnectionTime = acumulatedAverageConnectionTime;
    }

    public void setAcumulatedAverageModuleQueueLength(int acumulatedAverageModuleQueueLength) {
        this.acumulatedAverageModuleQueueLength = acumulatedAverageModuleQueueLength;
    }

    public void setAcumulatedAverageQueriesWaitTimeInModule(int acumulatedAverageQueriesWaitTimeInModule) {
        this.acumulatedAverageQueriesWaitTimeInModule = acumulatedAverageQueriesWaitTimeInModule;
    }

    public void setAcumulatedDiscardedConections(int acumulatedDiscardedConections) {
        this.acumulatedDiscardedConections = acumulatedDiscardedConections;
    }

    public void setAmountOfDoneSimulations(int amountOfDoneSimulations) {
        this.amountOfDoneSimulations = amountOfDoneSimulations;
    }

    public int getAcumulatedAverageConnectionTime() {
        return acumulatedAverageConnectionTime;
    }

    public int getAcumulatedAverageModuleQueueLength() {
        return acumulatedAverageModuleQueueLength;
    }

    public int getAcumulatedAverageQueriesWaitTimeInModule() {
        return acumulatedAverageQueriesWaitTimeInModule;
    }

    public int getAcumulatedDiscardedConections() {
        return acumulatedDiscardedConections;
    }

    public int getAmountOfDoneSimulations() {
        return amountOfDoneSimulations;
    }
}
