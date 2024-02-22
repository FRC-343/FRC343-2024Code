package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//18.2 degrees

public class Vision extends SubsystemBase {
    private static final Vision m_instance = new Vision();

    private final NetworkTable table1 = NetworkTableInstance.getDefault().getTable("ShooterPipe");
    private final NetworkTableEntry tx = table1.getEntry("tx");
    private final NetworkTableEntry ty = table1.getEntry("ty");

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