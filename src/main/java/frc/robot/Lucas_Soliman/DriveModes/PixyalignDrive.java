package frc.robot.Lucas_Soliman.DriveModes;

import static frc.robot.Utility.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
        baseInstance = baseDriveInstance;
    }

    @Override
    public void DriveModeInit() {
        SmartDashboard.putString("DB/String 0", "Pixyalign Mode");
    }

    @Override
    public void DriveModePeriodic() {
        //Data returned is a string form of average X-Position of game pieces
        String data = I2C_INTERFACE.readI2CString();
        int avgX = Integer.parseInt(data);
    }
}
