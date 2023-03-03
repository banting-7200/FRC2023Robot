package frc.robot.RobotBehaviours.PilotBehaviours;

import frc.robot.Core.RobotDrive;
import frc.robot.Interfaces.RobotBehaviour;

import static frc.robot.Core.Utility.*;

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
public final class ManualDrive implements RobotBehaviour {
    private RobotDrive BaseInstance;
    private double speedMultiplier;
    private double currentSpeed;

    public ManualDrive(RobotDrive baseDrive) {
        System.out.println("Init ManualDrive...");
        BaseInstance = baseDrive;
        speedMultiplier = 1;
    }

    @Override
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
        SmartDashboard.putString(SmartDashboardIDs.DRIVEMODEID, "Manual Mode");
    }

    @Override
    public void BehaviourPeriodic() {
        // If the driver is pressing the "creep button", use creepdrive speed. Otherwise, use normal speed.
        // If the driver pressed the "flip button" this periodic cycle, invert speed multiplier.
        if(PilotControls.JOYSTICK_PILOT.getRawButtonPressed(PilotControls.DRIVING_FLIPTOGGLE)) { speedMultiplier *= -1; }
        currentSpeed = PilotControls.JOYSTICK_PILOT.getRawButton(PilotControls.DRIVING_CREEPTOGGLE) ? DRIVE_CREEPSPEED : DRIVE_NORMALSPEED;

        double xInput = PilotControls.PILOT_X.get() * currentSpeed;
        double yInput = PilotControls.PILOT_Y.get() * currentSpeed * speedMultiplier;

        if(PilotControls.JOYSTICK_PILOT.getRawButton(PilotControls.DRIVING_CREEPTOGGLE)) {
            xInput = Clamp(xInput, -DRIVE_CREEPSPEED, DRIVE_CREEPSPEED);
        }
        
        BaseInstance.DriveRobot(xInput, yInput);
    }
}