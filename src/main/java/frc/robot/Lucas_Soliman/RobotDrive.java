package frc.robot.Lucas_Soliman;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import frc.robot.Lucas_Soliman.RobotBehaviours.*;
import frc.robot.Lucas_Soliman.RobotBehaviours.CoPilotBehaviours.Lift;
import frc.robot.Lucas_Soliman.RobotBehaviours.PilotBehaviours.Shoulder;
import frc.robot.Lucas_Soliman.RobotBehaviours.PilotBehaviours.Wrist;

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
    private final RobotBehaviour[] defaultModes = new RobotBehaviour[] {
        new Shoulder(), // Mainpilot controlled
        new Wrist(), // Mainpilot controlled
        new Lift() // Copilot controlled
    };

    private DifferentialDrive driveInstance;
    private RobotBehaviour currentDriveMode;

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

        for(RobotBehaviour mode : defaultModes) {
            mode.BehaviourInit();
        }
    }

    //Run this function in teleopPeriodic
    public void robotDriveTeleop() {
        for(RobotBehaviour mode : defaultModes) {
            mode.BehaviourPeriodic();
        }

        if(currentDriveMode != null) {
            currentDriveMode.BehaviourPeriodic();
            return;
        }
    }

    // A function called by DriveModeSetter.java to make this class tick specific drive modes.
    public void setDriveMode(RobotBehaviour mode) {
        currentDriveMode = mode;
        mode.BehaviourInit();
    }

    // A function for drive modes to interface with DifferentialDrive instance.
    // All parameters are in deltas, and not a set positikon/rotation.
    public void DriveRobot(double motorPower, double zRotation) {
        driveInstance.arcadeDrive(motorPower, zRotation);
    }
}