package DBMS_Sim.ChangeThisName;

public enum EventType {
    QueryArrival,
    ArriveClientToModule, ExitClientModule,
    ArriveToProcessAdminModule, ExitProcessAdminModule,
    ArriveToQueryProcessingModule, LexicalValidation, SintacticalValidation, SemanticValidation, PermissionVerification, QueryOptimization, ExitQueryProcessingModule,
    ArriveToTransactionModule, ExitTransactionModule,
    ArriveToExecutionModule, ExitExecutionModule,
    ShowResult,
    Finalization
}