package frc.robot.subsystems;



import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkRelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class IntakeLift extends SubsystemBase {
    private static final IntakeLift m_instance = new IntakeLift();


    private final CANSparkMax m_intakeLiftMotor = new CANSparkMax( 17, MotorType.kBrushless);

    private final RelativeEncoder m_intakeLiftEncoder = m_intakeLiftMotor.getEncoder(SparkRelativeEncoder.Type.kHallSensor, 42);

   private final DigitalInput m_isTop = new DigitalInput(2);
   private final DigitalInput m_isBottom = new DigitalInput(3);


    private static boolean runningIntakeLift = false;

    public IntakeLift() {
        m_intakeLiftMotor.setInverted(true);
        m_intakeLiftMotor.setIdleMode(IdleMode.kBrake);
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
            m_intakeLiftMotor.set(0.0);
            m_intakeLiftEncoder.setPosition(0);
        } else if (speed > 0 && (m_isTop.get())) {
            m_intakeLiftMotor.set(0.0);
        } else {
          m_intakeLiftMotor.set(speed);
        }
   }

    public static boolean isRunning() {
        return runningIntakeLift;
    }

    @Override
    public void periodic() {
              SmartDashboard.putBoolean("Intake Lift Bottom Limit", getBottomLimit());
              SmartDashboard.putBoolean("Intake lift Top limit", getTopLimit());


    }

}