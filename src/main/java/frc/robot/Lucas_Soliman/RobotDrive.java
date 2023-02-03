package frc.robot.Lucas_Soliman;

import static frc.robot.Utility.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import frc.robot.Lucas_Soliman.Arm.*;
import frc.robot.Lucas_Soliman.DriveModes.*;

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
    private final DriveMode[] defaultModes = new DriveMode[] {
        new Wrist(PORT_JOYSTICK, 0)
    };

    private DifferentialDrive driveInstance;
    private DriveMode currentDriveMode;

    public RobotDrive(int topLeft, int bottomLeft, int topRight, int bottomRight, int joyStickID) {
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

        for(DriveMode mode : defaultModes) {
            mode.DriveModeInit();
        }
    }

    //Run this function in teleopPeriodic
    public void robotDriveTeleop() {
        for(DriveMode mode : defaultModes) {
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
}