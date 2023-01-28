package frc.robot.Lucas_Soliman.DriveModes;

/*
 * Author: Lucas Soliman
 * Date Created: January 16, 2023
 * 
 * Aside from the Periodic function, all drive mode classes inheriting this interface
 * must contain a constructor that takes in a RobotDrive Instance.
 */
public interface DriveMode {
    public void DriveModeInit();
    public void DriveModePeriodic();
}
