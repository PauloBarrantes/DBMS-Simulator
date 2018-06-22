package DBMS_Sim.SourceCode;

import java.util.PriorityQueue;

public class ClientAdminModule extends Module{
    private int discardedConnections;
    private QueryGenerator queryGenerator;
    private int cantidadConsultasTerminadasoFinalizadas;
    private double acumuladoDeTiempoDeConsultasQueSalenDelSistema;

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

        if(occupiedFields < maxFields){
            System.out.println("Hay campo");

            //Generar una salida hacia el Process Module
            event.setType(EventType.ArriveToProcessAdminModule);
            System.out.println("Agregamos un nuevo evento");
            tableOfEvents.add(event);

            countNewQuery(event.getQuery());
            occupiedFields++;
            System.out.println("Aumentamos el número de conexiones usadas");
        }else{
            discardedConnections++;
        }

        //Generar una llegada

        Query query = queryGenerator.generate(event.getTime());
        Event arrive = new Event(EventType.ArriveClientToModule,query.getSubmissionTime(), query);
        tableOfEvents.add(arrive);
        System.out.println("Sale del Client Admin hacia el process");
    }


    public void processDeparture(Event event, PriorityQueue<Event> tableOfEvents) {
        // Acá ya la consulta paso por to-do el dbms ahora llega del execution module una salida, donde simplemente liberamos la conexion que estamos usando.
        --occupiedFields;
        cantidadConsultasTerminadasoFinalizadas++;
        acumuladoDeTiempoDeConsultasQueSalenDelSistema += (event.getTime() - event.getQuery().getSubmissionTime());
    }

    public void consultaTimeouteada(Event event){
        --occupiedFields;
        cantidadConsultasTerminadasoFinalizadas++;
        acumuladoDeTiempoDeConsultasQueSalenDelSistema += (event.getTime() - event.getQuery().getSubmissionTime());
    }

    public boolean showResult(Event event, PriorityQueue<Event> tableOfEvents) {
        boolean removedQuery = removeQuery(event.getTime(),event.getQuery());
        if(!removedQuery){
            event.getQuery().setModuleEntryTime(event.getTime());
            event.setTime(event.getTime() + event.getQuery().getLoadedBlocks());
            event.setType(EventType.ExitClientModule);
            tableOfEvents.add(event);
        }else{
            //++discardedConnections;
        }

        return removedQuery;
    }






    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------
}
