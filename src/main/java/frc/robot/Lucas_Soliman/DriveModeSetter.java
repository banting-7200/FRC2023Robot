package frc.robot.Lucas_Soliman;

import java.util.HashMap;

import frc.robot.Lucas_Soliman.DriveModes.*;
import frc.robot.Lucas_Soliman.InputDevices.Input;

import static frc.robot.Utility.*;

/*
 * Author: Lucas Soliman
 * Date-Created: January 25, 2023
 * 
 * This class uses a joystick and button binds to set the RobotDrive to a specified mode
 * This class depends on RobotDrive.java
 */
public final class DriveModeSetter {
    private HashMap<Integer, DriveMode> DriveModes;
    private RobotDrive driveInstance;
    private Input input;

    public DriveModeSetter(RobotDrive driveInstance, int joystickPort, int mainJoystickPort) {
        this.driveInstance = driveInstance;
        input = new Input(joystickPort);
        Input mainInput = new Input(mainJoystickPort);

        // Initialise all Drive Modes
        DriveModes = new HashMap<Integer, DriveMode>();
        DriveModes.put(DRIVEMODE_MANUAL, new ManualDrive(driveInstance, input));
        DriveModes.put(DRIVEMODE_AUTOBALANCE, new BalanceDrive(driveInstance));
        DriveModes.put(DRIVEMODE_PIXYALIGN, new PixyalignDrive(driveInstance, mainInput)); // PIXYALIGN being a manualDrive is temporary.

        // Set the drive mode to manual by default.
        CurrentDriveMode = DRIVEMODE_MANUAL;
    }

    public void driveModeSetterTeleop() {
        // Iterates over each drive mode that is in array form
        // Checks if the joystick is being pressed at the given drive mode
        // Sets the mode if required button is pressed, and prints the current drive mode.
        for(int mode : DRIVEMODE_MODEARRAY) {
            if(input.getBtnPress(mode)) {
                CurrentDriveMode = mode;
            }
        }

        // Run the current mode's periodic function.
        driveInstance.setDriveMode(DriveModes.get(CurrentDriveMode));
    }
}
