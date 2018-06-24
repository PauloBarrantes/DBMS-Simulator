package DBMS_Sim.SourceCode;

import java.util.Random;

public class ExpDistributionGenerator implements RandomVariableGenerator{
    private double mean;
    private double lambda;
    private Random rnd = new Random(System.currentTimeMillis());


    public ExpDistributionGenerator(double lambda){
        setLambda(lambda);
        setMean(1/lambda);
    }

    public void setLambda(double lambda){
        this.lambda = lambda;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public  double getLambda(){
        return lambda;
    }

    public double getMean() {
        return mean;
    }



    @Override
    public double generate() {
        double numRandom = rnd.nextDouble();
        //System.out.println(numRandom);
        return (-lambda)*Math.log(1-numRandom);
    }
    /*
    public static void main(String[] args) {
        ExpDistributionGenerator distribution = new ExpDistributionGenerator(1.5);
        System.out.println(distribution.generate());
        System.out.println(distribution.generate());
        System.out.println(distribution.generate());
    }
    */
}

