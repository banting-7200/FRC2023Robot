package frc.robot.Lucas_Soliman.DriveModes;

import static frc.robot.Utility.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Lucas_Soliman.RobotDrive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * Author: Lucas Soliman
 * Date Created: January 17, 2023
 * 
 * Uses a single-axis gyro to drive the robot to a balanced state.
 * This drive mode is autonomous
 */
public final class BalanceDrive implements DriveMode {
    private RobotDrive baseInstance;

    public BalanceDrive(RobotDrive driveInstance) {
        System.out.println("Init BalanceDrive...");
        baseInstance = driveInstance;
        RIO_GYRO.calibrate();
    }

    @Override
    public void DriveModeInit() {
        SmartDashboard.putString("DB/String 0", "Balance Mode");
    }

    @Override
    public void DriveModePeriodic() {
        double angle = Clamp(RIO_GYRO.getAngle(), -30, 30);
        if(angle > -1 && angle < 1) {
            angle = 0;
        }

        // Map the absolute value of the angle (from 0 -> 30 to 0 -> 1)
        // Use the mapped angle in the evaluateSpeed function.
        double absoluteSpeed = MapValue(Math.abs(angle), 0, 30, 0, 1);
        double speed = evaluateSpeed(absoluteSpeed) * -Math.signum(angle);
        speed = Clamp(speed, -0.7, 0.7);

        //Finally drive robot with speed.
        baseInstance.DriveRobot(0.0, speed);
    }

    //Sole purpose of this function is to smooth the speed values appropriately based on different angles.
    //Refer to project notes for desmos representation of function.
    private double evaluateSpeed(double evalPointX) {
        double x = Clamp(evalPointX, 0, 1.0);
        double eExpNegTwoX = Math.pow(Math.E, -2.0 * x);
        double xSquared = Math.pow(x, 2.0);
        return ((5.0 * xSquared) * (1.0 - eExpNegTwoX)) / ((1.0 + eExpNegTwoX) * (25.0 * xSquared + 1.0)) + 0.5;
    }
}