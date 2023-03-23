package frc.robot.Core;

import static frc.robot.Core.Utility.*;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Core.Utility.PilotControls;
import frc.robot.Interfaces.RobotBehaviour;
import frc.robot.RobotBehaviours.PilotBehaviours.BalanceDrive;
import frc.robot.RobotBehaviours.PilotBehaviours.ManualDrive;
import frc.robot.RobotBehaviours.PilotBehaviours.PixyalignDrive;

/*
 * Author: Lucas Soliman
 * Date-Created: January 25, 2023
 * 
 * This class uses a joystick and button binds to set the RobotDrive to a specified mode
 * This class depends on RobotDrive.java
 */
public final class DriveModeSetter {
    public static HashMap<Integer, RobotBehaviour> DriveModes;
    private static boolean isInitDrive = false;
   
    private List<Supplier<Integer>> modeSuppliers;
    private RobotDrive driveInstance;
    private int currDriveMode;

    public DriveModeSetter(RobotDrive driveInstance, List<Supplier<Integer>> modeSuppliers) {
        this.driveInstance = driveInstance;
        this.modeSuppliers = modeSuppliers;

        if(!isInitDrive) {
            DriveModes = new HashMap<Integer, RobotBehaviour>();
            DriveModes.put(DRIVEMODE_MANUAL, new ManualDrive(driveInstance));
            DriveModes.put(DRIVEMODE_AUTOBALANCE, new BalanceDrive(driveInstance)); //new BalanceDrive(driveInstance));
            DriveModes.put(DRIVEMODE_PIXYALIGN, new PixyalignDrive(driveInstance)); //new PixyalignDrive(driveInstance)); // PIXYALIGN being a manualDrive is temporary
            isInitDrive = true;
        }

        currDriveMode = DRIVEMODE_MANUAL;
        driveInstance.setDriveMode(DriveModes.get(DRIVEMODE_MANUAL));
    }

    public void driveModeSetterTeleop() {
        boolean supplyToggled = false;

        // Iterates over each drive mode that is in array form
        // Checks if the joystick is being pressed at the given drive mode
        // Sets the mode if required button is pressed, and prints the current drive mode.
        for(Supplier<Integer> modeSupply : modeSuppliers) {
            int supply = modeSupply.get();
            if(supply != -1) {
                currDriveMode = supply;
                supplyToggled = true;
            }
        }

        if(supplyToggled) {
            // Run the current mode's periodic function.
            driveInstance.setDriveMode(DriveModes.get(currDriveMode));
        }
    }
}
