package frc.robot.Commands.intakeCommands;

import edu.wpi.first.wpilibj2.command.Command;


import frc.robot.subsystems.IntakeLift;

public class IntakeLiftCommand extends Command {

    private double m_speed;
    private final IntakeLift m_IntakeLift;

    public IntakeLiftCommand(double speed) {
        m_IntakeLift = IntakeLift.getInstance();
        m_speed = speed;
        addRequirements(m_IntakeLift);
    }
    

    @Override
    public void execute() {
        m_IntakeLift.setIntakeLift(m_speed);
        
    }

    @Override
    public void end(boolean interrupted) {
        m_IntakeLift.setIntakeLift(0.0);
    }

    @Override
    public boolean isFinished() {
        boolean value = false;
         if (m_speed > 0) { // positive speed is going down
             if (m_IntakeLift.getBottomLimit()) {
                 value = true;
             }
         } else if (m_speed < 0) {
             if (m_IntakeLift.getTopLimit()) {
                 value = true;
             }
            }
       if (m_speed == 0) {
             value = true;
         }

        return value;
    }
}