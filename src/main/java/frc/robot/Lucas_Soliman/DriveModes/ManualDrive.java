package frc.robot.Lucas_Soliman.DriveModes;

import frc.robot.Lucas_Soliman.RobotDrive;
import static frc.robot.Utility.*;

/*
 * Author: Lucas Soliman
 * Date Created: January 16, 2023
 * 
 * This is the class contains the manual drive mode for the robot.
 * Initialised in RobotDrive.java
 */
public class ManualDrive implements DriveMode {
    private RobotDrive BaseInstance;
    private double speedMultiplier;
    private double currentSpeed;

    public ManualDrive(RobotDrive baseDrive) {
        System.out.println("Init ManualDrive...");
        BaseInstance = baseDrive;
    }

    @Override
    public void DriveModePeriodic() {
        // If the driver is pressing the "creep button", use creepdrive speed. Otherwise, use normal speed.
        // If the driver pressed the "flip button" this periodic cycle, invert speed multiplier.
        currentSpeed = INPUT.getBtn(CTRLS_CREEPBTN) ? DRIVE_CREEPSPEED : DRIVE_NORMALSPEED;
        if(INPUT.getBtnPress(CTRLS_FLIPBTN)) { speedMultiplier *= -1; }
        
        double xInput = INPUT.applyDeadZone(INPUT.stickX()) * currentSpeed * speedMultiplier;
        double yInput = INPUT.applyDeadZone(INPUT.stickY()) * currentSpeed * -speedMultiplier;
        BaseInstance.DriveRobot(xInput, yInput);
    }
}