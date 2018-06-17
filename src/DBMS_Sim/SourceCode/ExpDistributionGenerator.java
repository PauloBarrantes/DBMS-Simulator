package DBMS_Sim.SourceCode;

import java.util.Random;

public class ExpDistributionGenerator implements RandomVariableGenerator{
    private double media ;
    private double lambda;
    private Random rnd = new Random(System.currentTimeMillis());


    public ExpDistributionGenerator(double media){
        this.lambda= 1/media;
        this.media = media;
    }

    public void setLambda(double lambda){
        this.lambda = lambda;
    }

    public  double getLambda(){
        return lambda;
    }

    @Override
    public double generate() {
        double numRandom = rnd.nextDouble();
        double time = (-1/media)*Math.log(1-numRandom);
        return time;
    }
}

