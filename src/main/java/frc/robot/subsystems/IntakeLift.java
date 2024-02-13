package frc.robot.subsystems;



import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkRelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class IntakeLift extends SubsystemBase {
    private static final IntakeLift m_instance = new IntakeLift();

    // private final Spark m_intake = new Spark(6);

    private final CANSparkMax m_intakeLift = new CANSparkMax( 9, MotorType.kBrushed);

    private final RelativeEncoder m_intakeLiftEncoder = m_intakeLift.getEncoder(SparkRelativeEncoder.Type.kHallSensor, 42);

   private final DigitalInput m_isTop = new DigitalInput(1);
   private final DigitalInput m_isBottom = new DigitalInput(0);


    private static boolean runningIntakeLift = false;

    public IntakeLift() {
        m_intakeLift.setInverted(true);

        // SendableRegistry.setSubsystem(m_intake, this.getClass().getSimpleName());
        // SendableRegistry.setName(m_intake, "Intake Motor");


    }

    public static IntakeLift getInstance() {
        return m_instance;
    }

    public boolean getTopLimit() {
        return m_isTop.get();
    }

    public boolean getBottomLimit() {
        return m_isBottom.get();
    }

    public void setIntakeLift(double speed) {

        if (speed < 0.0 && getBottomLimit()) {
            m_intakeLift.set(0.0);
            m_intakeLiftEncoder.setPosition(0);
        } else if (speed > 0 && (m_isTop.get())) {
            m_intakeLift.set(0.0);
        } else {
          m_intakeLift.set(speed);
        }
   }

    public static boolean isRunning() {
        return runningIntakeLift;
    }

}