package frc.robot.Lucas_Soliman.DriveModes;

import frc.robot.Lucas_Soliman.RobotDrive;

import static frc.robot.Utility.*;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * Author: Lucas Soliman
 * Date Created: January 16, 2023
 * 
 * This is the class contains the manual drive mode for the robot.
 * Initialised in RobotDrive.java
 * 
 * This mode is teleOperated
 */
public final class ManualDrive implements DriveMode {
    private RobotDrive BaseInstance;
    private double speedMultiplier;
    private double currentSpeed;
    private XboxController input;

    public ManualDrive(RobotDrive baseDrive, XboxController inputInstance) {
        System.out.println("Init ManualDrive...");
        BaseInstance = baseDrive;
        input = inputInstance;
        speedMultiplier = 1;
    }

    @Override
    public void DriveModeInit() {
        SmartDashboard.putString("DB/String 0", "Manual Mode");
    }

    @Override
    public void DriveModePeriodic() {
        // If the driver is pressing the "creep button", use creepdrive speed. Otherwise, use normal speed.
        // If the driver pressed the "flip button" this periodic cycle, invert speed multiplier.
        currentSpeed = input.getLeftTriggerAxis() > 0.5 ? DRIVE_CREEPSPEED : DRIVE_NORMALSPEED;
        if(input.getStartButtonPressed()) { speedMultiplier *= -1; }
        
        /*
        double xInput = input.applyDeadZone(input.stickX()) * currentSpeed * speedMultiplier;
        double yInput = input.applyDeadZone(input.stickY()) * currentSpeed * -speedMultiplier;
        BaseInstance.DriveRobot(xInput, -yInput);
        */

        double leftInput = input.getLeftY() * currentSpeed * speedMultiplier;
        double rightInput = input.getRightY() * currentSpeed * speedMultiplier;
        BaseInstance.driveInstance.tankDrive(-leftInput, rightInput);
    }
}