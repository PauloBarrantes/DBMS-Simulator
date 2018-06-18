package DBMS_Sim.SourceCode;

import java.util.PriorityQueue;

public class ExecutionModule extends Module{


    public ExecutionModule(int maxFields, double timeout){
        super(maxFields,0,new PriorityQueue<Query>(),new double[NUMSTATEMENTS],timeout,new int[NUMSTATEMENTS]);
    }


    public void processArrival(Query query){

    }

    public void processDeparture(Query query) {

    }
}
