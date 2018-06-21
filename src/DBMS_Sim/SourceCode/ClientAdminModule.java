package DBMS_Sim.SourceCode;

import java.util.PriorityQueue;

public class ClientAdminModule extends Module{
    private int discardedConnections;
    private QueryGenerator queryGenerator;


    // ---------------------------------------------------------------------------------------------
    // ----------------------------- Beginning of constructors section -----------------------------
    // ---------------------------------------------------------------------------------------------

    public ClientAdminModule(int maxFields, double timeout){
        super(maxFields,0,new PriorityQueue<Query>(),timeout);
        queryGenerator = new QueryGenerator();
    }

    // ---------------------------------------------------------------------------------------------
    // ------------------------------ End of the constructors section ------------------------------
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // ----------------------- Beginning of the setters and getters section -----------------------
    // ---------------------------------------------------------------------------------------------

    public void setDiscardedConnections(int discardedConnections) {
        this.discardedConnections = discardedConnections;
    }
    public void setQueryGenerator(QueryGenerator queryGenerator) { this.queryGenerator = queryGenerator;}

    public int getDiscardedConnections() {
        return discardedConnections;
    }
    public QueryGenerator getQueryGenerator() { return queryGenerator;}


    // ---------------------------------------------------------------------------------------------
    // -------------------------- End of the setters and getters section --------------------------
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // ------------------------------- Beginning of methods section -------------------------------
    // ---------------------------------------------------------------------------------------------

    public void processArrival(Event event, PriorityQueue<Event> tableOfEvents){
        System.out.println(occupiedFields);
        System.out.println(event.toString());
        if(occupiedFields < maxFields){
            //Generar una salida hacia el Process Module
            event.setType(EventType.ArriveToProcessAdminModule);
            tableOfEvents.add(event);

            countNewQuery(event.getQuery());
            occupiedFields++;
        }else{

            discardedConnections++;
        }

        //Generar una llegada
        Query query = queryGenerator.generate(event.getTime());
        Event arrive = new Event(EventType.ArriveClientToModule,query.getModuleEntryTime(), query);
        tableOfEvents.add(arrive);
    }


    public void processDeparture(Event event, PriorityQueue<Event> tableOfEvents) {
        // Acá ya la consulta paso por to-do el dbms ahora llega del execution module una salida, donde simplemente liberamos la conexion que estamos usando.
        --occupiedFields;
    }

    public void showResult(Event event, PriorityQueue<Event> tableOfEvents) {
        if(removeQuery(event.getTime(), event.getQuery())){
            event.getQuery().setModuleEntryTime(event.getTime());
            event.setTime(event.getTime() + event.getQuery().getLoadedBlocks());
            event.setType(EventType.ExitClientModule);
            tableOfEvents.add(event);
        }else{
            //++discardedConnections;
        }
    }






    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------
}
