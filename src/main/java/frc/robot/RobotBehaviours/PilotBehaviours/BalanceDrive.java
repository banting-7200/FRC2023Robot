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

    public boolean isBalanced = false;

    private final ADXRS450_Gyro GYRO = INSTANCE_GYRO;
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
        System.out.println("Gyro Angle: " + GYRO.getAngle());

        if(!GYRO.isConnected()) {
            System.out.println("BalanceDrive: No gyro connected");
            return;
        }


        // Use the mapped angle in the evaluateSpeed function.
        double firstAngle = GYRO.getAngle();
        double angle = MapValue(Math.abs(Clamp(firstAngle, -30, 30)), 0, 30, 0, 4);
        double speed = evaluateSpeed(angle) * -Math.signum(GYRO.getAngle());


        if(Math.abs(angle) <= 3.0) {
            isBalanced = true;
        }

        // Finally drive robot with speed.
        baseInstance.DriveRobot(0.0, speed);
    }

    // Sole purpose of this function is to smooth the speed values appropriately based on different angles.
    // Refer to project notes for desmos representation of function.
    private double evaluateSpeed(double evalPointX) {
        double numerator = Math.pow(1.5, -0.2 * (evalPointX * evalPointX * evalPointX * evalPointX));
        double denom = 2.5;
        double t1 = -(numerator / denom);
        return t1 + 0.85;
    }

    public double getGyroAngle() {
        return GYRO.getAngle();
    }
}