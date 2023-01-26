package frc.robot.Lucas_Soliman.DriveModes;

import static frc.robot.Utility.*;
import frc.robot.Lucas_Soliman.RobotDrive;

/*
 * This mode of driving is semi-autonomous
 *  - Moving forward and back is teleoperated
 *  - Rotating left/right is automated
 * 
 * Meant to align with closest game piece to center (cone/cube)
 */
public class PixyalignDrive implements DriveMode{
    private RobotDrive baseDriveInstance;

    public PixyalignDrive(RobotDrive baseInstance) {

    }

    @Override
    public void DriveModePeriodic() {
        //Read data from I2C port
        //[0] = turnDirection
        byte[] dataRead = I2C_INTERFACE.readI2C();
    }
}
