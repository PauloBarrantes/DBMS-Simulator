package DBMS_Sim.Backend;

public class NormalDistributionGenerator implements RandomVariableGenerator{
    private double mean;
    private double variance;

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
        return 0;
    }
}
