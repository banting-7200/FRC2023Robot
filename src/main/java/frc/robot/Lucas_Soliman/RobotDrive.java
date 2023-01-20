package frc.robot.Lucas_Soliman;

import static frc.robot.Utility.*;

import java.util.HashMap;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import frc.robot.Lucas_Soliman.DriveModes.*;

/*
 * Author: Lucas Soliman
 * Date Created: January 9, 2023
 * 
 * The class responsible for driving/moving the robot using motor references.
 */
public class RobotDrive {
    private HashMap<Integer, DriveMode> DriveModes;
    private DifferentialDrive driveInstance;
    private Input inputInstance;

    public RobotDrive(int topLeft, int bottomLeft, int topRight, int bottomRight, int joyStickID) {
        inputInstance = new Input(joyStickID);

        //Initialise all Drive Modes
        DriveModes = new HashMap<Integer, DriveMode>();
        DriveModes.put(DRIVEMODE_MANUAL, new ManualDrive(this));
        DriveModes.put(DRIVEMODE_AUTOBALANCE, new BalanceDrive(this));
        DriveModes.put(DRIVEMODE_PIXYALIGN, new ManualDrive(this));

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
        // Iterates over each drive mode that is in array form
        // Checks if the joystick is being pressed at the given drive mode
        // Sets the mode if required button is pressed, and prints the current drive mode.
        for(int mode : DRIVEMODE_MODEARRAY) {
            if(inputInstance.getBtnPress(mode)) {
                CurrentDriveMode = mode;
            }
        }

        //Run the current mode's periodic function.
        DriveModes.get(CurrentDriveMode).DriveModePeriodic();
    }

    // A function for drive modes to interface with DifferentialDrive instance.
    // All parameters are in deltas, and not a set positikon/rotation.
    public void DriveRobot(double motorPower, double zRotation) {
        driveInstance.arcadeDrive(motorPower, zRotation);
    }
}