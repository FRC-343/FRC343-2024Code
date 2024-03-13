package frc.robot.Commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class Wantnote extends Command {

    private final Intake m_intake; 
    private final Timer t;


    public Wantnote(){
        m_intake = Intake.getInstance();
        t = new Timer();

    }
    
    @Override
    public void execute() {
        m_intake.Updater();

    }

    @Override
    public void initialize() {
        t.reset();
        t.start();
    }

    @Override
    public void end(boolean interrupted) {
        
    }

    @Override
    public boolean isFinished() {
        boolean value = false;
        if(t.get() > 2.0){
            value = true;
            m_intake.deUpdater();
            
        }
        return value;

    }
}
