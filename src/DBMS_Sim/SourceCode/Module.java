package DBMS_Sim.SourceCode;

import java.util.Iterator;
import java.util.Queue;

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
public class Module {
    protected final static int NUMSTATEMENTS = 4;

    protected int maxFields;
    protected int occupiedFields;
    protected Queue<Query> queriesInLine;
    protected double[] timeByQueryType;
    protected double timeout;
    protected int[] totalConnectionsByQueryType;


    // ---------------------------------------------------------------------------------------------
    // ----------------------------- Beginning of constructors section -----------------------------
    // ---------------------------------------------------------------------------------------------

    public Module(int maxFields, int occupiedFields, Queue<Query> queriesInLine, double[] timeByQueryType, double timeout, int[] totalConnectionsByQueryType){
        setMaxFields(maxFields);
        setOccupiedFields(occupiedFields);
        setQueriesInLine(queriesInLine);
        setTimeByQueryType(timeByQueryType);
        setTimeout(timeout);
        setTotalConnectionsByQueryType(totalConnectionsByQueryType);
    }

    // ---------------------------------------------------------------------------------------------
    // ------------------------------ End of the constructors section ------------------------------
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // ----------------------- Beginning of the setters and getters section -----------------------
    // ---------------------------------------------------------------------------------------------

    public void setMaxFields(int maxFields) { this.maxFields = maxFields; }
    public void setOccupiedFields(int occupiedFields) { this.occupiedFields = occupiedFields; }
    public void setQueriesInLine(Queue<Query> queriesInLine) { this.queriesInLine = queriesInLine; }
    public void setTimeByQueryType(double[] timeByQueryType) { this.timeByQueryType = timeByQueryType; }
    public void setTimeout(double timeout) { this.timeout = timeout; }
    public void setTotalConnectionsByQueryType(int[] totalConnectionsByQueryType) { this.totalConnectionsByQueryType = totalConnectionsByQueryType; }

    public int getMaxFields() { return maxFields; }
    public int getOccupiedFields() { return occupiedFields; }
    public Queue<Query> getQueriesInLine() { return queriesInLine; }
    public double getTimeout() { return timeout; }
    public double[] getTimeByQueryType() { return timeByQueryType; }
    public int[] getTotalConnectionsByQueryType() { return totalConnectionsByQueryType; }

    // ---------------------------------------------------------------------------------------------
    // -------------------------- End of the setters and getters section --------------------------
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // ------------------------------- Beginning of methods section -------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     * @function: resets the attributes of the module. Necessary if  new simulation wants to be done.
     */
    public void resetVariables(){
        setOccupiedFields(0);
        queriesInLine.clear();

        for(int i = 0; i < NUMSTATEMENTS; ++i){
            totalConnectionsByQueryType[i] = 0;
            timeByQueryType[i] = 0.0;
        }
    }

    /**
     *
     * @param clock, current clock time.
     * @function checks if any query in the queue needs to be removed.
     */
    void checkQueues(double clock){
        Iterator<Query> iterator = queriesInLine.iterator();
        Query query;
        while(iterator.hasNext()){
            query = iterator.next();
            removeQuery(clock,query);
        }
    }

    /**
     *
     * @param clock, current clock time.
     * @param query, query that we wanna try to remove.
     * @return boolean that becomes true if the query was removed successfully.
     * @function checks if the query overstayed in the System, if so it removes it.
     */
    boolean removeQuery(double clock, Query query){
        boolean success = false;
        if(query.elapsedTimeInSystem(clock) >= this.timeout){
            success = queriesInLine.remove(query);
        }
        return success;
    }

    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------
}
