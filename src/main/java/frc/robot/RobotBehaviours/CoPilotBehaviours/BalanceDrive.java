package frc.robot.RobotBehaviours.CoPilotBehaviours;

import static frc.robot.Utility.*;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotDrive;
import frc.robot.RobotBehaviours.RobotBehaviour;

/*
 * Author: Lucas Soliman
 * Date Created: January 17, 2023
 * 
 * Uses a single-axis gyro to drive the robot to a balanced state.
 * This drive mode is autonomous
 */
public final class BalanceDrive implements RobotBehaviour {
    private final ADXRS450_Gyro GYRO = new ADXRS450_Gyro();
    private RobotDrive baseInstance;

    public BalanceDrive(RobotDrive driveInstance) {
        System.out.println("Init BalanceDrive...");
        baseInstance = driveInstance;
        GYRO.calibrate();
    }

    @Override
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
        SmartDashboard.putString("DB/String 0", "Balance Mode");
    }

    @Override
    public void BehaviourPeriodic() {
        if(!GYRO.isConnected()) {
            System.out.println("BalanceDrive: No gyro connected");
            return;
        }

        double angle = Clamp(GYRO.getAngle(), -30, 30);

        // Map the absolute value of the angle (from 0 -> 30 to 0 -> 1)
        // Use the mapped angle in the evaluateSpeed function.
        double absoluteSpeed = MapValue(Math.abs(angle), 0, 30, 0, 1);
        double speed = evaluateSpeed(absoluteSpeed) * -Math.signum(angle);
        speed = Clamp(speed, -0.7, 0.7);

        // Finally drive robot with speed.
        baseInstance.DriveRobot(0.0, speed);
    }

    // Sole purpose of this function is to smooth the speed values appropriately based on different angles.
    // Refer to project notes for desmos representation of function.
    private double evaluateSpeed(double evalPointX) {
        double x = Clamp(evalPointX, 0, 1.0);
        double eExpNegTwoX = Math.pow(Math.E, -2.0 * x);
        double xSquared = Math.pow(x, 2.0);
        return ((16.0 * xSquared) * (1.0 - eExpNegTwoX)) / ((1.0 + eExpNegTwoX) * (25.0 * xSquared + 1.0)) + 0.5;
    }
}