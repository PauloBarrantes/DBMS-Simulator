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
    private int acumulatedAverageModuleQueueLength;
    private int acumulatedAverageQueriesWaitTimeInModule;
    private int acumulatedAverageConnectionTime;

    private int discardedConnections;
    private int moduleQueueLength;
    private int queriesWaitedTmeInModule;
    private int connectionTime;

    public StatisticsGenerator() { }

    /*
    public void addDiscardedConnection(int discardedConnections){

    }

    public void increaseDoneSimulations(){

    }

    public void obtainQueueLengthAverage(int  [] queueLengths, int callsToModules){

    }

    public void obtainAverageWaitTime(int[] amountOfQueries, int[] queryTimes, int module){

    }

    public void obtainAverageConnectionTime(double time, int amountOfQueries){
        double averageConnecctionTime = time/ (double) amountOfQueries;
    }
*/

}
