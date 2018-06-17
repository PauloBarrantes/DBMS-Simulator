package DBMS_Sim.SourceCode;


import java.util.Random;

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
    private Random rnd = new Random(System.currentTimeMillis());

    public QueryGenerator(){
        //The media is 30 queries each minute, that means that in one second it recieves
        //0.5 queries.
        distribution = new ExpDistributionGenerator(0.5);
    }

    public Query generate(double clock){
        Statement newStatement = Statement.SELECT;
        boolean readOnly = true;

        double acumulated = rnd.nextDouble();

        if(0.3 <= acumulated <= 0.55){
            Statement newStatement = Statement.UPDATE;
        }else{
            if(0.55 < acumulated <= 0.9){
                Statement newStatement = Statement.JOIN;
            }else{
                if(0.9 < acumulated <= 1.0){
                    Statement newStatement = Statement.DDL;
                }
            }
        }

        return st;
    }

    public void setDistribution(ExpDistributionGenerator distribution) {
        this.distribution = distribution;
    }

    public ExpDistributionGenerator getDistribution() {
        return distribution;
    }
}
