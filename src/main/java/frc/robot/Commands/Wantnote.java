package frc.robot.Commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LEDs;

public class Wantnote extends Command {

    private final Intake m_intake; 
    private final LEDs m_LED;

    private final Timer t;


    public Wantnote(){
        m_intake = Intake.getInstance();
        m_LED = LEDs.getInstance();
        t = new Timer();

    }
    
    @Override
    public void execute() {
        m_LED.rainbow();
        
    }

    @Override
    public void initialize() {
        t.reset();
        t.start();

    }

    @Override
    public void end(boolean interrupted) {
        m_intake.deUpdater();
    }

    @Override
    public boolean isFinished() {
        boolean value = false;

        return value;

    }
}
