package frc.robot.Commands.intakeCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.Intake;
import frc.robot.subsystems.ShooterAngle;
import frc.robot.subsystems.Visiontwo;

public class inatkeCommandFD extends Command{
        private final Intake m_intake;
        private final ShooterAngle m_ShooterAngle;
        private final Visiontwo m_vision;

        private Timer isGoodTime = new Timer();
    private double kIntakeSpeed;
 

    public inatkeCommandFD(double intakeSpeed, boolean raise) {
        m_intake = Intake.getInstance();
        m_ShooterAngle = ShooterAngle.getInstance();
        m_vision = Visiontwo.getInstance();
        kIntakeSpeed = intakeSpeed;

        addRequirements(m_intake);
    }



    public inatkeCommandFD() {
        this(-10, true); // defaults to .8 speed
    }

    public inatkeCommandFD(double intakeSpeed) {
        this(intakeSpeed, true);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        isGoodTime.start();
        isGoodTime.reset();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
            if (m_vision.camera.getLatestResult().hasTargets() == true){
              if(( m_ShooterAngle.isAimed() == true &&(m_vision.getTx() <=1 && m_vision.getTx() >= -1))){
            m_intake.setIntakeAuto(kIntakeSpeed);
        }
      }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_intake.setIntake(0);

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return ((m_intake.getNoteDetector()== true));
    }

}
