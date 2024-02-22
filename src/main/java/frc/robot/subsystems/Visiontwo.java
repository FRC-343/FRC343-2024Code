package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//18.2 degrees

public class Visiontwo extends SubsystemBase {
    private static final Visiontwo m_instance = new Visiontwo();

    private final NetworkTable table = NetworkTableInstance.getDefault().getTable("Shooter LimeLight");
    private final NetworkTableEntry tx = table.getEntry("tx");
    private final NetworkTableEntry ty = table.getEntry("ty");

    double angle = 0.0;

    public static Visiontwo getInstance() {
        return m_instance;
    }

    public double getTx() {
        return tx.getDouble(0.0);
    }

    public double getTy() {
        return ty.getDouble(0.0);
    }

    public double getTv() {
        return table.getEntry("tv").getDouble(0.0);
    }

    public double getThor() { //possibly with a hammer
        return table.getEntry("thor").getDouble(0.0);
    }

    public boolean isAimed(double precision) {
        return Math.abs(getTx()) < precision;
    }

    public double getId() {
        return table.getEntry("tid").getDouble(0.0);
    }

    public void setLEDS(boolean turnOn) {
        if (turnOn) {
            table.getEntry("ledMode").setNumber(3);
        } else {
            table.getEntry("ledMode").setNumber(1);
        }
    }
    
    public double AimMath(){
  
         if (getTv() == 1){

           
        if (getTy() > 6.9) { // 55 rps
         angle = 10;//3581.55 + getTy() * -453.53 + getTy() * getTy() * 25.3331 + getTy() * getTy() * getTy() * -0.5068; 
           
       } else if (getTy() > 2.0 && getTy() <= 6.9) { // 60 rps
         angle = 30;//16.1772 * getTy() * getTy() + -327.65 * getTy() + 2500;
        } else if (getTy() <= 2.0) { // 65 rps
           angle = 34;//(2176.33 + -84.183 * Math.pow(0.77463, getTy()));
           SmartDashboard.putNumber("Ty when called", getTy());
         }
         }

return angle;
}
    public void killYourEnimiesViaLEDS() {
        table.getEntry("ledMode").setNumber(2);
    }

    public void setLEDSToDefault() {
        table.getEntry("ledMode").setNumber(0);
    }

    //this is for second camera plugged into limelight
    public void setCamera(double value) {//vaulue = 0 split, 1 = secondary camera is small, 2 = limelight is small
        table.getEntry("stream").setNumber(0);
    }
}
