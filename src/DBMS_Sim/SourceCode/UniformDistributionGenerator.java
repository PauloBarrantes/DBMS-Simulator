package DBMS_Sim.SourceCode;

import java.util.Random;

public class UniformDistributionGenerator implements RandomVariableGenerator{
    private double leftBoundary;
    private double rightBoundary;
    private Random rnd = new Random(System.currentTimeMillis());


    public UniformDistributionGenerator(double leftBoundary, double rightBoundary){
        this.leftBoundary = leftBoundary;
        this.rightBoundary = rightBoundary;
    }

    public void setLeftBoundary(double leftBoundary){
        this.leftBoundary = leftBoundary;
    }

    public void setRightBoundary(double rightBoundary){
        this.rightBoundary = rightBoundary;
    }

    public double getLeftBoundary(){
        return leftBoundary;
    }

    public double getRightBoundary(){
        return rightBoundary;
    }

    @Override
    public double generate() {
        double r = rnd.nextDouble();
        return rightBoundary + (leftBoundary - rightBoundary) * r;
    }
}
