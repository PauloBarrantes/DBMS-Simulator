package DBMS_Sim.SourceCode;

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
public abstract class Module {
    //Maximum number of queries that we can execute at the same time
    protected int maxFields;
    //number of queries we are attending
    protected int occupiedFields;
    protected Queue<Query> queriesInLine;
    //maximum time of a connection in the system
    protected double timeout;
    //Statistical Variables
    protected double[] timeByQueryType;
    protected int[] totalConnectionsByQueryType;


    // ---------------------------------------------------------------------------------------------
    // ----------------------------- Beginning of constructors section -----------------------------
    // ---------------------------------------------------------------------------------------------

    public Module(int maxFields, int occupiedFields, Queue<Query> queriesInLine,double timeout){
        this.maxFields = maxFields;
        this.occupiedFields = occupiedFields;
        this.queriesInLine = queriesInLine;
        this.timeout = timeout;
        this.timeByQueryType = new double[StatementType.NUMSTATEMENTS];
        this.totalConnectionsByQueryType = new int[StatementType.NUMSTATEMENTS];
    }

    // ---------------------------------------------------------------------------------------------
    // ------------------------------ End of the constructors section ------------------------------
    // ---------------------------------------------------------------------------------------------





    // ---------------------------------------------------------------------------------------------
    // ------------------------------- Beginning of methods section -------------------------------
    // ---------------------------------------------------------------------------------------------
    public abstract boolean processArrival(Event event, PriorityQueue<Event> tableOfEvents);
    public abstract boolean processDeparture(Event event, PriorityQueue<Event> tableOfEvents);
    public abstract void checkQueue(double clock, ClientAdminModule clientAdminModule);

    public int getOccupiedFields(){return occupiedFields;}


    /**
     * @param clock, current clock time.
     * @function checks if any query in the queue needs to be removed.
     */
/*
    public void checkQueue(double clock, ClientAdminModule clientAdminModule){
        for(Query query : queriesInLine){
            if(timedOut (clock,query)){
                clientAdminModule.timedOutConnection(clock, query);
                queriesInLine.remove(query);
            }
        }
    }
    */
    /**
     * @function: get the queue Length
     */
    public int queueLength(){
        return queriesInLine.size();
    }

    /**
     * @param clock, current clock time.
     * @param query, query that we wanna try to remove.
     * @return boolean that becomes true if the query was removed successfully.
     * @function checks if the query overstayed in the System, if so it removes it.
     */
    protected boolean timedOut(double clock, Query query){
        boolean success = false;
        if(query.elapsedTimeInSystem(clock) >= this.timeout){
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
            ++totalConnectionsByQueryType[StatementType.SELECT];
        }else{
            if(query.getStatementType() == StatementType.UPDATE){
                ++totalConnectionsByQueryType[StatementType.UPDATE];
            }else{
                if(query.getStatementType() == StatementType.JOIN){
                    ++totalConnectionsByQueryType[StatementType.JOIN];
                }else{
                    if(query.getStatementType() == StatementType.DDL){
                        ++totalConnectionsByQueryType[StatementType.DDL];
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
    protected void addDurationInModule(double clock, Query query){
        double stayedTime = query.elapsedTimeInModule(clock);


        if(query.getStatementType() == StatementType.SELECT){
            timeByQueryType[StatementType.SELECT] += stayedTime;
        }else{
            if(query.getStatementType() == StatementType.UPDATE){
                timeByQueryType[StatementType.UPDATE] += stayedTime;
            }else{
                if(query.getStatementType() == StatementType.JOIN){
                    timeByQueryType[StatementType.JOIN] += stayedTime;
                }else{
                    if(query.getStatementType() == StatementType.DDL){
                        timeByQueryType[StatementType.DDL] += stayedTime;
                    }else{
                        System.out.println("Unknown query type");
                    }
                }
            }
        }
    }

    protected void processNextInQueue(double clock, PriorityQueue<Event> tableOfEvents, EventType eventType){
        Query nextQuery;
        Event newEvent;
        if(queriesInLine.size() > 0 && occupiedFields < maxFields){
            nextQuery = queriesInLine.poll();
            newEvent = new Event(eventType,clock, nextQuery);
            tableOfEvents.add(newEvent);
        }else{
            --occupiedFields;
        }
    }

    /**
     * @function: resets the attributes of the module. Necessary if  new simulation wants to be done.
     */

    public void resetVariables(){
        this.occupiedFields= 0;
        queriesInLine.clear();

        for(int i = 0; i < StatementType.NUMSTATEMENTS; ++i){
            totalConnectionsByQueryType[i] = 0;
            timeByQueryType[i] = 0.0;
        }
    }

    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------
}
