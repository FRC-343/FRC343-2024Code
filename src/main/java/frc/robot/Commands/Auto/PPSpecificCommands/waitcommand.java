package frc.robot.Commands.Auto.PPSpecificCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class waitcommand extends SequentialCommandGroup{

    public waitcommand() {
        addCommands(
    new WaitCommand(.5)
    
    );

    }
}
