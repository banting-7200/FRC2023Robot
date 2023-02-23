package frc.robot;

import java.util.HashMap;
import frc.robot.Interfaces.RobotBehaviour;
import frc.robot.RobotBehaviours.PilotBehaviours.BalanceDrive;
import frc.robot.RobotBehaviours.PilotBehaviours.ManualDrive;
import frc.robot.RobotBehaviours.PilotBehaviours.PixyalignDrive;

import static frc.robot.Utility.*;

/*
 * Author: Lucas Soliman
 * Date-Created: January 25, 2023
 * 
 * This class uses a joystick and button binds to set the RobotDrive to a specified mode
 * This class depends on RobotDrive.java
 */
public final class DriveModeSetter {
    private HashMap<Integer, RobotBehaviour> DriveModes;

    //Key represents the ID of Drivemode, value represents button on joystick to select mode.
    private final HashMap<Integer, Integer> DriveModeBinds = new HashMap<>() {{
        //MainPilot modes (Buttons on pilot joystick)
        put(DRIVEMODE_MANUAL, PilotControls.DRIVEMODE_MANUAL);
        put(DRIVEMODE_PIXYALIGN, PilotControls.DRIVEMODE_PIXYALIGN);
        put(DRIVEMODE_AUTOBALANCE, PilotControls.DRIVEMODE_AUTOBALANCE);
    }};

    private int currDriveMode;
    private RobotDrive driveInstance;
    private int[] modesToControl;

    public DriveModeSetter(RobotDrive driveInstance, int[] modesToControl) {
        this.driveInstance = driveInstance;

        // Initialise all Drive Modes
        DriveModes = new HashMap<Integer, RobotBehaviour>();
        DriveModes.put(DRIVEMODE_MANUAL, new ManualDrive(driveInstance));
        DriveModes.put(DRIVEMODE_AUTOBALANCE, new BalanceDrive(driveInstance));
        DriveModes.put(DRIVEMODE_PIXYALIGN, new PixyalignDrive(driveInstance)); // PIXYALIGN being a manualDrive is temporary.

        this.modesToControl = modesToControl;
        currDriveMode = 7;
    }

    public void driveModeSetterTeleop() {
        // Iterates over each drive mode that is in array form
        // Checks if the joystick is being pressed at the given drive mode
        // Sets the mode if required button is pressed, and prints the current drive mode.
        for(int mode : modesToControl) {
            if(PilotControls.JOYSTICK_PILOT.getRawButton(DriveModeBinds.get(mode))) {
                currDriveMode = mode;
            }
        }

        // Run the current mode's periodic function.
        driveInstance.setDriveMode(DriveModes.get(currDriveMode));
    }
}
