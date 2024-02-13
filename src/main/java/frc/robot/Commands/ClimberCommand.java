package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;


import frc.robot.subsystems.Climber;

public class ClimberCommand extends Command {

    private double m_speed;
    private final Climber m_Climber;

    public ClimberCommand(double speed) {
        m_Climber = Climber.getInstance();
        m_speed = speed;
        addRequirements(m_Climber);
    }
    

    @Override
    public void execute() {
        m_Climber.setCLimber(m_speed);
        
    }

    @Override
    public void end(boolean interrupted) {
        m_Climber.setCLimber(0.0);
    }

    @Override
    public boolean isFinished() {
        boolean value = false;
         if (m_speed > 0) { // positive speed is going down
             if (m_Climber.getBottomLimit()) {
                 value = true;
             }
         } else if (m_speed < 0) {
             if (m_Climber.getTopLimit()) {
                 value = true;
             }
            }
       if (m_speed == 0) {
             value = true;
         }

        return value;
    }
}