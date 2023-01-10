package Lucas_Soliman.Autonomous;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import Lucas_Soliman.*;

/*
 * Author: Lucas Soliman
 * Date Created: January 10, 2023
 * 
 * Balances the robot using a gyro and RobotDrive instance.
 */
public class RobotBalance {
    private RobotDrive driveInstance;
    private ADXRS450_Gyro targetGyro;

    public RobotBalance(ADXRS450_Gyro targetGyro, RobotDrive driveInstance) {
        this.targetGyro = targetGyro;
        this.driveInstance = driveInstance;
        gyro.calibrate();
    }

    public void RobotBalanceTick() {
        double angle = gyro.getAngle();
        System.out.println(angle);
    }
}
