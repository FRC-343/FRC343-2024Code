package frc.robot.subsystems;



import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Intake extends SubsystemBase {
    private static final Intake m_instance = new Intake();

    private LEDs m_LEDs = LEDs.getInstance();

    private final CANSparkMax m_intake = new CANSparkMax( 1, MotorType.kBrushed);

    private final DigitalInput m_NoteDetector = new DigitalInput(7);
    private final DigitalInput m_NoteDetector2 = new DigitalInput(8);

    private boolean flashNote = false;


    private static boolean runningIntake = false;

    public Intake() {
        m_intake.setInverted(false);

        // SendableRegistry.setSubsystem(m_intake, this.getClass().getSimpleName());
        // SendableRegistry.setName(m_intake, "Intake Motor");


    }

    public static Intake getInstance() {
        return m_instance;
    }

    public void Updater(){
        flashNote = true;
    }

    public void deUpdater(){
        flashNote = false;
    }
    
    public boolean getNoteDetector() {
        return m_NoteDetector.get(); // false = note in intake
    }

    public boolean getNoteDetector2() {
        return m_NoteDetector2.get(); // true = ball in chamber
    }

    public void setIntake(double speed) {

        if (getNoteDetector() == false){
            m_intake.set(0);           
         } else{

        m_intake.set(speed);
         }
        if (speed == 0) {
            runningIntake = false;
        } else {
            runningIntake = true;
        }
    } 

    public void setIntakeAuto(double speed){
          m_intake.set(speed);
        
        if (speed == 0) {
            runningIntake = false;
        } else {
            runningIntake = true;
        }

    }

    public static boolean isRunning() {
        return runningIntake;
    }
    @Override
    public void periodic() {
              SmartDashboard.putBoolean("Note detector 1", getNoteDetector());
              SmartDashboard.putBoolean("Note detector 2", getNoteDetector2());
         if (getNoteDetector() == false){
            m_LEDs.HaveNote(); 
         } else if(flashNote == false){
            m_LEDs.noNote();
            } else if(flashNote == true){
                m_LEDs.wantNote();
            }
         }

    }

