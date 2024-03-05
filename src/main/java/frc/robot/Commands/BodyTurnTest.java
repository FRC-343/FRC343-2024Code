package frc.robot.Commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterAngle;
import frc.robot.subsystems.Visiontwo;

public class BodyTurnTest extends Command implements Runnable{
    private final DriveSubsystem m_drive = new DriveSubsystem();
    private final Visiontwo m_vision;
   
    public double leftSpeedvar = 0.0;
    public double rightSpeedvar= 0.0;
    public boolean chargestationbalance = false;
    double setpoint = 0;
    double errorSum = 0;
    double lastTimestamp = 0;
    double lastError = 0;
    double berror = 0;
    double errorRate = 0;
    double biLimit = 3 ;
   

    private Pose2d m_startPose = new Pose2d(0, 0, new Rotation2d(0));

    public BodyTurnTest() {
        // m_turret = Turret.getInstance();
         m_vision = Visiontwo.getInstance();

         addRequirements();

        // turretPidContoller.setIntegratorRange(-5, 5);
    }


    @Override
    public void initialize() {
        m_startPose = m_drive.getPose();
        m_drive.drive(0, 0, 0, true, true);

    }

    @Override
    public void execute() {
        double pitch = m_vision.getTx();
       
        if (pitch <.9 && pitch>.9){
            chargestationbalance=true;
            leftSpeedvar = 0;
            rightSpeedvar = 0;
           }
       
            else {
                setpoint = 0;
       
                // get sensor position
                Double sensorPosition = pitch;
       
                // calculations
                berror = setpoint - sensorPosition;
                double dt = Timer.getFPGATimestamp()- lastTimestamp;
       
                
               if (Math.abs(berror) < biLimit) {
                errorSum += berror * dt;
                }
      
                errorRate = (berror - lastError) / dt;
     
                Double leftoutputSpeed = (m_drive.getDriveXVelocity()) * berror + m_drive.getDriveYVelocity() * errorSum + m_drive.getDriveAngularVelocity() * errorRate;
                //Double RightoutputSpeed = m_drive.getRightP() * berror + m_drive.getRightI() * errorSum + m_drive.getRightD() * errorRate;
            
                // output to motors
                leftSpeedvar=(leftoutputSpeed);
                //rightSpeedvar = (RightoutputSpeed);
                 
                
                // update last- variables
                lastTimestamp = Timer.getFPGATimestamp();
                lastError = berror;
        }
                // if (leftSpeedvar > .2 && rightSpeedvar > .2){
                //     leftSpeedvar = .2;
                //     rightSpeedvar = .2;
                // }
               
                // if (leftSpeedvar < -.2 && rightSpeedvar < -.2){
                //         leftSpeedvar = -.2;
                //         rightSpeedvar = -.2;
                // }
     
                // m_drive.drive(0, 0, .3, true, true);
                // m_drive.testLeftValue(leftSpeedvar);
                // m_drive.testRightValue(rightSpeedvar);
            }
     
    

    @Override
    public void end(boolean interrupted) {
        m_drive.drive(0, 0, 0, interrupted, interrupted);
    }

    @Override
    public boolean isFinished() {
<<<<<<< Updated upstream
        return false;
=======
        boolean value = false;
        if(m_vision.getTx() <= .9 && m_vision.getTx() >= -.9){
            value = true;
        }
        return value;
>>>>>>> Stashed changes
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

}