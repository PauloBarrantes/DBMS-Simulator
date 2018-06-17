package DBMS_Sim.ChangeThisName;

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
public class Query {
    private double submissionTime;
    private double moduleEntryTime;
    private Statement statement;
    private boolean readOnly;

    public double elapsedTimeInSystem(double gg){ return 0.0; }
    public double elapsedTimeInModule(double gg){
        return 0.0;
    }

    public void setSubmissionTime(double submissionTime) {
        this.submissionTime = submissionTime;
    }

    public void setModuleEntryTime(double moduleEntryTime) {
        this.moduleEntryTime = moduleEntryTime;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public double getSubmissionTime() {
        return submissionTime;
    }

    public double getModuleEntryTime() {
        return moduleEntryTime;
    }

    public Statement getStatement() {
        return statement;
    }

    public boolean isReadOnly() {
        return readOnly;
    }
}
