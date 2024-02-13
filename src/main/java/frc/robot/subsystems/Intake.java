package frc.robot.subsystems;



import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Intake extends SubsystemBase {
    private static final Intake m_instance = new Intake();

    // private final Spark m_intake = new Spark(6);

    private final CANSparkMax m_intake = new CANSparkMax( 8, MotorType.kBrushed);

    private final DigitalInput m_NoteDetector = new DigitalInput(14);

    private static boolean runningIntake = false;

    public Intake() {
        m_intake.setInverted(true);

        // SendableRegistry.setSubsystem(m_intake, this.getClass().getSimpleName());
        // SendableRegistry.setName(m_intake, "Intake Motor");


    }

    public static Intake getInstance() {
        return m_instance;
    }
    
    public boolean getNoteDetector() {
        return m_NoteDetector.get(); // true = ball in chamber
    }


    public void setIntake(double speed) {

        if (getNoteDetector() == false){
            m_intake.set(speed);
        }

        
        if (speed == 0) {
            runningIntake = false;
        } else {
            runningIntake = true;
        }
    } 

    public static boolean isRunning() {
        return runningIntake;
    }

}