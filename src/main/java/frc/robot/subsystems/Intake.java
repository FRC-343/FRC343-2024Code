package frc.robot.subsystems;


import edu.wpi.first.wpilibj.motorcontrol.Spark;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Intake extends SubsystemBase {
    private static final Intake m_instance = new Intake();

    // private final Spark m_intake = new Spark(6);

    private final CANSparkMax m_intake = new CANSparkMax( 8, MotorType.kBrushed);


    private static boolean runningIntake = false;

    public Intake() {
        m_intake.setInverted(true);

        // SendableRegistry.setSubsystem(m_intake, this.getClass().getSimpleName());
        // SendableRegistry.setName(m_intake, "Intake Motor");


    }

    public static Intake getInstance() {
        return m_instance;
    }

    public void raise() {
    }

    public void lower() {
    }

    public void setIntake(double speed) {
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

}