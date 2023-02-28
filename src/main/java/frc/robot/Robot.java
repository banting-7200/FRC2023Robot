package frc.robot;

import static frc.robot.Core.Utility.*;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Core.DriveModeSetter;
import frc.robot.Core.RobotDrive;

/*
 * Author: WPILib Project-Generator, Lucas Soliman
 * Date Created: January 9, 2023
 * 
 * The main robot class where all initialisation, and periodic functions are called.
 */
public class Robot extends TimedRobot {
  
  // THis solenoid is being referenced due to weird behavour of always being on.
  private final Solenoid solenoid5 = new Solenoid(PneumaticsModuleType.CTREPCM, 5);

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
    CameraServer.startAutomaticCapture(0);
    CameraServer.startAutomaticCapture(1);
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
    solenoid5.set(true);
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    solenoid5.set(true);
  }

  @Override
  public void teleopPeriodic() {
    driverModeSetter.driveModeSetterTeleop();
    driveInstance.robotDriveTeleop();
  }

  @Override
  public void disabledInit() {
    solenoid5.set(false);
  }
}