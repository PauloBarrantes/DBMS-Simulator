package DBMS_Sim.Backend;

import DBMS_Sim.NotBeingUsed.Statement;
import DBMS_Sim.Backend.ExpDistributionGenerator;



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
public class QueryGenerator {
    private ExpDistributionGenerator distribution;


    public Statement generate(){
        Statement st = Statement.DDL;
        return st;
    }
}
