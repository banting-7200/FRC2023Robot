package frc.robot;

import static frc.robot.Utility.*;

import java.util.HashMap;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;

/*
 * Author: WPILib Project-Generator, Lucas Soliman
 * Date Created: January 9, 2023
 * 
 * The main robot class where all initialisation, and periodic functions are called.
 */
public class Robot extends TimedRobot {
  
  // Delegates all teleoperated functions within RobotBehaviours
  private final RobotDrive driveInstance = new RobotDrive(
    MOTOR_DRIVEFWDLEFT, MOTOR_DRIVEBACKLEFT,
    MOTOR_DRIVEFWDRIGHT, MOTOR_DRIVEBACKRIGHT
  );

  // Pilot Controlled
  private final DriveModeSetter driverModeSetter = new DriveModeSetter(
    driveInstance,
    new int[] { DRIVEMODE_MANUAL, DRIVEMODE_PIXYALIGN, DRIVEMODE_AUTOBALANCE }
  );

  @Override
  public void robotInit() {
    CameraServer.startAutomaticCapture();
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopPeriodic() {
    driverModeSetter.driveModeSetterTeleop();
    driveInstance.robotDriveTeleop();
  }
}