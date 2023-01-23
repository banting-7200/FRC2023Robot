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
    private RobotDrive baseInstance;

    public BalanceDrive(RobotDrive driveInstance) {
        System.out.println("Init BalanceDrive...");
        baseInstance = driveInstance;
        RIO_GYRO.calibrate();
    }

    //TODO Test Sigmoid function implementation.
    @Override
    public void DriveModePeriodic() {
        double angle = RIO_GYRO.getAngle();
        baseInstance.DriveRobot(0, getSpeed(angle));

        /* This implementation is legacy as of this project version.
        if(angle > BALANCEDRIVE_ANGLETHRESHOLD) {
            baseInstance.DriveRobot(0, -0.6);
        } else if(angle < -BALANCEDRIVE_ANGLETHRESHOLD) {
            baseInstance.DriveRobot(0, 0.6);
        } else {
            baseInstance.DriveRobot(0, 0);
        }
        */
    }

    //Refer to {Project Notes.txt} for desmos link for function
    private double getSpeed(double x) {
        return -((2.0 / (1.0 - Math.pow(Math.E, -0.26 * x))) - (1.0));
    }
}
