package DBMS_Sim.SourceCode;

public enum EventType {
    QueryArrival,
    ArriveClientToModule, ExitClientModule,
    ArriveToProcessAdminModule, ExitProcessAdminModule,
    ArriveToQueryProcessingModule,LexicalValidation, SintacticalValidation, SemanticValidation, PermissionVerification, QueryOptimization,
    ArriveToTransactionModule, ExitTransactionModule,
    ArriveToExecutionModule, ExitExecutionModule,
    ShowResult,
    Finalization
}
