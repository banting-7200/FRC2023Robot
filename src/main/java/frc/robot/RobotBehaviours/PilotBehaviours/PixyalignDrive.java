package frc.robot.RobotBehaviours.PilotBehaviours;

import static frc.robot.Core.Utility.*;

import edu.wpi.first.wpilibj.DigitalInput;
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
public class PixyalignDrive implements RobotBehaviour {
    //private final I2C_Interface ARDUINO_INTERFACE = new I2C_Interface(1);
    private final DigitalInput shouldRotate = new DigitalInput(5);
    private final DigitalInput leftRight = new DigitalInput(6);
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
       System.out.println("SHOULDROTATE: " + shouldRotate.get() + "//" + "LEFTRIGHT: " + leftRight.get());

       /*
        if(shouldRotate.get()) {
            double direction = leftRight.get() ? 1.0 : -1.0;
            double nY = PilotControls.PILOT_Y.get();

            boolean creepEnabled = PilotControls.JOYSTICK_PILOT.getRawButton(PilotControls.DRIVING_CREEPTOGGLE);
            double mappedInputY = MapValue(Math.abs(nY), 0, 1, 0, creepEnabled ? 0.25 : 0.5);

            if(Math.abs(nY) <= 0.2) {
                mappedInputY = -0.5;
            }

            double finalFwdSpeed = Math.signum(nY) * (0.5 + mappedInputY);
            baseDriveInstance.DriveRobot(direction * 0.6, finalFwdSpeed);
        } else {
            baseDriveInstance.DriveRobot(0, 0);
        }
        */

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
