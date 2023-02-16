package frc.robot;

/*
 * Author: Lucas Soliman
 * Date Created: January 10, 2023
 * 
 * Stores constants that will be used project wide.
 * Will be updated by everyone working on project.
 * 
 * Variables/Functions in PascalCase are variables that are read/write
 * Variables in ALL_CAPS_SNAKE_CASE are variables that are read only
 * 
 * For further information, refer to comments above and around variables.
 */
public final class Utility {
    /* Utility functions */
    public static double MapValue(double x, double a1, double b1, double a2, double b2) {
        return ((x - a1) * (b2 - a2) / (b1 - a1)) + a2;
    }

    public static double Clamp(double x, double min, double max) {
        double val = x;
        if(val < min) {
            val = min;
        } else if(val > max) {
            val = max;
        }

        return val;
    }

    /* PORTS */
    // Integers storing ports that external drive devices are connected to on computer (USB)
    public static final int PORT_JOYSTICK = 0;
    public static final int PORT_COJOYSTICK = 1;

    // Integers storing ports that drive motors are connected to on RoboRIO
    public static final int MOTOR_DRIVEFWDLEFT = 4;
    public static final int MOTOR_DRIVEBACKLEFT = 3;
    public static final int MOTOR_DRIVEFWDRIGHT = 2;
    public static final int MOTOR_DRIVEBACKRIGHT = 1;

    //Integers that resemble different states for classes
    /* STATES */
    public static int STATE_CURRDRIVEMODE = 7;

    //The below values are also button mappings on the joystick
    public static final int DRIVEMODE_MANUAL = 7;
    public static final int DRIVEMODE_AUTOBALANCE = 8;
    public static final int DRIVEMODE_PIXYALIGN = 9;
    public static final int[] DRIVEMODE_MODEARRAY = new int[] {
        DRIVEMODE_MANUAL,
        DRIVEMODE_AUTOBALANCE,
        DRIVEMODE_PIXYALIGN
    };
    
    /* CONSTANT PARAMETERS */
    public static final int I2C_MAXBYTESREAD = 2;
    public static final double BALANCEDRIVE_ANGLETHRESHOLD = 2; //Degrees

    /* SPEED VALUES */
    //The value that the joystick x/y must surpass in order to register.
    public static final double JOYSTICK_DEADZONE = 0.25;

    // Drivespeed being the speed of drive motors.
    // Creepspeed referring to a slower speed for finer drive adjustments.
    public static final double DRIVE_NORMALSPEED = 1;
    public static final double DRIVE_CREEPSPEED = 0.5;

    // Armspeed referring to the speed at which the arm would rotate
    // Creepspeed referring to a slower speed for finer arm adjustments.
    public static final double ARM_SPEED = 0.8;
    public static final double ARM_CREEPSPEED = 0.5;
}