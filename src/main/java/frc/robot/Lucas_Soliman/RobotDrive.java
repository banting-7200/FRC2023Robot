package frc.robot.Lucas_Soliman;

import static frc.robot.Utility.*;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

/*
 * Author: Lucas Soliman
 * Date Created: January 9, 2023
 * 
 * The class responsible for driving/moving the robot using motor references.
 */
public class RobotDrive {
    private DifferentialDrive driveInstance;
    private double speedMultiplier;
    private double currentSpeed;

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
        currentSpeed = DRIVE_NORMALSPEED;
        speedMultiplier = 1;
    }

    public void robotDriveTeleopInit() {
        currentSpeed = DRIVE_NORMALSPEED;
    }

    //Run this function in teleopPeriodic
    public void robotDriveTeleop() {

        // If the driver is pressing the "creep button", use creepdrive speed. Otherwise, use normal speed.
        // If the driver pressed the "flip button" this periodic cycle, invert speed multiplier.
        currentSpeed = INPUT.getBtn(CTRLS_CREEPBTN) ? DRIVE_CREEPSPEED : DRIVE_NORMALSPEED;
        if(INPUT.getBtnPress(CTRLS_FLIPBTN)) { speedMultiplier *= -1; }
        
        double xInput = INPUT.applyDeadZone(INPUT.stickX()) * currentSpeed * speedMultiplier;
        double yInput = INPUT.applyDeadZone(INPUT.stickY()) * currentSpeed * -speedMultiplier;
        DriveRobot(xInput, yInput);
    }

    public void DriveRobot(double motorPower, double zRotation) {
        driveInstance.arcadeDrive(motorPower, zRotation);
    }
}