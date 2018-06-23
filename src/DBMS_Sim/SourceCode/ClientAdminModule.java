package DBMS_Sim.SourceCode;

import java.util.PriorityQueue;

public class ClientAdminModule extends Module{
    private int discardedConnections;
    private QueryGenerator queryGenerator;
    private int cantidadConsultasTerminadasoFinalizadas;
    private double acumuladoDeTiempoDeConsultasQueSalenDelSistema;
    private int numberOfArrivalToTheSystem;

    // ---------------------------------------------------------------------------------------------
    // ----------------------------- Beginning of constructors section -----------------------------
    // ---------------------------------------------------------------------------------------------

    public ClientAdminModule(int maxFields, double timeout){
        super(maxFields,0,new PriorityQueue<Query>(),timeout);
        queryGenerator = new QueryGenerator();
        cantidadConsultasTerminadasoFinalizadas = 0;
        acumuladoDeTiempoDeConsultasQueSalenDelSistema = 0;
        numberOfArrivalToTheSystem = 0;
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
    public boolean processArrival(Event event, PriorityQueue<Event> tableOfEvents){

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
        numberOfArrivalToTheSystem++;
        //Generar una llegada

        Query query = queryGenerator.generate(event.getTime());
        Event arrive = new Event(EventType.ArriveClientToModule,query.getSubmissionTime(), query);
        tableOfEvents.add(arrive);

        System.out.println("Sale del Client Admin hacia el process");

        return false;
    }


    public boolean processDeparture(Event event, PriorityQueue<Event> tableOfEvents) {
        boolean timedOut = timedOut(event.getTime(), event.getQuery());
        if (!timedOut){

        }else{

        }
        // Acá ya la consulta paso por to-do el dbms ahora llega del execution module una salida, donde simplemente liberamos la conexion que estamos usando.
        --occupiedFields;
        cantidadConsultasTerminadasoFinalizadas++;
        acumuladoDeTiempoDeConsultasQueSalenDelSistema += (event.getTime() - event.getQuery().getSubmissionTime());
        return false;
    }

    public void timedOutConnection(double clock, Query query){
        --occupiedFields;
        ++discardedConnections;
        cantidadConsultasTerminadasoFinalizadas++;
        acumuladoDeTiempoDeConsultasQueSalenDelSistema += (clock - query.getSubmissionTime());
    }

    public boolean showResult(Event event, PriorityQueue<Event> tableOfEvents) {
        boolean timedOut = timedOut(event.getTime(),event.getQuery());
        if(!timedOut){
            event.getQuery().setModuleEntryTime(event.getTime());
            event.setTime(event.getTime() + event.getQuery().getLoadedBlocks());
            event.setType(EventType.ExitClientModule);
            tableOfEvents.add(event);
        }

        return timedOut;
    }

    @Override
    public void checkQueue(double clock, ClientAdminModule clientAdminModule){

    }
    @Override
    public void resetVariables(){

    }






    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------
}
