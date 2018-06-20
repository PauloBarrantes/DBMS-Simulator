package DBMS_Sim.SourceCode;

import java.util.PriorityQueue;

public class ClientAdminModule extends Module{
    private int discardedConnections;


    // ---------------------------------------------------------------------------------------------
    // ----------------------------- Beginning of constructors section -----------------------------
    // ---------------------------------------------------------------------------------------------

    public ClientAdminModule(int maxFields, double timeout){
        super(maxFields,0,new PriorityQueue<Query>(),timeout);
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

    public int getDiscardedConnections() {
        return discardedConnections;
    }

    // ---------------------------------------------------------------------------------------------
    // -------------------------- End of the setters and getters section --------------------------
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // ------------------------------- Beginning of methods section -------------------------------
    // ---------------------------------------------------------------------------------------------

    public boolean processArrival(Event event, PriorityQueue<Event> tableOfEvents){
        boolean gg = true;
        if(occupiedFields <= maxFields){
            //Generar una llegada al Process Module

        }else{
            gg = false;
            discardedConnections++;
        }

        //Generar una llegada

        return gg ;
    }

    public void processDeparture(Event event, PriorityQueue<Event> tableOfEvents) {
        // Acá ya la consulta paso por to-do el dbms ahora llega del execution module una salida, donde simplemente liberamos la conexion que estamos usando.

    }

    public void showResult(Event event, PriorityQueue<Event> tableOfEvents) {

    }

    public void processFinalization(Query query) {

    }



    protected boolean addQueryInQueue(double clock, PriorityQueue<Event> tableOfEvents){
        return false;
    }

    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------
}
