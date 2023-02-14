package frc.robot.Lucas_Soliman;

import java.util.HashMap;

import frc.robot.Lucas_Soliman.DriveModes.*;
import frc.robot.Lucas_Soliman.DriveModes.DefaultModes.*;
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
    public final DriveMode[] defaultModes = new DriveMode[] {
        new Wrist(MOTOR_WRIST, PORT_COJOYSTICK),
        new Lift(CAN_LIFTMOTORID, PORT_COJOYSTICK)
    };
    
    private HashMap<Integer, DriveMode> DriveModes;
    private RobotDrive driveInstance;
    private Input mainInput;
    private Input coInput;

    public DriveModeSetter(int joystickPort, int mainJoystickPort) {
        mainInput = new Input(mainJoystickPort);
        coInput = new Input(joystickPort);

        // Initialise all Drive Modes
        DriveModes = new HashMap<Integer, DriveMode>();
        DriveModes.put(DRIVEMODE_MANUAL, new ManualDrive(driveInstance, mainInput));
        DriveModes.put(DRIVEMODE_AUTOBALANCE, new BalanceDrive(driveInstance));
        DriveModes.put(DRIVEMODE_PIXYALIGN, new PixyalignDrive(driveInstance, mainInput)); // PIXYALIGN being a manualDrive is temporary.

        // Set the drive mode to manual by default.
        CurrentDriveMode = DRIVEMODE_MANUAL;
    }

    public void driveModeSetterTeleop() {
        if(driveInstance == null) {
            System.out.println("DriveModeSetter: No RobotDrive instance attached...");
            return;
        }

        // Iterates over each drive mode that is in array form
        // Checks if the joystick is being pressed at the given drive mode
        // Sets the mode if required button is pressed, and prints the current drive mode.
        for(int mode : DRIVEMODE_MODEARRAY) {
            if(coInput.getBtnPress(mode)) {
                CurrentDriveMode = mode;
            }
        }

        // Run the current mode's periodic function.
        driveInstance.setDriveMode(DriveModes.get(CurrentDriveMode));
    }

    //This function is called externally to assign a RobotDrive instance
    public void attachBase(RobotDrive base) {
        driveInstance = base;

        //After attaching, initialise defaultModes for running in RobotDrive
        for(DriveMode mode : defaultModes) {
            mode.DriveModeInit();
        }
    }
}
