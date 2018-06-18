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
public class Query {
    private double submissionTime;
    private double moduleEntryTime;
    private StatementType statementType;
    private boolean readOnly;


    // ---------------------------------------------------------------------------------------------
    // ----------------------------- Beginning of constructors section -----------------------------
    // ---------------------------------------------------------------------------------------------

    public Query(double submissionTime, StatementType statementType, boolean readOnly) {
        setSubmissionTime(submissionTime);
        setStatementType(statementType);
        setReadOnly(readOnly);
        setModuleEntryTime(-1);
    }

    // ---------------------------------------------------------------------------------------------
    // ------------------------------ End of the constructors section ------------------------------
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // ----------------------- Beginning of the setters and getters section -----------------------
    // ---------------------------------------------------------------------------------------------

    public void setSubmissionTime(double submissionTime) {
        this.submissionTime = submissionTime;
    }
    public void setModuleEntryTime(double moduleEntryTime) {
        this.moduleEntryTime = moduleEntryTime;
    }
    public void setStatementType(StatementType statementType) {
        this.statementType = statementType;
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
    public StatementType getStatementType() {
        return statementType;
    }
    public boolean getReadOnly() {
        return readOnly;
    }

    // ---------------------------------------------------------------------------------------------
    // -------------------------- End of the setters and getters section --------------------------
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // ------------------------------- Beginning of methods section -------------------------------
    // ---------------------------------------------------------------------------------------------

    public double elapsedTimeInSystem(double clock){ return clock - submissionTime; }
    public double elapsedTimeInModule(double clock){
        return clock - moduleEntryTime;
    }

    // ---------------------------------------------------------------------------------------------
    // -------------------------------- End of the methods section --------------------------------
    // ---------------------------------------------------------------------------------------------
}
