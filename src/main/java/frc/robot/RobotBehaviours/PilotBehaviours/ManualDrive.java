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

        if(PilotControls.CREEP.get()) {
            double creepX = PilotControls.PILOT_X.get();
            double creepY = PilotControls.PILOT_Y.get();

            double mappedInputX = MapValue(Math.abs(creepX), 0, 1, 0, 0.25);
            double mappedInputY = MapValue(Math.abs(creepY), 0, 1, 0, 0.25);

            if(Math.abs(creepY) <= 0.2) {
                mappedInputY = -0.5;
            }

            if(Math.abs(creepX) <= 0.3) {
                mappedInputX = -0.5;
            }
            
            xInput = Math.signum(creepX) * (0.5 + mappedInputX);
            yInput = Math.signum(creepY) * (0.5 + mappedInputY);
        } else {
            double nX = PilotControls.PILOT_X.get();
            double nY = PilotControls.PILOT_Y.get();

            double mappedInputX = MapValue(Math.abs(nX), 0, 1, 0, 0.5);
            double mappedInputY = MapValue(Math.abs(nY), 0, 1, 0, 0.6);

            if(Math.abs(nY) <= 0.3) {
                mappedInputY = -0.5;
            }

            if(Math.abs(nX) <= 0.3) {
                mappedInputX = -0.5;
            }

            xInput = Math.signum(nX) * (0.5 + mappedInputX);
            yInput = Math.signum(nY) * (0.5 + mappedInputY);
        }

        SmartDashboard.putString("Driver-Inputs: ", "X: " + xInput + ", Y" + yInput  * speedMultiplier);
        BaseInstance.DriveRobot(xInput, yInput * speedMultiplier);
    }
}