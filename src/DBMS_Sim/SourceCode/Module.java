package DBMS_Sim.SourceCode;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
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
    protected final static int SELECT = 0;
    protected final static int UPDATE = 1;
    protected final static int JOIN = 2;
    protected final static int DDL = 3;

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

    public String toString(){
        String string = "Query processing module's information:\n\t-Number of queries:\n\t\tSELECT->" +
                totalConnectionsByQueryType[SELECT] + "\n\t\tUPDATE->" + totalConnectionsByQueryType[UPDATE] +
                "\n\t\tJOIN->" + totalConnectionsByQueryType[JOIN] + "\n\t\tDDL->" +
                totalConnectionsByQueryType[DDL] + "\n-Stayed time by query type:\n\t\tSELECT->" +
                timeByQueryType[SELECT] + "\n\t\tUPDATE->" + timeByQueryType[UPDATE] +
                "\n\t\tJOIN->" + timeByQueryType[JOIN] + "\n\t\tDDL->" +
                timeByQueryType[DDL];
        return string;
    }

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
     * @param clock, current clock time.
     * @function checks if any query in the queue needs to be removed.
     */
    public void checkQueues(double clock){
        Iterator<Query> iterator = queriesInLine.iterator();
        Query query;
        while(iterator.hasNext()){
            query = iterator.next();
            if(removeQuery(clock,query)){
                queriesInLine.remove(query);
            }
        }
    }

    /**
     * @param clock, current clock time.
     * @param query, query that we wanna try to remove.
     * @return boolean that becomes true if the query was removed successfully.
     * @function checks if the query overstayed in the System, if so it removes it.
     */
    protected boolean removeQuery(double clock, Query query){
        boolean success = false;
        if(query.elapsedTimeInSystem(clock) >= this.timeout){
            --occupiedFields;
            System.out.println("Query exceed it's available time");
            success = true;
        }
        return success;
    }

    /**
     * @param query, the query that arrived to the Module.
     * @function check the type of the query, and depending on which is it, increase the amount of
     * that type of query.
     */
    protected void countNewQuery(Query query){
        if(query.getStatementType() == StatementType.SELECT){
            ++totalConnectionsByQueryType[SELECT];
        }else{
            if(query.getStatementType() == StatementType.UPDATE){
                ++totalConnectionsByQueryType[UPDATE];
            }else{
                if(query.getStatementType() == StatementType.JOIN){
                    ++totalConnectionsByQueryType[JOIN];
                }else{
                    if(query.getStatementType() == StatementType.DDL){
                        ++totalConnectionsByQueryType[DDL];
                    }else{
                        System.out.println("Unknown query type");
                    }
                }
            }
        }
    }

    /**
     * @param clock, current clock time.
     * @param query, the query that is leaving the Module.
     * @function check the type of the query, and depending on which is it, increase the amount of
     * that type of query is staying in the Module.
     */
    protected void countStayedTime(double clock, Query query){
        double stayedTime = query.elapsedTimeInModule(clock);

        if(query.getStatementType() == StatementType.SELECT){
            timeByQueryType[SELECT] += stayedTime;
        }else{
            if(query.getStatementType() == StatementType.UPDATE){
                timeByQueryType[UPDATE] += stayedTime;
            }else{
                if(query.getStatementType() == StatementType.JOIN){
                    timeByQueryType[JOIN] += stayedTime;
                }else{
                    if(query.getStatementType() == StatementType.DDL){
                        timeByQueryType[DDL] += stayedTime;
                    }else{
                        System.out.println("Unknown query type");
                    }
                }
            }
        }
    }

    protected boolean addQueryInQueue(double clock, PriorityQueue<Event> tableOfEvents, EventType eventType){
        boolean success = false;
        if(queriesInLine.size() > 0  && occupiedFields < maxFields){
            Event newArrival = new Event(eventType,clock,queriesInLine.remove());
            tableOfEvents.add(newArrival);
            System.out.println("Creating event for query in queue");
            System.out.println(newArrival.getQuery().toString());
            System.out.println(newArrival.toString());
            success = true;
        }

        return success;
    }

    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------
}
