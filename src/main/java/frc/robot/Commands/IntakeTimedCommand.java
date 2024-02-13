package frc.robot.Commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class IntakeTimedCommand extends Command {
    private final Intake m_intake;
    private final Timer t;

    private final double time;
    private final double kIntakeSpeed;

    public IntakeTimedCommand(double intakeSpeed, double time) {
        m_intake = Intake.getInstance();
        addRequirements(m_intake);

        kIntakeSpeed = intakeSpeed;
        t = new Timer();
        this.time = time; // time to run the intake 
    }

    public IntakeTimedCommand(double intakeSpeed) {
        this(intakeSpeed, Math.sqrt(2) / 2); //defaults time to this
    }

    public IntakeTimedCommand() {
        this(0.8); // defaults to .8 speed
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        t.reset();
        t.start();
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
        if (t.get() >= time) {
            return true;
        } else {
            return false;
        }

    }
}