// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PS4Controller.Button;
import frc.robot.Commands.Auto.TestAuto;
import frc.robot.Commands.Auto.PPSpecificCommands.waitcommand;
import frc.robot.Commands.ShootingRelatingCommands.AimCommand;
import frc.robot.Commands.ShootingRelatingCommands.PresetHoodCommand;
import frc.robot.Commands.ShootingRelatingCommands.ShootCommand;
import frc.robot.Commands.ShootingRelatingCommands.SpecificCommands.AimShootCommand;
import frc.robot.Commands.ShootingRelatingCommands.SpecificCommands.AimShootCommandAuto;
import frc.robot.Commands.ShootingRelatingCommands.SpecificCommands.ShootSpecificSpeedCommand;
import frc.robot.Commands.ShootingRelatingCommands.SpecificCommands.ShootSpecificSpeedCommandAuto;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

import java.util.List;

import frc.robot.Commands.*;
import frc.robot.subsystems.*;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.util.PathPlannerLogging;

/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems
  private final DriveSubsystem m_robotDrive = DriveSubsystem.getInstance();
  private final IntakeLift m_IntakeLift = IntakeLift.getInstance(); 
  private final Climber m_Climber = Climber.getInstance();
  private final Shooter m_Shooter = Shooter.getInstance();
  private final Intake m_Intake = Intake.getInstance();
  private final Vision m_Vision = Vision.getInstance();

  private final LEDs m_LEDs = LEDs.getInstance();

  private final ShooterAngle m_ShooterAngle = ShooterAngle.getInstance();

  private final SendableChooser<Command> autoChooser;
  
  private final Field2d field;


  // The driver's controller
  XboxController m_driverController = new XboxController(OIConstants.kDriverControllerPort);
  XboxController m_OpController = new XboxController(OIConstants.kOpControllerPort);
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    

      NamedCommands.registerCommand("AimShoot", new AimShootCommandAuto());
      NamedCommands.registerCommand("Intake lift", new IntakeLiftCommand(.3));
      NamedCommands.registerCommand("Climber", new ClimberCommand(.3));
      NamedCommands.registerCommand("Intake with stop", new IntakeCommand());
      NamedCommands.registerCommand("Wait", new waitcommand());
      NamedCommands.registerCommand("Shoot",new ShootCommand());
      NamedCommands.registerCommand("Intake for Auto", new IntakeCommandAuto());
      NamedCommands.registerCommand("Timed Intake", new IntakeTimedCommand(-10, .9));


      // Auto fire speed
      NamedCommands.registerCommand("Auto Shot Speed", new ShootSpecificSpeedCommandAuto(80));

      // Auto Preset Shots
      NamedCommands.registerCommand("Stage 2 note Preload", new PresetHoodCommand(148.25));
      NamedCommands.registerCommand("Stage note shot", new PresetHoodCommand(127.25));
      NamedCommands.registerCommand("Center Preload", new PresetHoodCommand(146.5));
      NamedCommands.registerCommand("Center Note", new PresetHoodCommand(106.5));
      NamedCommands.registerCommand("Zero", new PresetHoodCommand(0));
      NamedCommands.registerCommand("Center Wing 1", new PresetHoodCommand(34.5));
      



      field = new Field2d();
        SmartDashboard.putData("Field", field);
        

        // Logging callback for current robot pose
        PathPlannerLogging.setLogCurrentPoseCallback((pose) -> {
            // Do whatever you want with the pose here
            field.setRobotPose(pose);
        });

        // Logging callback for target robot pose
        PathPlannerLogging.setLogTargetPoseCallback((pose) -> {
            // Do whatever you want with the pose here
            field.getObject("target pose").setPose(pose);
        });

        // Logging callback for the active path, this is sent as a list of poses
        PathPlannerLogging.setLogActivePathCallback((poses) -> {
            // Do whatever you want with the poses here
            field.getObject("path").setPoses(poses);
        });
    // Configure the button bindings
    configureButtonBindings();
    // Build an auto chooser. This will use Commands.none() as the default option.
  //  initAutoChooser();
    autoChooser = AutoBuilder.buildAutoChooser();
    // autoChooser.setDefaultOption("Test", new TestAuto());
    
    // Another option that allows you to specify the default auto by its name
    // autoChooser = AutoBuilder.buildAutoChooser("My Default Auto");

    SmartDashboard.putData("Auto Chooser", autoChooser);
    // Configure default commands
    m_robotDrive.setDefaultCommand(
        // The left stick controls translation of the robot.
        // Turning is controlled by the X axis of the right stick.
        new RunCommand(
            () -> m_robotDrive.drive(
                -MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband),
                true, true),
            m_robotDrive));



  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its
   * subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling
   * passing it to a
   * {@link JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(m_driverController, Button.kR1.value)
        .whileTrue(new RunCommand(
            () -> m_robotDrive.setX(),
            m_robotDrive));


            // new JoystickButton(m_OpController, XboxController.Button.kLeftBumper.value).whileTrue(new RunCommand(()-> m_robotDrive.AimBody(), m_robotDrive));


            new JoystickButton(m_driverController, XboxController.Button.kX.value).whileTrue(new RunCommand(()-> m_robotDrive.Centerbot(), m_robotDrive));

             m_IntakeLift.setDefaultCommand(
               new RunCommand(() -> m_IntakeLift.setIntakeLift(  -m_OpController.getLeftY()), m_IntakeLift));

             m_Climber.setDefaultCommand(
               new RunCommand(() -> m_Climber.setCLimber(  m_OpController.getRightY() * 2), m_Climber));

            new JoystickButton(m_OpController, XboxController.Button.kRightBumper.value)
            .whileTrue(new IntakeCommandAuto(-8));

            new JoystickButton(m_OpController, XboxController.Button.kB.value).whileTrue(new IntakeCommandAuto(8));

            new JoystickButton(m_driverController, XboxController.Button.kLeftBumper.value).whileTrue(new IntakeCommand(-8));

            new JoystickButton(m_OpController, XboxController.Button.kA.value)
            .onTrue( new PresetHoodCommand(100.5));

            new POVButton(m_OpController, 90).onTrue(new PresetHoodCommand(22));
            
            new JoystickButton(m_OpController, XboxController.Button.kLeftBumper.value)
            .whileTrue(new AimShootCommand());
 
             new JoystickButton(m_driverController, XboxController.Button.kA.value).whileTrue( new RunCommand(() -> m_robotDrive.resetGyro(), m_robotDrive));

             new JoystickButton(m_OpController, XboxController.Button.kX.value)
             .onTrue(new PresetHoodCommand(165));

             new JoystickButton(m_OpController, XboxController.Button.kY.value).whileTrue(new ShootSpecificSpeedCommand(80));
            
            // new JoystickButton(m_OpController, XboxController.Button.kLeftBumper.value).whileFalse((new ShootSpecificSpeedCommand(0)));

            // new JoystickButton(m_OpController, XboxController.Button.kA.value).onTrue(new AimCommand());

            m_ShooterAngle.setDefaultCommand(
            new RunCommand(() -> m_ShooterAngle.move(  m_OpController.getRightX()/1.5), m_ShooterAngle));
  }
