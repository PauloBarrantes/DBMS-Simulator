package DBMS_Sim.SourceCode;

public enum EventType {
    ArriveClientToModule, ExitClientModule,
    ArriveToProcessAdminModule, ExitProcessAdminModule,
    ArriveToQueryProcessingModule,LexicalValidation, SintacticalValidation, SemanticValidation, PermissionVerification, QueryOptimization, ExitQueryProcessingModule,
    ArriveToTransactionModule, ExitTransactionModule,
    ArriveToExecutionModule, ExecuteQuery, ExitExecutionModule,
    ShowResult

}
