package DBMS_Sim.SourceCode;

import java.util.Random;

public class NormalDistributionGenerator implements RandomVariableGenerator{
    private double mean;
    private double variance;
    private double standardDeviation;
    private Random rnd = new Random(System.currentTimeMillis());


    public NormalDistributionGenerator(double mean, double variance, double deviation){
        this.mean = mean;
        this.variance= variance;
        this.standardDeviation = Math.sqrt(deviation);
    }

    public void setMean(double mean){
        this.mean = mean;
    }

    public void setVariance(double variance){
        this.variance = variance;
    }

    public double getMean(){
        return mean;
    }

    public double getVariance(){
        return variance;
    }

    @Override
    public double generate() {
        double zeta= 0;

        for(int i = 0; i <12 ; ++i){
            double numRandom = rnd.nextDouble();
            zeta += numRandom;
        }
        zeta = zeta - 6;
        double time = mean + standardDeviation * zeta;
        return time;
    }
}
