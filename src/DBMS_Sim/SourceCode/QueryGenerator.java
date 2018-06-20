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
    private StatementType statementType;

    // ---------------------------------------------------------------------------------------------
    // ----------------------------- Beginning of constructors section -----------------------------
    // ---------------------------------------------------------------------------------------------

    public QueryGenerator(){
        //The media is 30 queries each minute, that means that in one second it recieves
        //0.5 queries.
        distribution = new ExpDistributionGenerator(2);
    }

    // ---------------------------------------------------------------------------------------------
    // ------------------------------ End of the constructors section ------------------------------
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // ----------------------- Beginning of the setters and getters section -----------------------
    // ---------------------------------------------------------------------------------------------

    public void setDistribution(ExpDistributionGenerator distribution) {
        this.distribution = distribution;
    }

    public ExpDistributionGenerator getDistribution() {
        return distribution;
    }

    // ---------------------------------------------------------------------------------------------
    // -------------------------- End of the setters and getters section --------------------------
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // ------------------------------- Beginning of methods section -------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     * @param clock, current clock time.
     * @return the new query that is generated
     * @function generates queries randomly.
     */
    public Query generate(double clock){
        int newStatementType = statementType.SELECT;
        boolean readOnly = true;
        double submissionTime = clock + distribution.generate();

        double numRandom = rnd.nextDouble();
        //System.out.println(numRandom);

        if(0.3 <= numRandom && numRandom <= 0.55){
            newStatementType = statementType.UPDATE;
            readOnly = false;
        }else{
            if(0.55 < numRandom && numRandom <= 0.9){
                newStatementType = statementType.JOIN;
            }else{
                newStatementType = statementType.DDL;
                readOnly = false;
            }
        }

        Query query = new Query(readOnly,newStatementType,submissionTime);
        return query;
    }

    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------

    /*
    public static void main(String[] args){
        QueryGenerator generator = new QueryGenerator();
        Query query = generator.generate(0);
        System.out.println(query.toString());
        query = generator.generate(1.5);
        System.out.println(query.toString());
        query = generator.generate(6);
        System.out.println(query.toString());
    }
    */
}
