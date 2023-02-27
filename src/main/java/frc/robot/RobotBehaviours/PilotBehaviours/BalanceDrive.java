package frc.robot.RobotBehaviours.PilotBehaviours;

import static frc.robot.Core.Utility.*;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Core.RobotDrive;
import frc.robot.Interfaces.RobotBehaviour;

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
        SmartDashboard.putString(SmartDashboardIDs.DRIVEMODEID, "Balance Drive");
    }

    @Override
    public void BehaviourPeriodic() {
        if(!GYRO.isConnected()) {
            System.out.println("BalanceDrive: No gyro connected");
            return;
        }
        
        // Map the absolute value of the angle (from 0 -> 30 to 0 -> 1)
        // Use the mapped angle in the evaluateSpeed function.
        double angle = Clamp(GYRO.getAngle(), -30, 30);
        double absoluteSpeed = MapValue(angle, -30, 30, -1, 1);
        double speed = evaluateSpeed(absoluteSpeed) * -Math.signum(angle);

        // Finally drive robot with speed.
        baseInstance.DriveRobot(0.0, speed);
    }

    // Sole purpose of this function is to smooth the speed values appropriately based on different angles.
    // Refer to project notes for desmos representation of function.
    private double evaluateSpeed(double evalPointX) {
        double exponent = -(Math.pow(evalPointX, 4));
        double base = 1.01;
        double divisor = 2.5;
        return -(Math.pow(base, exponent) / divisor) + (1.0 / divisor) + 0.5;
    }
}