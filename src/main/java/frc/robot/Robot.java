package frc.robot;

import static frc.robot.Core.Utility.*;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Core.DriveModeSetter;
import frc.robot.Core.RobotDrive;
import frc.robot.RobotBehaviours.AutoBehaviours.AutonomousRunner;
import frc.robot.RobotBehaviours.PilotBehaviours.BalanceDrive;

/*
 * Author: WPILib Project-Generator, Lucas Soliman
 * Date Created: January 9, 2023
 * 
 * The main robot class where all initialisation, and periodic functions are called.
 */
public class Robot extends TimedRobot {
  public static final boolean inGameMode = true;
  public static final Solenoid BREAK = new Solenoid(PneumaticsModuleType.CTREPCM, 5);

  private RobotDrive driveInstance;
  private DriveModeSetter driverModeSetter;
  private DriveModeSetter coDriverModeSetter;
  private AutonomousRunner autoRunner;

  @Override
  public void robotInit() {
    CameraServer.startAutomaticCapture(0);
    CameraServer.startAutomaticCapture(1);

    driveInstance = new RobotDrive(
      MOTOR_DRIVEFWDLEFT, MOTOR_DRIVEBACKLEFT,
      MOTOR_DRIVEFWDRIGHT, MOTOR_DRIVEBACKRIGHT
    );

    driverModeSetter = new DriveModeSetter(
      driveInstance,
      new int[] { DRIVEMODE_MANUAL, DRIVEMODE_AUTOBALANCE },
      PilotControls.JOYSTICK_PILOT
    );

    coDriverModeSetter = new DriveModeSetter(
      driveInstance, 
      new int[] {DRIVEMODE_PIXYALIGN}, 
      CoPilotControls.JOYSTICK_COPILOT
    );

    autoRunner = new AutonomousRunner(driveInstance);
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("Autobalance Input: ", INSTANCE_GYRO.getAngle());
    
    if(PilotControls.JOYSTICK_PILOT.getRawButtonPressed(10)) {
      BREAK.set(!BREAK.get());
      SmartDashboard.putBoolean("Break State:", BREAK.get());
    }
  }

  @Override
  public void autonomousInit() {
    BREAK.set(true);
    autoRunner.resetAuto();
  }

  @Override
  public void autonomousPeriodic() {
    autoRunner.runAuto();
  }

  @Override
  public void teleopInit() {
    BREAK.set(true);
  }

  @Override
  public void teleopPeriodic() {
    driverModeSetter.driveModeSetterTeleop();
    coDriverModeSetter.driveModeSetterTeleop();
    driveInstance.robotDriveTeleop();
  }

  @Override
  public void disabledInit() {
    BREAK.set(false);
  }
}