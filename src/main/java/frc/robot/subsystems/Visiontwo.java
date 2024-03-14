package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.lang.annotation.Target;

import org.ejml.equation.Variable;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonTargetSortMode;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;    

//18.2 degrees

public class Visiontwo extends SubsystemBase {
    private static final Visiontwo m_instance = new Visiontwo();

    private LEDs m_LEDs = LEDs.getInstance();

    public final PhotonCamera camera = new PhotonCamera("ShooterCam");
     

    private final NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight-shooter");
    private final NetworkTableEntry tx = table.getEntry("tx");
    private final NetworkTableEntry ty = table.getEntry("ty");




    double angle = 0.0;


    public static Visiontwo getInstance() {
        return m_instance;
    }

    public double getTx() {
        if(camera.getLatestResult().hasTargets() == true){//} && (getId() == (4) || getId() == 7)){
        return camera.getLatestResult().getBestTarget().getYaw();
        } else {
            return 0;
        }
    }

    public double getTy() {
       if (camera.getLatestResult().hasTargets() == true){// && (getId() == (4) || getId() == 7)){
        return camera.getLatestResult().getBestTarget().getPitch();
       } else {
        return 0.0;
       }
    }

    public boolean getTv() {
        if (camera.getLatestResult().hasTargets() == true )//&& (getId() == (4) || getId() == 7))
        return camera.getLatestResult().hasTargets();
        return false;
    }


    // public double getThor() { //possibly with a hammer
    // //     return table.getEntry("thor").getDouble(0.0);
    // // }

    public boolean isAimed(double precision) {
        if (camera.getLatestResult().hasTargets() == true)// && (getId() == (4) || getId() == 7))
        return Math.abs(getTx()) < precision;
        return false;
    }

    public double getId() {
        if (camera.getLatestResult().hasTargets() == true)
        return camera.getLatestResult().getBestTarget().getFiducialId();
        return 0.0;
    }

 

    // public void setLEDS(boolean turnOn) {
    //     if (turnOn) {
    //         table.getEntry("ledMode").setNumber(3);
    //     } else {
    //         table.getEntry("ledMode").setNumber(1);
    //     }
    // }
    
    public double AimMath(){
            if (camera.getLatestResult().hasTargets() == true )//&& (getId() == (4) || getId() == 7))
            angle = (4.13614  * getTy() + 56.67256) + 6;

         SmartDashboard.putNumber("Ty", getTy());
         SmartDashboard.putNumber("Tx", getTx());
        

return angle;
}
    // public void killYourEnimiesViaLEDS() {
    //     table.getEntry("ledMode").setNumber(2);
    // }

    // public void setLEDSToDefault() {
    //     table.getEntry("ledMode").setNumber(0);
    // }

    //this is for second camera plugged into limelight
    // public void setCamera(double value) {//vaulue = 0 split, 1 = secondary camera is small, 2 = limelight is small
    //     table.getEntry("stream").setNumber(0);
    // }


    @Override
    public void periodic() {
    
        // if (target.getFiducialId() == 3 || target.getFiducialId() == 7){

        // }

    

     if(getTv() == true){
         m_LEDs.target();
     }
    //               var alliance = DriverStation.getAlliance();
    //           if (alliance.isPresent()) {
    //             if (alliance.get() == DriverStation.Alliance.Red){
    //               NetworkTableEntry pipline = table.getEntry("pipeline");
    //               pipline.setNumber(0);
    //             } else if (alliance.get() == DriverStation.Alliance.Blue){
    //               NetworkTableEntry pipline = table.getEntry("pipeline");
    //               pipline.setNumber(1);                    
    //           }
    //         }
    }
    

}
