package frc.robot.Commands.ShootingRelatingCommands.SpecificCommands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Commands.ShootingRelatingCommands.ShootCommand;

public class ShootSpecificSpeedCommand2 extends SequentialCommandGroup {

  public ShootSpecificSpeedCommand2(double bottomSpeed, double topSpeed) {
    addCommands(
        new InstantCommand(() -> ShootCommand.useCustom(false, bottomSpeed, topSpeed, 0, 0)),
        new ShootCommand()
    );
  }

  public ShootSpecificSpeedCommand2(double speed) {
    this(speed, MathUtil.clamp(speed * 1.5, speed, 100)); //sets top speed to bottom speed * 1.5 with a max of 100
  }
}
