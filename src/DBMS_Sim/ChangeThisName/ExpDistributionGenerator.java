package DBMS_Sim.ChangeThisName;

public class ExpDistributionGenerator implements RandomVariableGenerator{
    private double lambda;

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

