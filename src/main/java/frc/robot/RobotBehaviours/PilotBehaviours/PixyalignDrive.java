package frc.robot.RobotBehaviours.PilotBehaviours;

import static frc.robot.Utility.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotDrive;

import frc.robot.ExternalIO.I2C_Interface;
import frc.robot.InputDevices.Input;
import frc.robot.RobotBehaviours.RobotBehaviour;

/*
 * This mode of driving is semi-autonomous
 *  - Moving forward and back is teleoperated
 *  - Rotating left/right is automated
 * 
 * Meant to align with closest game piece to center (cone/cube)
 */
public class PixyalignDrive implements RobotBehaviour{
    private final I2C_Interface ARDUINO_INTERFACE = new I2C_Interface(1);

    private final Input INPUT_DEVICE = new Input(PORT_JOYSTICK);
    private final double PIXY_FWDAXIS = INPUT_DEVICE.applyDeadZone(INPUT_DEVICE.stickY());
    private final boolean PIXY_CREEP = INPUT_DEVICE.getBtn(3);
    private RobotDrive baseDriveInstance;

    public PixyalignDrive(RobotDrive baseInstance) {
        System.out.println("Init PixyalignDrive...");
        baseDriveInstance = baseInstance;
    }

    @Override
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
        SmartDashboard.putString("DB/String 0", "Pixyalign Mode");
    }

    @Override
    public void BehaviourPeriodic() {
        //Data returned is a string form of average X-Position of game pieces
        byte[] data = ARDUINO_INTERFACE.readI2C(1);
        byte direction = data[0];

        //The below calculations are partially derived from ManualDrive.java
        double fwdInput = PIXY_FWDAXIS;
        boolean creepEnabled = PIXY_CREEP;
        double finalFwdSpeed = fwdInput * (creepEnabled ? 0.6 : 1);

        //Apply the automated rotation of the pixycam, and the manual input for forward and back
        baseDriveInstance.DriveRobot(direction * 0.6, finalFwdSpeed);
    }
}
