package frc.robot;

import edu.wpi.first.wpilibj.*;
import frc.robot.Lucas_Soliman.*;

/*
 * Author: Lucas Soliman
 * Date Created: January 10, 2023
 * 
 * Stores constants that will be used project wide.
 * Will be updated by everyone working on project.
 */
public class Utility {
    public static final int PORT_JOYSTICK = 0;
    public static final int MOTOR_DRIVEFWDLEFT = 1;
    public static final int MOTOR_DRIVEBACKLEFT = 2;
    public static final int MOTOR_DRIVEFWDRIGHT = 3;
    public static final int MOTOR_DRIVEBACKRIGHT = 4;

    // Gyro uses "onboard CS0" connection.
    public static final ADXRS450_Gyro GYRO = new ADXRS450_Gyro();
    public static final Joystick JOYSTICK = new Joystick(PORT_JOYSTICK);
    public static final Input INPUT = new Input(JOYSTICK);
}