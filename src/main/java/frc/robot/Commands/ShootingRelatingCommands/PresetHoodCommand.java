package frc.robot.Commands.ShootingRelatingCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterAngle;

// import frc.robot.subsystems.Hood;

public class PresetHoodCommand extends Command {

    // private final Hood m_hood;

    private final ShooterAngle m_hood;

    private boolean m_startWithZeroing;

    private double kTarget;
    
    public PresetHoodCommand(double target, boolean startWithZeroing) {
         m_hood = ShooterAngle.getInstance();
         kTarget = target;
         m_startWithZeroing = startWithZeroing;
         addRequirements(m_hood);
    }

    public PresetHoodCommand(double target) {
        this(target, false);
    }

    @Override
    public void execute() {
         m_hood.aim(kTarget, m_startWithZeroing);
    }

    @Override
    public void end(boolean interrupted) {
         m_hood.stop();
    }

    @Override
    public boolean isFinished() {
        return m_hood.isAimed();
    }

}
