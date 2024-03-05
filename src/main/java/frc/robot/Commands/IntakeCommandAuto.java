package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class IntakeCommandAuto extends Command {
    private final Intake m_intake;

    private double kIntakeSpeed;
 

    public IntakeCommandAuto(double intakeSpeed, boolean raise) {
        m_intake = Intake.getInstance();
        kIntakeSpeed = intakeSpeed;

        addRequirements(m_intake);
    }

    public IntakeCommandAuto() {
        this(-8, true); // defaults to .8 speed
    }

    public IntakeCommandAuto(double intakeSpeed) {
        this(intakeSpeed, true);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
  
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_intake.setIntakeAuto(kIntakeSpeed);

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_intake.setIntake(0);

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

}