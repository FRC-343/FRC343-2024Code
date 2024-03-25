package frc.robot.Commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class IntakeTimedStopCommand extends Command {
    private final Intake m_intake;

    private final Timer time;

    private double kIntakeSpeed;
 

    public IntakeTimedStopCommand(double intakeSpeed, boolean raise) {
        m_intake = Intake.getInstance();
        kIntakeSpeed = intakeSpeed;
        time = new Timer();

        addRequirements(m_intake);
    }

    public IntakeTimedStopCommand() {
        this(-10, true); // defaults to .8 speed
    }

    public IntakeTimedStopCommand(double intakeSpeed) {
        this(intakeSpeed, true);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        time.start();
        time.reset();
  
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_intake.setIntake(kIntakeSpeed);

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_intake.setIntake(0);

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return (m_intake.getNoteDetector() == false || time.get() > 1.2 );
    }

}