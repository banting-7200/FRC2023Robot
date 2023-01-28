package frc.robot.Lucas_Soliman.DriveModes;

import frc.robot.Lucas_Soliman.Input;
import frc.robot.Lucas_Soliman.RobotDrive;
import static frc.robot.Utility.*;

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
    private Input input;

    public ManualDrive(RobotDrive baseDrive, Input inputInstance) {
        System.out.println("Init ManualDrive...");
        BaseInstance = baseDrive;
        speedMultiplier = 1;
        input = inputInstance;
    }

    @Override
    public void DriveModeInit() {
        SmartDashboard.putString("DB/String 0", "Manual Mode");
    }

    @Override
    public void DriveModePeriodic() {
        // If the driver is pressing the "creep button", use creepdrive speed. Otherwise, use normal speed.
        // If the driver pressed the "flip button" this periodic cycle, invert speed multiplier.
        currentSpeed = input.getBtn(CTRLS_CREEPBTN) ? DRIVE_CREEPSPEED : DRIVE_NORMALSPEED;
        if(input.getBtnPress(CTRLS_FLIPBTN)) { speedMultiplier *= -1; }
        
        double xInput = input.applyDeadZone(input.stickX()) * currentSpeed * speedMultiplier;
        double yInput = input.applyDeadZone(input.stickY()) * currentSpeed * -speedMultiplier;
        BaseInstance.DriveRobot(xInput, -yInput);
    }
}