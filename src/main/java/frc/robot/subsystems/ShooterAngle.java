package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterAngle extends SubsystemBase {
    private static final ShooterAngle m_instance = new ShooterAngle();

    private final Encoder m_ShooterAngleEncoder = new Encoder(0, 1);
    private final DigitalInput m_ShooterAngleBack = new DigitalInput(5); // 2 = bottom = back
    private final DigitalInput m_ShooterAngleFront = new DigitalInput(4);


    private final CANSparkMax m_ShooterAngleMotor = new CANSparkMax(15,  MotorType.kBrushed);

    private boolean m_aimed = false; // if shooter is currently aimed
    private double m_target = 0.0; // where it needs to be aiming
    private double m_speed = 0.0; // manual control
    private boolean m_aiming = false; // if currently aiming (for automatic)
    private boolean m_zeroing = false; // resetting ShooterAngle

    private double kMaxShooterAngleSpeed = .4;

    public ShooterAngle() {
        SendableRegistry.setSubsystem(m_ShooterAngleEncoder, this.getClass().getSimpleName());
        SendableRegistry.setName(m_ShooterAngleEncoder, "ShooterAngle Encoder");
        SendableRegistry.setSubsystem(m_ShooterAngleBack, this.getClass().getSimpleName());
        SendableRegistry.setName(m_ShooterAngleBack, "ShooterAngle Back Limit");
        SendableRegistry.setSubsystem(m_ShooterAngleFront, this.getClass().getSimpleName());
        SendableRegistry.setName(m_ShooterAngleFront, "ShooterAngle Front Limit");


    }

    public static ShooterAngle getInstance() {
        return m_instance;
    }

    public void aim(double target, boolean startWithZeroing) {
        m_target = target;
        SmartDashboard.putNumber("ShooterAngle_target", m_target);

        if (!m_aiming) {
            m_aiming = true;
            if (startWithZeroing) {
                m_zeroing = true;
            } else {
                m_zeroing = false;
            }
        }
    }

    public void aim(double target) {
        m_target = target;
        aim(target, false);
    }

    public void stop() {
        m_ShooterAngleMotor.set(0.0);
    }

    public boolean isAimed() {
        return false;
    }

    public void move(double speed) {
        m_aiming = false;
        m_aimed = false;
        m_speed = speed;
    }
    
    public boolean getTopLimit() {
        return m_ShooterAngleFront.get();
    }

    public boolean getBottomLimit() {
        return m_ShooterAngleBack.get();
    }
    @Override
    public void periodic() {
     
        if (m_aiming) {

            if (m_ShooterAngleEncoder.getDistance() > 300 || m_ShooterAngleEncoder.getDistance() < -200) {
                System.err.println("ShooterAngle encoder sent garbage values, zeroing again...");
                m_zeroing = true;
                m_aimed = false;
            }

            if (m_ShooterAngleBack.get()) {
                m_zeroing = false;
                m_ShooterAngleEncoder.reset();
            }

            if (m_zeroing) {
                m_ShooterAngleMotor.set(kMaxShooterAngleSpeed);
                m_aimed = false;
            } else {
                double speed = .8;
                if (Math.abs(m_ShooterAngleEncoder.getDistance() - m_target) < 20) {
                    speed = .4;
                }

                if (m_ShooterAngleFront.get()) {
                    m_zeroing = true;
                    m_ShooterAngleMotor.set(0.0);
                } else if (m_ShooterAngleEncoder.getDistance() < m_target - 5) {
                    m_ShooterAngleMotor.set(-speed);
                    m_aimed = false;
                } else if (m_ShooterAngleEncoder.getDistance() > m_target + 5) {
                    m_ShooterAngleMotor.set(speed);
                    m_aimed = false;
                } else { // m_ShooterAngleEncoder.getDistance >m_target-50 && < m_target+50
                    m_ShooterAngleMotor.set(0.0);
                    m_aimed = true;
                }
            }
            SmartDashboard.putBoolean("Is ShooterAngle aimed", m_aimed);

        } else {
            if (m_ShooterAngleBack.get() && m_speed > 0.0) {
                m_ShooterAngleMotor.set(0.0);
                m_ShooterAngleEncoder.reset();
            } else if (m_ShooterAngleFront.get() && m_speed < 0.0) {
                m_ShooterAngleMotor.set(0.0);
            } else {
                m_ShooterAngleMotor.set(m_speed);
            }

        }

        SmartDashboard.putBoolean("Shooter Top Limit", getTopLimit());
        SmartDashboard.putBoolean("Shooter Bottom Limit", getBottomLimit());

        SmartDashboard.putData("Shooter Angle", m_ShooterAngleEncoder);
    }
}