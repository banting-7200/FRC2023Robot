package frc.robot;

import static frc.robot.Core.Utility.*;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.hal.SerialPortJNI;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.SPI.Port;
import frc.robot.Core.DriveModeSetter;
import frc.robot.Core.RobotDrive;
import frc.robot.RobotBehaviours.AutoBehaviours.AutonomousRunner;
import frc.robot.RobotBehaviours.AutoBehaviours.RobotBreak;

/*
 * Author: WPILib Project-Generator, Lucas Soliman
 * Date Created: January 9, 2023
 * 
 * The main robot class where all initialisation, and periodic functions are called.
 */
public class Robot extends TimedRobot {
  // Enable this to have motor positions set to starting positions when calibrated to max top on lift/ straight down on shoulder
  public static final boolean inGameMode = true;

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
  //private final RobotBreak breakInstance = new RobotBreak(9000);
  Solenoid s = new Solenoid(PneumaticsModuleType.CTREPCM, 5);

  @Override
  public void robotInit() {
    CameraServer.startAutomaticCapture(0);
    CameraServer.startAutomaticCapture(1);

    s.set(true);
  }

  @Override
  public void robotPeriodic() {
    //breakInstance.robotBreakPeriodic();
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
  public void teleopPeriodic() {
    driverModeSetter.driveModeSetterTeleop();
    coDriverModeSetter.driveModeSetterTeleop();
    driveInstance.robotDriveTeleop();
  }

  @Override
  public void disabledInit() {
    s.set(false);
  }
}