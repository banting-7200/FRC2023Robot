package frc.robot;

import java.util.HashMap;

import frc.robot.InputDevices.Input;
import frc.robot.RobotBehaviours.*;
import frc.robot.RobotBehaviours.CoPilotBehaviours.BalanceDrive;
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
    private final Input MAIN_INPUT = new Input(PORT_JOYSTICK);
    private final Input CO_INPUT = new Input(PORT_COJOYSTICK);

    private HashMap<Integer, RobotBehaviour> DriveModes;

    //Key represents the ID of Drivemode, value represents button on joystick to select mode.
    private final HashMap<Integer, Integer> DriveModeBinds = new HashMap<>() {{
        //MainPilot modes (Buttons on pilot joystick)
        put(DRIVEMODE_MANUAL, 7);
        put(DRIVEMODE_PIXYALIGN, 8);

        //Copilot modes (Buttons on copilot joystick)
        put(DRIVEMODE_AUTOBALANCE, 7);
    }};

    private int currDriveMode;
    private RobotDrive driveInstance;
    private int[] mainPilotModes;
    private int[] coPilotModes;

    public DriveModeSetter(RobotDrive driveInstance, int[] coPilotModes, int[] mainPilotModes) {
        this.driveInstance = driveInstance;

        // Initialise all Drive Modes
        DriveModes = new HashMap<Integer, RobotBehaviour>();
        DriveModes.put(DRIVEMODE_MANUAL, new ManualDrive(driveInstance));
        DriveModes.put(DRIVEMODE_AUTOBALANCE, new BalanceDrive(driveInstance));
        DriveModes.put(DRIVEMODE_PIXYALIGN, new PixyalignDrive(driveInstance)); // PIXYALIGN being a manualDrive is temporary.

        this.mainPilotModes = mainPilotModes;
        this.coPilotModes = coPilotModes;
        currDriveMode = 7;
    }

    public void driveModeSetterTeleop() {
        // Iterates over each drive mode that is in array form
        // Checks if the joystick is being pressed at the given drive mode
        // Sets the mode if required button is pressed, and prints the current drive mode.
        for(int mode : mainPilotModes) {
            if(MAIN_INPUT.getBtnPress(DriveModeBinds.get(mode))) {
                currDriveMode = mode;
            }
        }

        for(int mode : coPilotModes) {
            if(CO_INPUT.getBtnPress(DriveModeBinds.get(mode))) {
                currDriveMode = mode;
            }
        }

        // Run the current mode's periodic function.
        driveInstance.setDriveMode(DriveModes.get(currDriveMode));
    }
}
