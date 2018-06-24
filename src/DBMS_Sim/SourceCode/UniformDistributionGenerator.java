package DBMS_Sim.SourceCode;

import java.util.Random;

public class UniformDistributionGenerator implements RandomVariableGenerator{
    private double leftBoundary;
    private double rightBoundary;
    private Random rnd = new Random(System.currentTimeMillis());


    public UniformDistributionGenerator(double boundary1, double boundary2){
        if(boundary1 <= boundary2){
            setLeftBoundary(boundary1);
            setRightBoundary(boundary2);
        }else{
            setLeftBoundary(boundary2);
            setRightBoundary(boundary1);
        }
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
        double numRandom = rnd.nextDouble();
        //System.out.println(numRandom);
        return leftBoundary + (rightBoundary - leftBoundary) * numRandom;
    }

}
