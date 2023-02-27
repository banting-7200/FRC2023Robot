package frc.robot.Core;
import java.util.function.Supplier;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Interfaces.RobotBehaviour;

/* Author: Lucas Soliman
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

    //TODO:
    /*
     * Move turning to twisting
     */
    public static final class PilotControls {
        public static final Joystick JOYSTICK_PILOT = new Joystick(PORT_JOYSTICK);

        // Controls are utilised in:
        // - PixyAlignDrive.java
        // - ManualDrive.java
        public static final int DRIVING_CREEPTOGGLE = 1;
        public static final int DRIVING_FLIPTOGGLE = 2;

        public static final Supplier<Double> PILOT_X = () -> {
            return JOYSTICK_PILOT.getX();
        };

        public static final Supplier<Double> PILOT_Y = () -> {
            return JOYSTICK_PILOT.getY();
        };

        // Controls are utilised in DriveModeSetter.java
        public static final int DRIVEMODE_MANUAL = 7;
        public static final int DRIVEMODE_PIXYALIGN = 9;
        public static final int DRIVEMODE_AUTOBALANCE = 11;
    }

    public static final class CoPilotControls {
        public static final Joystick JOYSTICK_COPILOT = new Joystick(PORT_COJOYSTICK);

        // Controls are utilised in Wrist.java
        public static final Supplier<Double> WRIST_MOVE = () -> {
            return JOYSTICK_COPILOT.getX();
        };

        // Controls are utilised in Lift.java
        public static final int MACRO_PICKUP = 11;
        public static final int MACRO_LEVEL1 = 9;
        public static final int MACRO_LEVEL2 = 7;
        public static final int MACRO_LEVEL3 = 8;
        public static final int MACRO_CARRY = 2;

        // public static final int LIFT_PICKUPPOSITION = 12;
        // public static final int LIFT_CARRYPOSITION = 2;

        public static final Supplier<Double> SHOULDER_MOVE = () -> {
            return JOYSTICK_COPILOT.getY();
        };

        // Controls are utilised in Shoulder.java
        public static final int SHOULDERPOV_PICKUPPOS = 0;
        public static final int SHOULDERPOV_EXTENDPOS = 180;

        public static final int LIFT_DOWN = 4;
        public static final int LIFT_UP = 6;

        // Controls are utilised in Claw.java
        public static final int CLAW_TOGGLE = 1;

        // Controls are utilised in Kicker.java
        public static final int KICKER_KICK = 3;

        // Controls are utilised in Lights.java
        public static final int LIGHTS_SWITCHMODE = 12;
    }
    //#region Constants
    public static class SmartDashboardIDs {
        public static final String DRIVEMODEID = "Current Drive Mode: ";
        public static final String SHOULDERPOSITIONID = "Shoulder Motor Position: ";
        public static final String LIFTPOSITIONID = "Lift Motor Position: ";
    }

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
    public static final int MOTOR_WRISTMOTOR = 5;

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
    public static final double JOYSTICK_DEADZONE = 0.1;

    // Drivespeed being the speed of drive motors.
    // Creepspeed referring to a slower speed for finer drive adjustments.
    public static final double DRIVE_NORMALSPEED = 1;
    public static final double DRIVE_CREEPSPEED = 0.6;

    // Armspeed referring to the speed at which the arm would rotate
    // Creepspeed referring to a slower speed for finer arm adjustments.
    public static final double ARM_SPEED = 0.8;
    public static final double ARM_CREEPSPEED = 0.5;

    //#endregion
}

// Lift Max Height Position: -996164 || -1006396 (unsafe)
// Lift Gameplay Maximum: -720173 or 724173
// Lift Gameplay Minimum: 
// Lift UpperScore Position: -722173
// Lift Lower Score Position: -252345
// Lift Drive Position: -640584

// Get Differences for each position:
// Shoulder Drop: -1253646 