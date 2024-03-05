package frc.robot.Commands.ShootingRelatingCommands.SpecificCommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Commands.IntakeTimedCommand;
import frc.robot.Commands.ShootingRelatingCommands.AimCommand;
import frc.robot.Commands.ShootingRelatingCommands.ShootCommand;

public class AimShootCommandAuto extends SequentialCommandGroup {

  public AimShootCommandAuto(double time) {
    addCommands(
        new InstantCommand(AimCommand::useStandardAutoAim),
        new InstantCommand(() -> ShootCommand.useStandardAutoAimForAutonomous(time)),
        new ParallelDeadlineGroup(
            new ShootCommand(),
            new AimCommand(),
        new SequentialCommandGroup(
          new WaitCommand(.6),
          new IntakeTimedCommand()
        ))

    );
  }

  public AimShootCommandAuto() {
    this(2);
  }
}