//   private void initAutoChooser() {
//     autoChooser.setDefaultOption("Test", new TestAuto());
//   }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();

    // Create config for trajectory
    // TrajectoryConfig config = new TrajectoryConfig(
    //     AutoConstants.kMaxSpeedMetersPerSecond,
    //     AutoConstants.kMaxAccelerationMetersPerSecondSquared)
    //     // Add kinematics to ensure max speed is actually obeyed
    //     .setKinematics(DriveConstants.kDriveKinematics);

    // // An example trajectory to follow. All units in meters.
    // Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
    //     // Start at the origin facing the +X direction
    //     new Pose2d(0, 0, new Rotation2d(0)),
    //     // Pass through these two interior waypoints, making an 's' curve path
    //     List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
    //     // End 3 meters straight ahead of where we started, facing forward
    //     new Pose2d(3, 0, new Rotation2d(0)),
    //     config);

    // var thetaController = new ProfiledPIDController(
    //     AutoConstants.kPThetaController, 0, 0, AutoConstants.kThetaControllerConstraints);
    // thetaController.enableContinuousInput(-Math.PI, Math.PI);

    // SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand(
    //     exampleTrajectory,
    //     m_robotDrive::getPose, // Functional interface to feed supplier
    //     DriveConstants.kDriveKinematics,

    //     // Position controllers
    //     new PIDController(AutoConstants.kPXController, 0, 0),
    //     new PIDController(AutoConstants.kPYController, 0, 0),
    //     thetaController,
    //     m_robotDrive::setModuleStates,
    //     m_robotDrive);

    // // Reset odometry to the starting pose of the trajectory.
    // m_robotDrive.resetOdometry(exampleTrajectory.getInitialPose());

    // // Run path following command, then stop at the end.
    // return swerveControllerCommand.andThen(() -> m_robotDrive.drive(0, 0, 0, false, false));
  }
}
