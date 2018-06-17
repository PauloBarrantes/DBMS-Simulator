package DBMS_Sim.SourceCode;

public class ExpDistributionGenerator implements RandomVariableGenerator{
    private double lambda;

    public ExpDistributionGenerator(){

    }

    public void setLambda(double lambda){
        this.lambda = lambda;
    }

    public  double getLambda(){
        return lambda;
    }

    @Override
    public double generate() {
        return 0;
    }
}

