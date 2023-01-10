package frc.robot;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

/*
 * Author: Lucas Soliman
 * Date Created: January 10, 2023
 * 
 * Stores constants that will be used project wide.
 */
public class Utility {
    public static final int MOTOR_DRIVEFWDLEFT = 1;
    public static final int MOTOR_DRIVEBACKLEFT = 2;
    public static final int MOTOR_DRIVEFWDRIGHT = 3;
    public static final int MOTOR_DRIVEBACKRIGHT = 4;

    // Gyro uses "onboard CS0" connection.
    public static final ADXRS450_Gyro GYRO = new ADXRS450_Gyro();
}
