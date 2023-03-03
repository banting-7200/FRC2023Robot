package frc.robot.RobotBehaviours.PilotBehaviours;

import static frc.robot.Core.Utility.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Core.DriveModeSetter;
import frc.robot.Core.RobotDrive;
import frc.robot.Core.ExternalIO.I2C_Interface;
import frc.robot.Interfaces.RobotBehaviour;

/*
 * This mode of driving is semi-autonomous
 *  - Moving forward and back is teleoperated
 *  - Rotating left/right is automated
 * 
 * Meant to align with closest game piece to center (cone/cube)
 */
public class PixyalignDrive implements RobotBehaviour{
    private final I2C_Interface ARDUINO_INTERFACE = new I2C_Interface(1);
    private RobotDrive baseDriveInstance;

    public PixyalignDrive(RobotDrive baseInstance) {
        System.out.println("Init PixyalignDrive...");
        baseDriveInstance = baseInstance;
    }

    @Override
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
        SmartDashboard.putString(SmartDashboardIDs.DRIVEMODEID, "Pixyalign Drive");
    }

    @Override
    public void BehaviourPeriodic() {
        baseDriveInstance.setDriveMode(DriveModeSetter.DriveModes.get(DRIVEMODE_MANUAL));

        /*
        //Data returned is a string form of average X-Position of game pieces
        byte[] data = ARDUINO_INTERFACE.readI2C(1);

        if(data == null) {
            System.out.println("NULL DATA");
            return;
        }
        
        byte direction = data[0];
        System.out.println(direction);

        //The below calculations are partially derived from ManualDrive.java
        double fwdInput = PilotControls.PILOT_Y.get();
        boolean creepEnabled = PilotControls.JOYSTICK_PILOT.getRawButton(PilotControls.DRIVING_CREEPTOGGLE);
        double finalFwdSpeed = fwdInput * (creepEnabled ? DRIVE_CREEPSPEED : DRIVE_NORMALSPEED);

        //Apply the automated rotation of the pixycam, and the manual input for forward and back
        baseDriveInstance.DriveRobot(direction * 0.6, finalFwdSpeed);
        */
    }
}
