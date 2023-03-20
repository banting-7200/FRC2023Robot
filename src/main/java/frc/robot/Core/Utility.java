package frc.robot.Core;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Claw;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Kicker;

/* Author: Lucas Soliman
 * Date Created: January 10, 2023
 * 
 * Stores constants that will be used project wide.
 * Will be updated by everyone working on project.
 * 
 * Variables/Functions in Pa    scalCase are variables that are read/write
 * Variables in ALL_CAPS_SNAKE_CASE are variables that are read only
 * 
 * For further information, refer to comments above and around variables.
 */
public final class Utility {
    //The below values are also button mappings on the joystick
    public static final int DRIVEMODE_MANUAL = 7;
    public static final int DRIVEMODE_AUTOBALANCE = 8;
    public static final int DRIVEMODE_PIXYALIGN = 9;
    
    public static final class PilotControls {
        public static final Joystick JOYSTICK_PILOT = new Joystick(PORT_JOYSTICK);
        public static final XboxController JOYSTICK_PILOT1 = new XboxController(PORT_JOYSTICK);
        public static final SendableChooser<Integer> DRIVE_STICKCHOOSER = new SendableChooser<>() {{
            addOption("Single-stick Drive", 0);
            addOption("Double-stick Drive", 1);
            SmartDashboard.putData("Drive Joystick Settings", DRIVE_STICKCHOOSER);
        }};

        // Controls are utilised in:
        // - PixyAlignDrive.java
        // - ManualDrive.java
        public static final Supplier<Integer> MANUALDRIVE = () -> {
            return JOYSTICK_PILOT1.getPOV() == 180 ? DRIVEMODE_MANUAL : -1;
        };

        public static final Supplier<Integer> BALANCEDRIVE = () -> {
            return JOYSTICK_PILOT1.getPOV() == 0 ? DRIVEMODE_AUTOBALANCE : -1;
        };

        public static final Supplier<Boolean> FLIP = () -> {
            return JOYSTICK_PILOT1.getRightBumperPressed();
        };

        public static final Supplier<Boolean> CREEP = () -> {
            return JOYSTICK_PILOT1.getLeftTriggerAxis() > 0.5;
        };

        public static final Supplier<Double> PILOT_X = () -> {
            return JOYSTICK_PILOT1.getRightX();
        };

        public static final Supplier<Double> PILOT_Y = () -> {
            if(DRIVE_STICKCHOOSER.getSelected() == 1) {
                return JOYSTICK_PILOT1.getLeftY();
            }

            return JOYSTICK_PILOT1.getRightY();
        };
        
        public static final Supplier<Boolean> KICK = () -> {
            return JOYSTICK_PILOT1.getXButton();
        };

        public static final Supplier<Boolean> KICKLIGHTSSWITCH = () -> {
            return JOYSTICK_PILOT1.getYButtonPressed();
        };

        public static final Supplier<Boolean> BREAKTOGGLE = () -> {
            return JOYSTICK_PILOT1.getBButtonPressed();
        };
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
        
        public static final Supplier<Integer> PIXYALIGNDRIVE = () -> {
            return JOYSTICK_COPILOT.getRawButton(10) ? DRIVEMODE_PIXYALIGN : -1;
        };

        public static final int SHOULDER_ZEROENCODER = 12;
    }
    //#region Constants
    public static class SmartDashboardIDs {
        public static final String DRIVEMODEID = "Current Drive Mode: ";
        public static final String SHOULDERPOSITIONID = "Shoulder Motor Position: ";
        public static final String LIFTPOSITIONID = "Lift Motor Position: ";
    }

    /* Utility functions */
    public static double roundHundredth(double x) {
        return Math.round(x * 100.0) / 100.0;
    }
    
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
    
    /* CONSTANT PARAMETERS */
    public static final int I2C_MAXBYTESREAD = 2;
    public static final double BALANCEDRIVE_ANGLETHRESHOLD = 2; //Degrees
    public static final double TALONFXMOVETO_PERCENTERROR = 0.05;
    /* SPEED VALUES */
    //The value that the joystick x/y must surpass in order to register.
    public static final double JOYSTICK_DEADZONE = 0.1;
    public static final double SHOULDER_kP = 0.25;
    public static final double SHOULDER_kI = 0;
    public static final double SHOULER_kD = 0;
    // Drivespeed being the speed of drive motors.
    // Creepspeed referring to a slower speed for finer drive adjustments.
    public static final double DRIVE_NORMALSPEED = 1;
    public static final double DRIVE_CREEPSPEED = 0.6;

    // Armspeed referring to the speed at which the arm would rotate
    // Creepspeed referring to a slower speed for finer arm adjustments.
    public static final double ARM_SPEED = 0.8;
    public static final double ARM_CREEPSPEED = 0.5;

    //#endregion

    //Pneumatics instances since solenoids cannot have mutliple references
    public static final Kicker INSTANCE_KICKER = new Kicker();
    public static final Claw INSTANCE_CLAW = new Claw();
    public static final ADXRS450_Gyro INSTANCE_GYRO = new ADXRS450_Gyro();
}

// Lift Max Height Position: -996164 || -1006396 (unsafe)
// Lift Gameplay Maximum: -720173 or 724173
// Lift Gameplay Minimum: 
// Lift UpperScore Position: -722173
// Lift Lower Score Position: -252345
// Lift Drive Position: -640584

// Get Differences for each position:
// Shoulder Drop: -1253646 