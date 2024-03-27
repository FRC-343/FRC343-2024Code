package frc.robot.Commands.ShootingRelatingCommands.SpecificCommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Commands.BodyTurnTest;
import frc.robot.Commands.intakeCommands.IntakeCommandAuto;
import frc.robot.Commands.intakeCommands.IntakeCommandWait;
import frc.robot.Commands.intakeCommands.IntakeTimedCommand;
import frc.robot.Commands.intakeCommands.inatkeCommandFD;
import frc.robot.Commands.Auto.PPSpecificCommands.waitcommand;
import frc.robot.Commands.ShootingRelatingCommands.AimCommand;
import frc.robot.Commands.ShootingRelatingCommands.ShootCommand;
import frc.robot.Commands.intakeCommands.IntakeCommandAuto;
import frc.robot.Commands.intakeCommands.IntakeCommandWait;
import frc.robot.Commands.intakeCommands.IntakeTimedCommand;
import frc.robot.Commands.BodyTurnTest;

public class AimShootCommand extends SequentialCommandGroup {


  public AimShootCommand() {
    addCommands(
        new InstantCommand(AimCommand::useAutoAutoAim),
        new InstantCommand(ShootCommand::useStandardAutoAim),
        new ParallelDeadlineGroup(
            new ShootCommand(),  
            new AimCommand(),
        new SequentialCommandGroup(
          new BodyTurnTest(),
          new inatkeCommandFD()
        ))
        

    );
  }
}
