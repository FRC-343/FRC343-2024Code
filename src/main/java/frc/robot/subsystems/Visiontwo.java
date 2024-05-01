package frc.robot.subsystems;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.lang.annotation.Target;
import java.util.Optional;

import org.ejml.equation.Variable;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonTargetSortMode;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.PhotonPoseEstimator;

//18.2 degrees

public class Visiontwo extends SubsystemBase {
    private static final Visiontwo m_instance = new Visiontwo();

  


    private static AprilTagFieldLayout aprilTagFieldLayout = AprilTagFields.k2024Crescendo.loadAprilTagLayoutField();

    private LEDs m_LEDs = LEDs.getInstance();

    public static final PhotonCamera camera = new PhotonCamera("ShooterCam");

    private static Transform3d robotToCam = new Transform3d(new Translation3d(0.5, 0.0, 0.5), new Rotation3d(0,0,0)); //Cam mounted facing forward, half a meter forward of center, half a meter up from center.

    private static PhotonPoseEstimator photonPoseEstimator = new PhotonPoseEstimator(aprilTagFieldLayout, PoseStrategy.CLOSEST_TO_REFERENCE_POSE, camera, robotToCam);
    // private final NetworkTable table =
    // NetworkTableInstance.getDefault().getTable("limelight-shooter");
    // private final NetworkTableEntry tx = table.getEntry("tx");
    // private final NetworkTableEntry ty = table.getEntry("ty");

    double angle = 0.0;

    public static Visiontwo getInstance() {
        return m_instance;
    }

        public Optional<EstimatedRobotPose> getEstimatedGlobalPose(Pose2d prevEstimatedRobotPose) {
        photonPoseEstimator.setReferencePose(prevEstimatedRobotPose);
        return photonPoseEstimator.update();
    }

    public double getTx() {
        try {
            PhotonPipelineResult frame = camera.getLatestResult();
            if (frame.hasTargets()) {
                return frame.getBestTarget().getYaw();
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
        // if(camera.getLatestResult().hasTargets()){//} && (getId() == (4) || getId()
        // == 7)){
        // return camera.getLatestResult().getBestTarget().getYaw();
        // } else {
        // return 0;
        // }
    }

    public double getTy() {
         try {
            PhotonPipelineResult frame = camera.getLatestResult();
            if (frame.hasTargets()) {
                return frame.getBestTarget().getYaw();
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }
    public boolean getTv() {
        if (camera.getLatestResult().hasTargets() == true)// && (getId() == (4) || getId() == 7))
            return camera.getLatestResult().hasTargets();
        return false;
    }

    // public double getThor() { //possibly with a hammer
    // // return table.getEntry("thor").getDouble(0.0);
    // // }

    public boolean isAimed(double precision) {
        if (camera.getLatestResult().hasTargets() == true)// && (getId() == (4) || getId() == 7))
            return Math.abs(getTx()) < precision;
        return false;
    }

    public double getId() {
        try {
            PhotonPipelineResult frame = camera.getLatestResult();
            if (frame.hasTargets()) {
                return frame.getBestTarget().getFiducialId();
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    // public void setLEDS(boolean turnOn) {
    // if (turnOn) {
    // table.getEntry("ledMode").setNumber(3);
    // } else {
    // table.getEntry("ledMode").setNumber(1);
    // }
    // }


    //.35

    public double AimMath() {
        // if (camera.getLatestResult().targets.stream().anyMatch((PhotonTrackedTarget
        // target) -> {
        // return target.getFiducialId() == 4 || target.getFiducialId() == 7;
        // })) {

        // }

        PhotonPipelineResult frame = camera.getLatestResult();

        frame.targets.stream().forEach((PhotonTrackedTarget target) -> {
            if (target.getFiducialId() == 1 || target.getFiducialId() == 7) {
                angle = (7.97224 * getTy() + 86.37707) - 12;
                // something?
            }
        });

        // if (camera.getLatestResult().hasTargets() == true && (getId() == (4) ||
        // getId() == 7))
        // angle = (7.97224 * getTy() + 86.37707)-12;

        // SmartDashboard.putNumber("Ty", getTy());
        // SmartDashboard.putNumber("Tx", getTx());

        return angle;
    }
    // public void killYourEnimiesViaLEDS() {
    // table.getEntry("ledMode").setNumber(2);
    // }

    // public void setLEDSToDefault() {
    // table.getEntry("ledMode").setNumber(0);
    // }

    // this is for second camera plugged into limelight
    // public void setCamera(double value) {//vaulue = 0 split, 1 = secondary camera
    // is small, 2 = limelight is small
    // table.getEntry("stream").setNumber(0);
    // }

    @Override
    public void periodic() {

        // if (target.getFiducialId() == 3 || target.getFiducialId() == 7){

        // }
        if (getTv() == true) {
            SmartDashboard.putNumber("Ty", getTy());
            SmartDashboard.putNumber("Tx", getTx());
        }
        if (getTv() == true) {
            if ((getTx() < 1 && getTx() > -1) && getTv() == true) {
                m_LEDs.target();
            }
        }
        // var alliance = DriverStation.getAlliance();
        // if (alliance.isPresent()) {
        // if (alliance.get() == DriverStation.Alliance.Red){
        // NetworkTableEntry pipline = table.getEntry("pipeline");
        // pipline.setNumber(0);
        // } else if (alliance.get() == DriverStation.Alliance.Blue){
        // NetworkTableEntry pipline = table.getEntry("pipeline");
        // pipline.setNumber(1);
        // }
        // }
    }

}
