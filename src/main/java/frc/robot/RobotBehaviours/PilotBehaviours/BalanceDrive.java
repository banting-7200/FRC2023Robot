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

        // Use the mapped angle in the evaluateSpeed function.
        double angle = Clamp(GYRO.getAngle(), -40, 40);
        double speed = evaluateSpeed(angle) * -Math.signum(angle);

        // Finally drive robot with speed.
        baseInstance.DriveRobot(0.0, speed);
    }

    // Sole purpose of this function is to smooth the speed values appropriately based on different angles.
    // Refer to project notes for desmos representation of function.
    private double evaluateSpeed(double evalPointX) {
        double exponent = -(Math.pow(evalPointX, 2));
        double base = 1.002;
        double divisor = 2.5;
        return Math.max(0.5, -(Math.pow(base, exponent) / divisor) + (1.0 / divisor) + 0.49);
    }
}