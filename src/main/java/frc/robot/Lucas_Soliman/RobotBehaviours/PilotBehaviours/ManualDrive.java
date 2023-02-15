package frc.robot.Lucas_Soliman.RobotBehaviours.PilotBehaviours;

import frc.robot.Lucas_Soliman.RobotDrive;
import frc.robot.Lucas_Soliman.InputDevices.Input;
import frc.robot.Lucas_Soliman.RobotBehaviours.RobotBehaviour;

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
public final class ManualDrive implements RobotBehaviour {

    private final Input INPUT_DEVICE = new Input(PORT_JOYSTICK);
    private final double MANUAL_STICKX = INPUT_DEVICE.applyDeadZone(INPUT_DEVICE.stickX());
    private final double MANUAL_STICKY = INPUT_DEVICE.applyDeadZone(INPUT_DEVICE.stickY());

    private final boolean MANUAL_FLIP = INPUT_DEVICE.getBtnPress(4);
    private final boolean MANUAL_CREEP = INPUT_DEVICE.getBtn(3);

    private RobotDrive BaseInstance;
    private double speedMultiplier;
    private double currentSpeed;

    public ManualDrive(RobotDrive baseDrive) {
        System.out.println("Init ManualDrive...");
        BaseInstance = baseDrive;
        speedMultiplier = 1;
    }

    @Override
    public void BehaviourInit() {
        SmartDashboard.putString("DB/String 0", "Manual Mode");
    }

    @Override
    public void BehaviourPeriodic() {
        // If the driver is pressing the "creep button", use creepdrive speed. Otherwise, use normal speed.
        // If the driver pressed the "flip button" this periodic cycle, invert speed multiplier.
        currentSpeed = MANUAL_CREEP ? DRIVE_CREEPSPEED : DRIVE_NORMALSPEED;
        if(MANUAL_FLIP) { speedMultiplier *= -1; }
        
        double xInput = MANUAL_STICKX * currentSpeed;
        double yInput = MANUAL_STICKY * currentSpeed * speedMultiplier;
        BaseInstance.DriveRobot(xInput, -yInput);
    }
}