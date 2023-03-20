package frc.robot.RobotBehaviours.PilotBehaviours;

import static frc.robot.Core.Utility.*;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
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

    private final double angleBalanceThreshold = 4.0;
    private final ADXRS450_Gyro GYRO = INSTANCE_GYRO;
    private RobotDrive baseInstance;

    public BalanceDrive(RobotDrive driveInstance) {
        System.out.println("BalanceDrive Init...");
        baseInstance = driveInstance;
        GYRO.calibrate();
    }

    @Override
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
        SmartDashboard.putString(SmartDashboardIDs.DRIVEMODEID, "Balance Drive");
        Robot.BREAK.set(true);
    }

    @Override
    public void BehaviourPeriodic() {
        if(!GYRO.isConnected()) {
            System.out.println("BalanceDrive: No gyro connected");
            return;
        }

        // Use the mapped angle in the evaluateSpeed function.
        double currAngle = getGyroAngle();
        double mappedAngle = MapValue(currAngle, -30.0, 30.0, -1.0, 1.0);
        double speed = speedFunction(mappedAngle);
        speed = roundHundredth(speed);

        if(Math.abs(currAngle) <= angleBalanceThreshold) {
            Robot.BREAK.set(false);
            isBalanced = true;
            speed = 0;
        }

        // Finally drive robot with speed.
        baseInstance.DriveRobot(0.0, speed);
    }

    private double roundHundredth(double x) {
        return Math.round(x * 100.0) / 100.0;
    }

    // Sole purpose of this function is to smooth the speed values appropriately based on different angles.
    // Refer to project notes for desmos representation of function.
    private double speedFunction(double x) {
        return Math.pow(1.25 * x, 3.0);
    }

    public double getGyroAngle() {
        return GYRO.getAngle();
    }
}