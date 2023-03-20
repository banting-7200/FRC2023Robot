package frc.robot.RobotBehaviours.PilotBehaviours;

import frc.robot.Robot;
import frc.robot.Core.RobotDrive;
import frc.robot.Interfaces.RobotBehaviour;

import static frc.robot.Core.Utility.*;

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
public final class ManualDrive implements RobotBehaviour {
    private RobotDrive BaseInstance;
    private double speedMultiplier;
    private double currentSpeed;

    public ManualDrive(RobotDrive baseDrive) {
        System.out.println("ManualDrive Init...");
        BaseInstance = baseDrive;
        speedMultiplier = 1;
    }

    @Override
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
        SmartDashboard.putString(SmartDashboardIDs.DRIVEMODEID, "Manual Mode");
        Robot.BREAK.set(true);
    }

    @Override
    public void BehaviourPeriodic() {
        // If the driver is pressing the "creep button", use creepdrive speed. Otherwise, use normal speed.
        // If the driver pressed the "flip button" this periodic cycle, invert speed multiplier.
        if(PilotControls.FLIP.get()) { speedMultiplier *= -1; }
        currentSpeed = PilotControls.CREEP.get() ? DRIVE_CREEPSPEED : DRIVE_NORMALSPEED;

        double xInput = PilotControls.PILOT_X.get() * currentSpeed;
        double yInput = PilotControls.PILOT_Y.get() * currentSpeed * speedMultiplier;

        xInput /= 2.0;
        yInput /= 2.0;

        SmartDashboard.putString("Driver-Inputs: ", "X: " + xInput + ", Y" + yInput  * speedMultiplier);
        BaseInstance.DriveRobot(0.48 + xInput, 0.48 + yInput * speedMultiplier);
    }
}