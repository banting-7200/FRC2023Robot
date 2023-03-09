package frc.robot.Core;

import static frc.robot.Core.Utility.*;

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
    public static final RobotBehaviour[] defaultModes = new RobotBehaviour[] {
        //TalonFX Motor Systems
        new Lift(), // Copilot controlled
        new Shoulder(), // Pilot Controlled

        //Pneumatic Systems
        INSTANCE_KICKER, // Copilot controlled
        INSTANCE_CLAW, // Copilot Controlled

        //PWM Systems
        new Wrist(), // Pilot Controlled
        new Lights(), // Copilot controlled
        new CoPilotAutoRunner()
    };


    private final boolean defaultOnly = false;
    public PWMSparkMax[] leftMotors;
    public PWMSparkMax[] rightMotors;

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
        leftMotors = new PWMSparkMax[] {
            tl, bl
        };

        //Create instances of right motors
        PWMSparkMax tr = new PWMSparkMax(topRight);
        PWMSparkMax br = new PWMSparkMax(bottomRight);
        rightMotors = new PWMSparkMax[] {
            tr, br
        };

        /*
        //Group the left and right motors
        MotorControllerGroup leftGroup = new MotorControllerGroup(tl, bl);
        MotorControllerGroup rightGroup = new MotorControllerGroup(tr, br);

        //Create new DifferentialDrive instance with motor groups.
        driveInstance = new DifferentialDrive(leftGroup, rightGroup);
        */
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
        if(mode == null) {
            System.out.println("Drive Mode Given is Null");
            return;
        }

        currentDriveMode = mode;
        currentDriveMode.BehaviourInit(defaultModes);
    }

    // A function for drive modes to interface with DifferentialDrive instance.
    // All parameters are in deltas, and not a set positikon/rotation.
    public void DriveRobot(double x, double y) {
        if(!isDefaultOnly) {
            leftMotors[0].set(x + y); //top left
            leftMotors[1].set(x + y); //bottom left

            rightMotors[2].set(x - y); //top left
            rightMotors[3].set(x - y); //bottom left
        }
    }

    public void reinitDefaults() {
        for(RobotBehaviour b : defaultModes) {
            b.BehaviourInit(defaultModes);
        }
    }
}