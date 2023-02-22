package frc.robot.RobotBehaviours.PilotBehaviours;

import frc.robot.RobotDrive;
import frc.robot.InputDevices.Input;
import frc.robot.RobotBehaviours.RobotBehaviour;

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
    private final double MANUAL_STICKX = INPUT_DEVICE.stickX();
    private final double MANUAL_STICKY = INPUT_DEVICE.stickY();

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
        SmartDashboard.putString("Current Mode: ", "Manual Mode");
    }

    @Override
    public void BehaviourPeriodic() {
        // If the driver is pressing the "creep button", use creepdrive speed. Otherwise, use normal speed.
        // If the driver pressed the "flip button" this periodic cycle, invert speed multiplier.
        currentSpeed = (-INPUT_DEVICE.joystickInstance.getRawAxis(3) + 1) / 2.0;
        if(INPUT_DEVICE.getBtnPress(2)) { speedMultiplier *= -1; }
        
        double xInput = INPUT_DEVICE.stickX() * currentSpeed;
        double yInput = INPUT_DEVICE.stickY() * currentSpeed * speedMultiplier;
        BaseInstance.DriveRobot(xInput, -yInput);
    }
}