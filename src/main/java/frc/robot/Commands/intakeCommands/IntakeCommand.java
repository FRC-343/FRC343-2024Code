package frc.robot.Commands.intakeCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class IntakeCommand extends Command {
    private final Intake m_intake;

    private double kIntakeSpeed;
 

    public IntakeCommand(double intakeSpeed, boolean raise) {
        m_intake = Intake.getInstance();
        kIntakeSpeed = intakeSpeed;

        addRequirements(m_intake);
    }

    public IntakeCommand() {
        this(-10, true); // defaults to .8 speed
    }

    public IntakeCommand(double intakeSpeed) {
        this(intakeSpeed, true);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
  
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
        return m_intake.getNoteDetector2() == false;
    }

}