package frc.robot.Lucas_Soliman;

import static frc.robot.Utility.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

/*
 * Author: Lucas Soliman
 * Date Created: January 9, 2023
 * 
 * The class responsible for driving/moving the robot using motor references.
 */
public class RobotDrive {
    private DifferentialDrive driveInstance;
    private double currentSpeed;

    public RobotDrive(int topLeft, int bottomLeft, int topRight, int bottomRight) {
        //Create instances of left motors
        CANSparkMax tl = new CANSparkMax(topLeft, MotorType.kBrushless);
        CANSparkMax bl = new CANSparkMax(bottomLeft, MotorType.kBrushless);

        //Create instances of right motors
        CANSparkMax tr = new CANSparkMax(topRight, MotorType.kBrushless);
        CANSparkMax br = new CANSparkMax(bottomRight, MotorType.kBrushless);

        //Group the left and right motors
        MotorControllerGroup leftGroup = new MotorControllerGroup(tl, bl);
        MotorControllerGroup rightGroup = new MotorControllerGroup(tr, br);

        //Create new DifferentialDrive instance with motor groups.
        driveInstance = new DifferentialDrive(leftGroup, rightGroup);
        currentSpeed = DRIVE_NORMALSPEED;
    }

    //Run this function in teleopPeriodic
    public void robotDriveTeleop() {
        double xInput = INPUT.applyDeadZone(INPUT.getAxis(JOYSTICK_X)) * currentSpeed;
        double yInput = INPUT.applyDeadZone(INPUT.getAxis(JOYSTICK_Y)) * currentSpeed;
        DriveRobot(yInput, xInput);
    }

    public void DriveRobot(double motorPower, double zRotation) {
        driveInstance.arcadeDrive(motorPower, zRotation);
    }
}