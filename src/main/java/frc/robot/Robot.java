package frc.robot;

import static frc.robot.Utility.*;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Lucas_Soliman.*;

/*
 * Author: WPILib Project-Generator
 * Date Created: January 9, 2023
 * 
 * The main robot class where all initialisation, and periodic functions are called.
 */
public class Robot extends TimedRobot {
  private RobotDrive driveInstance;

  @Override
  public void robotInit() {
    driveInstance = new RobotDrive(
      MOTOR_DRIVEFWDLEFT, MOTOR_DRIVEBACKLEFT,
      MOTOR_DRIVEFWDRIGHT, MOTOR_DRIVEBACKRIGHT,
      PORT_JOYSTICK
    );

    RIO_GYRO.calibrate();
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}


  @Override
  public void teleopInit() {}

  int delayTime = 10;
  @Override
  public void teleopPeriodic() {
    driveInstance.robotDriveTeleop();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}