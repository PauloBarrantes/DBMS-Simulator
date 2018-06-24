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
    private int loadedBlocks;
    private double moduleEntryTime;
    private boolean readOnly;
    private int statementType;
    private double submissionTime;



    // ---------------------------------------------------------------------------------------------
    // ----------------------------- Beginning of constructors section -----------------------------
    // ---------------------------------------------------------------------------------------------

    public Query(boolean readOnly, int statementType, double submissionTime) {
        setLoadedBlocks(0);
        setReadOnly(readOnly);
        setSubmissionTime(submissionTime);
        setStatementType(statementType);
        setModuleEntryTime(-1);
    }

    // ---------------------------------------------------------------------------------------------
    // ------------------------------ End of the constructors section ------------------------------
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // ----------------------- Beginning of the setters and getters section -----------------------
    // ---------------------------------------------------------------------------------------------


    public void setLoadedBlocks(int loadedBlocks) { this.loadedBlocks = loadedBlocks; }
    public void setModuleEntryTime(double moduleEntryTime) {
        this.moduleEntryTime = moduleEntryTime;
    }
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
    public void setStatementType(int statementType) {
        this.statementType = statementType;
    }
    public void setSubmissionTime(double submissionTime) {
        this.submissionTime = submissionTime;
    }

    public int getLoadedBlocks() { return loadedBlocks; }
    public double getModuleEntryTime() { return moduleEntryTime; }
    public boolean getReadOnly() {
        return readOnly;
    }
    public double getSubmissionTime() {
        return submissionTime;
    }
    public int getStatementType() {
        return statementType;
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
