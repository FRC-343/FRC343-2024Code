package frc.robot.Commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;


import frc.robot.subsystems.Climber;

public class ClimberCommand extends Command {

    private double m_speed;
    private final Climber m_Climber;

    public ClimberCommand() {
        m_Climber = Climber.getInstance();
   
        addRequirements(m_Climber);
    }
    

    @Override
    public void execute() {
       
        double speed = (Math.abs(m_Climber.Encoder())+35) / 100.0; // equivilent to a PID (P only), goes proportionally slower the closer you are
          speed = MathUtil.clamp(speed, .01, 1); 
         m_Climber.setCLimber(speed);
    }

    @Override
    public void end(boolean interrupted) {
        m_Climber.setCLimber(0.0);
    }

    @Override
    public boolean isFinished() {
       
        return false;
    }
}