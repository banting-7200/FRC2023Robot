package frc.robot.Lucas_Soliman.DriveModes;

import static frc.robot.Utility.*;
import frc.robot.Lucas_Soliman.RobotDrive;

/*
 * Author: Lucas Soliman
 * Date Created: January 17, 2023
 * 
 * Uses a single-axis gyro to drive the robot to a balanced state.
 */
public class BalanceDrive implements DriveMode {
    public BalanceDrive(RobotDrive driveInstance) {
        System.out.println("Init BalanceDrive...");
    }

    @Override
    public void DriveModePeriodic() {
        double gyroAngle = RIO_GYRO.getAngle();
        System.out.println("Current Robot Angle: " + gyroAngle);
    }
}
