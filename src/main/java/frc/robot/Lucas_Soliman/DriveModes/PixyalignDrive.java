package frc.robot.Lucas_Soliman.DriveModes;

import static frc.robot.Utility.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Lucas_Soliman.*;
import frc.robot.Lucas_Soliman.InputDevices.Input;

/*
 * This mode of driving is semi-autonomous
 *  - Moving forward and back is teleoperated
 *  - Rotating left/right is automated
 * 
 * Meant to align with closest game piece to center (cone/cube)
 */
public class PixyalignDrive implements DriveMode{
    private RobotDrive baseDriveInstance;
    private Input inputDevice;

    public PixyalignDrive(RobotDrive baseInstance, Input driveStick) {
        baseDriveInstance = baseInstance;
        inputDevice = driveStick;
    }

    @Override
    public void DriveModeInit() {
        SmartDashboard.putString("DB/String 0", "Pixyalign Mode");
    }

    @Override
    public void DriveModePeriodic() {
        //Data returned is a string form of average X-Position of game pieces
        byte[] data = I2C_INTERFACE.readI2C(1);
        byte direction = data[0];

        //The below calculations are partially derived from ManualDrive.java
        double fwdInput = inputDevice.stickY();
        boolean creepEnabled = inputDevice.getBtn(CTRLS_CREEPBTN);
        double finalFwdSpeed = fwdInput * (creepEnabled ? 0.6 : 1);

        //Apply the automated rotation of the pixycam, and the manual input for forward and back
        baseDriveInstance.DriveRobot(direction * 0.6, finalFwdSpeed);
    }
}
