package DBMS_Sim.SourceCode;

/**
 * Instances of this class hold relevant information of an Event.
 *
 * @author  Paulo Barrantes
 * @author  André Flasterstein
 * @author  Fabián Álvarez
 */
public class Event {
    private EventType type;
    private double time;
    private Query query;


    // ---------------------------------------------------------------------------------------------
    // ----------------------------- Beginning of constructors section -----------------------------
    // ---------------------------------------------------------------------------------------------

    public Event(EventType type, double time, Query query){
        setType(type);
        setTime(time);
        setQuery(query);
    }

    // ---------------------------------------------------------------------------------------------
    // ------------------------------ End of the constructors section ------------------------------
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // ----------------------- Beginning of the setters and getters section -----------------------
    // ---------------------------------------------------------------------------------------------

    public void setType(EventType type){
        this.type = type;
    }
    public void setTime(double time){
        this.time = time;
    }
    public void setQuery(Query query){
        this.query = query;
    }

    public EventType getType() {
        return type;
    }
    public double getTime() {
        return time;
    }
    public Query getQuery() {
        return query;
    }

    public String toString(){
        String string = "Event's information:\n\t-Type->" + type + "\n\t-Time->" + time;
        return string;
    }

    // ---------------------------------------------------------------------------------------------
    // -------------------------- End of the setters and getters section --------------------------
    // ---------------------------------------------------------------------------------------------
}


