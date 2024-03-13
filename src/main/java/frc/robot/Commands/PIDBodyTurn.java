package frc.robot.Commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Visiontwo;

public class PIDBodyTurn extends Command{
        private final DriveSubsystem m_drive;
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


    public PIDBodyTurn(){
        m_drive = DriveSubsystem.getInstance();
        m_vision = Visiontwo.getInstance();
        addRequirements(m_drive);
  
    }

    @Override
    public void initialize() {
        m_startPose = m_drive.getPose();
        m_drive.drive(0, 0, 0, true , true);

    }

    @Override
    public void execute() {
        double pitch = m_vision.getTx();
       
        if (pitch <-2 && pitch>-4){
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
     
                Double leftoutputSpeed = (Constants.ModuleConstants.kDrivingP*2) * berror + Constants.ModuleConstants.kDrivingI * errorSum + Constants.ModuleConstants.kDrivingD * errorRate;
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
     
                m_drive.drive(0, 0, leftSpeedvar, true, true);

            }
     
    

    @Override
    public void end(boolean interrupted) {
        m_drive.drive(0, 0, 0, true, true);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}

