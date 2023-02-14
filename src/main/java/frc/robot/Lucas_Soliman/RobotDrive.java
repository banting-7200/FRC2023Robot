package frc.robot.Lucas_Soliman;

import static frc.robot.Utility.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import frc.robot.Lucas_Soliman.DriveModes.*;
import frc.robot.Lucas_Soliman.DriveModes.DefaultModes.*;

/*
 * Author: Lucas Soliman
 * Date Created: January 9, 2023
 * 
 * The class responsible for driving/moving the robot using motor references.
 * This class ticks a drivemode assigned in teleopPeriodic.
 * 
 * This class depends on DriveModeSetter.java
 */
public final class RobotDrive {
    private DifferentialDrive driveInstance;
    private DriveModeSetter modeSetter;
    private DriveMode currentDriveMode;

    public RobotDrive(int topLeft, int bottomLeft, int topRight, int bottomRight) {
        //Create instances of left motors
        PWMSparkMax tl = new PWMSparkMax(topLeft);
        PWMSparkMax bl = new PWMSparkMax(bottomLeft);
        
        //Create instances of right motors
        PWMSparkMax tr = new PWMSparkMax(topRight);
        PWMSparkMax br = new PWMSparkMax(bottomRight);

        //Group the left and right motors
        MotorControllerGroup leftGroup = new MotorControllerGroup(tl, bl);
        MotorControllerGroup rightGroup = new MotorControllerGroup(tr, br);

        //Create new DifferentialDrive instance with motor groups.
        driveInstance = new DifferentialDrive(leftGroup, rightGroup);
    }

    //Run this function in teleopPeriodic
    public void robotDriveTeleop() {
        if(modeSetter == null) {
            System.out.println("RobotDrive: No DriveModeSetter instance attached...");
            return;
        }
        
        for(DriveMode mode : modeSetter.defaultModes) {
            mode.DriveModePeriodic();
        }

        if(currentDriveMode == null) {
            System.out.println("No DriveMode being run!");
            return;
        }

        currentDriveMode.DriveModePeriodic();
    }

    // A function called by DriveModeSetter.java to make this class tick specific drive modes.
    public void setDriveMode(DriveMode mode) {
        currentDriveMode = mode;
        mode.DriveModeInit();
    }

    // A function for drive modes to interface with DifferentialDrive instance.
    // All parameters are in deltas, and not a set positikon/rotation.
    public void DriveRobot(double motorPower, double zRotation) {
        driveInstance.arcadeDrive(motorPower, zRotation);
    }

    // Allow for the RobotDrive to retain a DriveModeSetter instance.
    // This is mainly to allow for this class to access the default drive modes.
    public void attachModeSetter(DriveModeSetter instance) {
        modeSetter = instance;
    }
}