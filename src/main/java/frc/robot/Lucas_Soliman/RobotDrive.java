package frc.robot.Lucas_Soliman;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
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
    public DifferentialDrive driveInstance;
    private DriveMode currentDriveMode;
    private PWMSparkMax[] driveMotors;

    public RobotDrive(int topLeft, int bottomLeft, int topRight, int bottomRight, Joystick coPoilotDevice) {

        //Create instances of left motors
        PWMSparkMax tl = new PWMSparkMax(topLeft);
        PWMSparkMax bl = new PWMSparkMax(bottomLeft);
        
        //Create instances of right motors
        PWMSparkMax tr = new PWMSparkMax(topRight);
        PWMSparkMax br = new PWMSparkMax(bottomRight);
        driveMotors = new PWMSparkMax[] {
            tl, bl, tr, br
        };

        //Group the left and right motors
        MotorControllerGroup leftGroup = new MotorControllerGroup(tl, bl);
        MotorControllerGroup rightGroup = new MotorControllerGroup(tr, br);

        //Create new DifferentialDrive instance with motor groups.
        //driveInstance = new DifferentialDrive(leftGroup, rightGroup);

        for(DriveMode mode : DriveModeSetter.defaultModes) {
            mode.DriveModeInit();
        }
    }

    //Run this function in teleopPeriodic
    public void robotDriveTeleop() {
        for(DriveMode mode : DriveModeSetter.defaultModes) {
            mode.DriveModePeriodic();
        }

        /*
        if(currentDriveMode == null) {
            System.out.println("No DriveMode being run!");
            return;
        }

        currentDriveMode.DriveModePeriodic();
         */
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

    public PWMSparkMax[] getDriveMotors() {
        return driveMotors;
    }
}