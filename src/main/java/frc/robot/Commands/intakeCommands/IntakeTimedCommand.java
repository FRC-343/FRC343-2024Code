package frc.robot.Commands.intakeCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Commands.ShootingRelatingCommands.ShootCommand;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterAngle;

public class IntakeTimedCommand extends Command {
    private final Intake m_intake;
    private final ShooterAngle m_ShooterAngle;
    private final Shooter m_Shooter;

    private final Timer t;

    private final double time;
    private final double kIntakeSpeed;

    public IntakeTimedCommand(double intakeSpeed, double time) {
        m_intake = Intake.getInstance();
        m_ShooterAngle = ShooterAngle.getInstance();
        m_Shooter = Shooter.getInstance();
        addRequirements(m_intake);

        kIntakeSpeed = intakeSpeed;
        t = new Timer();
        this.time = time; // time to run the intake 
    }

    public IntakeTimedCommand(double intakeSpeed) {
        this(intakeSpeed, Math.sqrt(2) / 2); //defaults time to this
    }

    public IntakeTimedCommand() {
        this(-10); // defaults to .8 speed
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
        if(m_ShooterAngle.isAimed() == true && m_Shooter.getBottomShooterRPS() > 75){
                m_intake.setIntakeAuto(kIntakeSpeed);
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
        return (m_intake.getNoteDetector()== true);

    }
}