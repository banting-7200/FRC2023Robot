package frc.robot;

import static frc.robot.Core.Utility.*;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.SPI.Port;
import frc.robot.Core.DriveModeSetter;
import frc.robot.Core.RobotDrive;
import frc.robot.RobotBehaviours.AutoBehaviours.AutonomousRunner;

/*
 * Author: WPILib Project-Generator, Lucas Soliman
 * Date Created: January 9, 2023
 * 
 * The main robot class where all initialisation, and periodic functions are called.
 */
public class Robot extends TimedRobot {
  
  // Enable this to have motor positions set to starting positions when calibrated to max top on lift/ straight down on shoulder
  public static final boolean inGameMode = false;

  // This solenoid is being referenced due to weird behavour of always being on.
  // Disable when robot is on but disabled
  // Enable when robot is enabled
  private final Solenoid solenoid5 = new Solenoid(PneumaticsModuleType.CTREPCM, 5);

  // Delegates all teleoperated functions within RobotBehaviours
  private final RobotDrive driveInstance = new RobotDrive(
    MOTOR_DRIVEFWDLEFT, MOTOR_DRIVEBACKLEFT,
    MOTOR_DRIVEFWDRIGHT, MOTOR_DRIVEBACKRIGHT
  );

  // Pilot Controlled
  private final DriveModeSetter driverModeSetter = new DriveModeSetter(
    driveInstance,
    new int[] { DRIVEMODE_MANUAL, DRIVEMODE_AUTOBALANCE },
    PilotControls.JOYSTICK_PILOT
  );

  private final DriveModeSetter coDriverModeSetter = new DriveModeSetter(
    driveInstance, 
    new int[] {DRIVEMODE_PIXYALIGN}, 
    CoPilotControls.JOYSTICK_COPILOT
  );

  private final AutonomousRunner autoRunner = new AutonomousRunner(driveInstance);

  @Override
  public void robotInit() {
    CameraServer.startAutomaticCapture(0);
    CameraServer.startAutomaticCapture(1);
  }

  @Override
  public void robotPeriodic() {

  }

  @Override
  public void autonomousInit() {
    autoRunner.resetAuto();
  }

  @Override
  public void autonomousPeriodic() {
    autoRunner.runAuto();
  }

  @Override
  public void teleopInit() {
    solenoid5.set(true);
  }

  @Override
  public void teleopPeriodic() {
    driverModeSetter.driveModeSetterTeleop();
    coDriverModeSetter.driveModeSetterTeleop();
    driveInstance.robotDriveTeleop();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {
    solenoid5.set(false);
  }
}