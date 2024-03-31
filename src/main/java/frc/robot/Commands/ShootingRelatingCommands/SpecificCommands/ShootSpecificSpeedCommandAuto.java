package frc.robot.Commands.ShootingRelatingCommands.SpecificCommands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Commands.ShootingRelatingCommands.ShootCommand;

public class ShootSpecificSpeedCommandAuto extends SequentialCommandGroup {

  public ShootSpecificSpeedCommandAuto(double bottomSpeed, double topSpeed) {
    addCommands(
        new InstantCommand(() -> ShootCommand.useCustom(false, bottomSpeed, topSpeed, 0, 8)),
        new ShootCommand()
    );
  }

  public ShootSpecificSpeedCommandAuto(double speed) {
    this(speed, speed); //sets top speed to bottom speed * 1.5 with a max of 100
  }
}
