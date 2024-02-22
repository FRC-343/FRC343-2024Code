package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//18.2 degrees

public class Vision extends SubsystemBase {
    private static final Vision m_instance = new Vision();

    private final NetworkTable table1 = NetworkTableInstance.getDefault().getTable("Intake limelight");
    private final NetworkTableEntry tx = table1.getEntry("tx");
    private final NetworkTableEntry ty = table1.getEntry("ty");

    double angle;

    public static Vision getInstance() {
        return m_instance;
    }
    
    public double getTx() {
        return tx.getDouble(0.0);
    }

    public double getTy() {
        return ty.getDouble(0.0);
    }

    public double getTv() {
        return table1.getEntry("tv").getDouble(0.0);
    }

    public double getThor() { //possibly with a hammer
        return table1.getEntry("thor").getDouble(0.0);
    }

    public boolean isAimed(double precision) {
        return true;
    }



    public void setLEDS(boolean turnOn) {
        if (turnOn) {
            table1.getEntry("ledMode").setNumber(3);
        } else {
            table1.getEntry("ledMode").setNumber(1);
        }
    }
    public double AimMath(){
  
        // if (getId() == 3.0){

           
        if (getTy() > 6.9) { // 55 rps
         angle = 10;//3581.55 + getTy() * -453.53 + getTy() * getTy() * 25.3331 + getTy() * getTy() * getTy() * -0.5068; 
           
       } else if (getTy() > 2.0) { // 60 rps
         angle = 30;//16.1772 * getTy() * getTy() + -327.65 * getTy() + 2500;
        } else if (getTy() <= 2.0) { // 65 rps
           angle = 50;//(2176.33 + -84.183 * Math.pow(0.77463, getTy()));
         }
        // }

return angle;
}
    // public void killYourEnimiesViaLEDS() {
    //     table.getEntry("ledMode").setNumber(2);
    // }

    // public void setLEDSToDefault() {
    //     table.getEntry("ledMode").setNumber(0);
    // }

    //this is for second camera plugged into limelight
    public void setCamera(double value) {//vaulue = 0 split, 1 = secondary camera is small, 2 = limelight is small
        table1.getEntry("stream").setNumber(value);
    }
}