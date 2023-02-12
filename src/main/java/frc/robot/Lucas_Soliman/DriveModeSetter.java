package frc.robot.Lucas_Soliman;

import java.util.HashMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Lucas_Soliman.DriveModes.*;

import static frc.robot.Utility.*;

/*
 * Author: Lucas Soliman
 * Date-Created: January 25, 2023
 * 
 * This class uses a joystick and button binds to set the RobotDrive to a specified mode
 * This class depends on RobotDrive.java
 */
public final class DriveModeSetter {
    public static final DriveMode[] defaultModes = new DriveMode[] {
        new Lift(new Joystick(PORT_JOYSTICK)),
        new Wrist(5, new Joystick(PORT_JOYSTICK)),
        new shoulder(new Joystick(PORT_JOYSTICK))
    };

    private HashMap<Integer, DriveMode> DriveModes;
    private RobotDrive driveInstance;
    private Joystick input;

    public DriveModeSetter(RobotDrive driveInstance, Joystick coPilotDevice, XboxController mainPilotDevice) {
        this.driveInstance = driveInstance;

        // Initialise all Drive Modes
        DriveModes = new HashMap<Integer, DriveMode>();
        DriveModes.put(DRIVEMODE_MANUAL, new ManualDrive(driveInstance, mainPilotDevice));
        DriveModes.put(DRIVEMODE_AUTOBALANCE, new BalanceDrive(driveInstance));
        DriveModes.put(DRIVEMODE_PIXYALIGN, new PixyalignDrive(driveInstance, mainPilotDevice)); // PIXYALIGN being a manualDrive is temporary.

        // Set the drive mode to manual by default.
        CurrentDriveMode = DRIVEMODE_MANUAL;
        input = coPilotDevice;
    }

    public void driveModeSetterTeleop() {
        // Iterates over each drive mode that is in array form
        // Checks if the joystick is being pressed at the given drive mode
        // Sets the mode if required button is pressed, and prints the current drive mode.
        for(int mode : DRIVEMODE_MODEARRAY) {
            if(input.getRawButtonPressed(mode)) {
                CurrentDriveMode = mode;
            }
        }

        // Run the current mode's periodic function.
        driveInstance.setDriveMode(DriveModes.get(CurrentDriveMode));
    }
}
