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

    public double getMean(){
        return mean;
    }

    public double getVariance(){
        return variance;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }



    @Override
    public double generate() {
        double zeta= 0;
        double numRandom;

        for(int i = 0; i <12 ; ++i){
            numRandom = rnd.nextDouble();
            System.out.println(numRandom);
            zeta += numRandom;
        }
        //System.out.println(zeta);
        zeta = zeta - 6;

        return  mean + standardDeviation * zeta;
    }
    /*
    public static void main(String[] args) {
        NormalDistributionGenerator distribution = new NormalDistributionGenerator(3,0.10);
        System.out.println(distribution.generate());
        System.out.println(distribution.generate());
        System.out.println(distribution.generate());
    }
    */
}
