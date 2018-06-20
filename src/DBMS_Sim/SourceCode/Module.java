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
    protected int maxFields;
    protected int occupiedFields;
    protected Queue<Query> queriesInLine;
    protected StatementType statementType;
    protected double[] timeByQueryType;
    protected double timeout;
    protected int[] totalConnectionsByQueryType;


    // ---------------------------------------------------------------------------------------------
    // ----------------------------- Beginning of constructors section -----------------------------
    // ---------------------------------------------------------------------------------------------

    public Module(int maxFields, int occupiedFields, Queue<Query> queriesInLine,double timeout){
        setMaxFields(maxFields);
        setOccupiedFields(occupiedFields);
        setQueriesInLine(queriesInLine);
        setTimeByQueryType(new double[statementType.NUMSTATEMENTS]);
        setTimeout(timeout);
        setTotalConnectionsByQueryType(new int[statementType.NUMSTATEMENTS]);
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
                totalConnectionsByQueryType[statementType.SELECT] + "\n\t\tUPDATE->" + totalConnectionsByQueryType[statementType.UPDATE] +
                "\n\t\tJOIN->" + totalConnectionsByQueryType[statementType.JOIN] + "\n\t\tDDL->" +
                totalConnectionsByQueryType[statementType.DDL] + "\n-Stayed time by query type:\n\t\tSELECT->" +
                timeByQueryType[statementType.SELECT] + "\n\t\tUPDATE->" + timeByQueryType[statementType.UPDATE] +
                "\n\t\tJOIN->" + timeByQueryType[statementType.JOIN] + "\n\t\tDDL->" +
                timeByQueryType[statementType.DDL];
        return string;
    }

    // ---------------------------------------------------------------------------------------------
    // -------------------------- End of the setters and getters section --------------------------
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // ------------------------------- Beginning of methods section -------------------------------
    // ---------------------------------------------------------------------------------------------

    public boolean processArrival(Event event, PriorityQueue<Event> tableOfEvents,EventType nextType){
        boolean removedQuery = removeQuery(event.getTime(),event.getQuery());
        if(!removedQuery) {
            Query query = event.getQuery();
            query.setModuleEntryTime(event.getTime());
            countNewQuery(query);

            if(occupiedFields < maxFields){
                ++occupiedFields;
                event.setType(nextType);

                tableOfEvents.add(event);
                System.out.println("Arrival event");
                System.out.println(event.toString());
            }else{
                System.out.println("Adding query to queue");
                queriesInLine.add(query);
            }
        }

        return removedQuery;
    }

    /**
     * @function: resets the attributes of the module. Necessary if  new simulation wants to be done.
     */
    public void resetVariables(){
        setOccupiedFields(0);
        queriesInLine.clear();

        for(int i = 0; i < statementType.NUMSTATEMENTS; ++i){
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
        System.out.println("Counting query");
        System.out.println(query.toString());
        if(query.getStatementType() == statementType.SELECT){
            ++totalConnectionsByQueryType[statementType.SELECT];
        }else{
            if(query.getStatementType() == statementType.UPDATE){
                ++totalConnectionsByQueryType[statementType.UPDATE];
            }else{
                if(query.getStatementType() == statementType.JOIN){
                    ++totalConnectionsByQueryType[statementType.JOIN];
                }else{
                    if(query.getStatementType() == statementType.DDL){
                        ++totalConnectionsByQueryType[statementType.DDL];
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
        System.out.println("Counting query stayed time");
        System.out.println(query.toString());

        if(query.getStatementType() == statementType.SELECT){
            timeByQueryType[statementType.SELECT] += stayedTime;
        }else{
            if(query.getStatementType() == statementType.UPDATE){
                timeByQueryType[statementType.UPDATE] += stayedTime;
            }else{
                if(query.getStatementType() == statementType.JOIN){
                    timeByQueryType[statementType.JOIN] += stayedTime;
                }else{
                    if(query.getStatementType() == statementType.DDL){
                        timeByQueryType[statementType.DDL] += stayedTime;
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
            Event waitingQuery = new Event(eventType,clock,queriesInLine.remove());
            tableOfEvents.add(waitingQuery);
            System.out.println("Creating event for query in queue");
            System.out.println(waitingQuery.getQuery().toString());
            System.out.println(waitingQuery.toString());
            success = true;
        }
        return success;
    }

    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------
}
