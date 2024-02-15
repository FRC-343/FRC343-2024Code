package frc.robot.subsystems;



import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkRelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Climber extends SubsystemBase {
    private static final Climber m_instance = new Climber();

    // private final Spark m_intake = new Spark(6);

    private final CANSparkMax m_climber1 = new CANSparkMax( 10, MotorType.kBrushed);

    private final RelativeEncoder m_climber1Encoder = m_climber1.getEncoder(SparkRelativeEncoder.Type.kHallSensor, 42);


   private final DigitalInput m_isTop = new DigitalInput(1);
   private final DigitalInput m_isBottom = new DigitalInput(0);


    private static boolean runningIntakeLift = false;

    public Climber() {
        m_climber1.setInverted(true);

        // SendableRegistry.setSubsystem(m_intake, this.getClass().getSimpleName());
        // SendableRegistry.setName(m_intake, "Intake Motor");


    }

    public static Climber getInstance() {
        return m_instance;
    }

    public boolean getTopLimit() {
        return m_isTop.get();
    }

    public boolean getBottomLimit() {
        return m_isBottom.get();
    }

    public void setCLimber(double speed) {

        if (speed < 0.0 && getBottomLimit()) {
            m_climber1.set(0.0);
            m_climber1Encoder.setPosition(0);
        
        } else if (speed > 0 && (m_isTop.get())) {
            m_climber1.set(0.0);

        } else {
          m_climber1.set(speed);
        }
   }

    public static boolean isRunning() {
        return runningIntakeLift;
    }

}