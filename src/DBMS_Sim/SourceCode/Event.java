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
public class Event {
    private EventType type;
    private double time;
    private Query query;

    public Event(EventType type, double time, Query query){
        this.setType(type);
        this.setTime(time);
        this.setQuery(query);
    }

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
}


