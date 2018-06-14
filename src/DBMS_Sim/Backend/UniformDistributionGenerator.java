package DBMS_Sim.Backend;

public class UniformDistributionGenerator implements RandomVariableGenerator{
    private double leftBoudary;
    private double rightBoudary;

    public void setLeftBoudary(double leftBoudary){
        this.leftBoudary = leftBoudary;
    }

    public void setRightBoudary(double rightBoudary){
        this.rightBoudary = rightBoudary;
    }

    public double getLeftBoudary(){
        return leftBoudary;
    }

    public double getRightBoudary(){
        return rightBoudary;
    }

    @Override
    public double generate() {
        return 0;
    }
}
