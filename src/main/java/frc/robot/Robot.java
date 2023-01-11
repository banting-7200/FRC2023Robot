package frc.robot;

import static frc.robot.Utility.*;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.Lucas_Soliman.*;

public class Robot extends TimedRobot {
  private RobotDrive driveInstance;

  @Override
  public void robotInit() {
    driveInstance = new RobotDrive(
      MOTOR_DRIVEFWDLEFT, MOTOR_DRIVEBACKLEFT,
      MOTOR_DRIVEFWDRIGHT, MOTOR_DRIVEBACKRIGHT
    );
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {

  }

  @Override
  public void teleopInit() {
    driveInstance.robotDriveTeleopInit();
  }

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