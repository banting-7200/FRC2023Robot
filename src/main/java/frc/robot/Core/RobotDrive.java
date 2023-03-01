package frc.robot.Core;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

import frc.robot.Interfaces.RobotBehaviour;
import frc.robot.RobotBehaviours.CoPilotBehaviours.CoPilotAutoRunner;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.*;

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
        //TalonFX Motor Systems
        new Lift(), // Copilot controlled
        new Shoulder(), // Pilot Controlled

        //Pneumatic Systems
        new Kicker(), // Copilot controlled
        new Claw(), // Copilot Controlled

        //PWM Systems
        new Wrist(), // Pilot Controlled
        new Lights(), // Copilot controlled
        new CoPilotAutoRunner()
    };


    private final boolean defaultOnly = false;
    
    private DifferentialDrive driveInstance;
    private RobotBehaviour currentDriveMode;
    private boolean isDefaultOnly;

    public RobotDrive(int topLeft, int bottomLeft, int topRight, int bottomRight) {
        isDefaultOnly = defaultOnly;

        for(RobotBehaviour mode : defaultModes) {
            mode.BehaviourInit(defaultModes);
        }

        if(isDefaultOnly) {
            return;
        }

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
        for(RobotBehaviour mode : defaultModes) {
            mode.BehaviourPeriodic();
        }

        if(currentDriveMode != null && isDefaultOnly == false) {
            currentDriveMode.BehaviourPeriodic();
        }
    }

    // A function called by DriveModeSetter.java to make this class tick specific drive modes.
    public void setDriveMode(RobotBehaviour mode) {
        currentDriveMode = mode;
        currentDriveMode.BehaviourInit(defaultModes);
    }

    // A function for drive modes to interface with DifferentialDrive instance.
    // All parameters are in deltas, and not a set positikon/rotation.
    public void DriveRobot(double motorPower, double zRotation) {
        if(!isDefaultOnly) {
            driveInstance.arcadeDrive(motorPower, zRotation);
        }
    }
}