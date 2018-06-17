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
        StatementType newStatementType = StatementType.SELECT;
        boolean readOnly = true;
        double submissionTime = clock + distribution.generate();

        double acumulated = rnd.nextDouble();

        if(0.3 <= acumulated && acumulated <= 0.55){
            newStatementType = StatementType.UPDATE;
            readOnly = false;
        }else{
            if(0.55 < acumulated && acumulated <= 0.9){
                newStatementType = StatementType.JOIN;
            }else{
                if(0.9 < acumulated && acumulated <= 1.0){
                    newStatementType = StatementType.DDL;
                    readOnly = false;
                }
            }
        }


        Query query = new Query(submissionTime,newStatementType,readOnly);

        return query;
    }

    public void setDistribution(ExpDistributionGenerator distribution) {
        this.distribution = distribution;
    }

    public ExpDistributionGenerator getDistribution() {
        return distribution;
    }
}
