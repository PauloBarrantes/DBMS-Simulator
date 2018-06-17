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
public interface Module {

    void resetVariables();
    void processArrival(Query query);
    void processDeparture(Query query);
    void checkQueues(double clock);
}
