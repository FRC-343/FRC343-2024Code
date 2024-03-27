package frc.robot.Commands;


import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Visiontwo;

public class BodyTurnTest extends Command {

    private final DriveSubsystem m_DriveSubsystem;
     private final Visiontwo m_vision;

    public BodyTurnTest() {
        m_DriveSubsystem = DriveSubsystem.getInstance();
        m_vision = Visiontwo.getInstance();
        addRequirements(m_DriveSubsystem);
    }
    

    @Override
    public void execute() {
        if(m_vision.getTv() == true){
        m_DriveSubsystem.AimBody();
        }
        
    }

    @Override
    public void end(boolean interrupted) {
        
    }

    @Override
    public boolean isFinished() {
        boolean value = false;
        if(m_vision.getTx() <= 1 && m_vision.getTx() >= -1){
            value = true;
        }
        return value;

    }



}