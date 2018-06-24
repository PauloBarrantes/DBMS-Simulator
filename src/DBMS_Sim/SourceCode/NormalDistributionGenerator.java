package DBMS_Sim.SourceCode;

import java.util.Random;

public class NormalDistributionGenerator implements RandomVariableGenerator{
    private double mean;
    private double variance;
    private double standardDeviation;
    private Random rnd = new Random(System.currentTimeMillis());


    public NormalDistributionGenerator(double mean, double variance){
        setMean(mean);
        setVariance(variance);
        setStandardDeviation(Math.sqrt(variance));
    }

    public void setMean(double mean){
        this.mean = mean;
    }

    public void setVariance(double variance){
        this.variance = variance;
    }

    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    @Override
    public double generate() {
        double zeta= 0;
        double numRandom;

        for(int i = 0; i <12 ; ++i){
            numRandom = rnd.nextDouble();
            zeta += numRandom;
        }
        //System.out.println(zeta);
        zeta = zeta - 6;

        return  mean + standardDeviation * zeta;
    }

}
