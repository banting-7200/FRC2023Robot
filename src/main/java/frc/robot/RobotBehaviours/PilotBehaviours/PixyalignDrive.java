package frc.robot.RobotBehaviours.PilotBehaviours;

import static frc.robot.Core.Utility.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;

import frc.robot.Interfaces.RobotBehaviour;
import frc.robot.Core.RobotDrive;

/*
 * This mode of driving is semi-autonomous
 *  - Moving forward and back is teleoperated
 *  - Rotating left/right is automated
 * 
 * Meant to align with closest game piece to center (cone/cube)
 */
public class PixyalignDrive implements RobotBehaviour {
    private final DigitalInput shouldRotate = new DigitalInput(4);
    private final DigitalInput leftRight = new DigitalInput(5);
    private RobotDrive baseDriveInstance;

    public PixyalignDrive(RobotDrive baseInstance) {
        System.out.println("Pixyalign-Drive Init...");
        baseDriveInstance = baseInstance;
    }

    @Override
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
        SmartDashboard.putString(SmartDashboardIDs.DRIVEMODEID, "Pixyalign Drive");
    }

    @Override
    public void BehaviourPeriodic() {
        if(shouldRotate.get()) {
            double direction = leftRight.get() ? -1.0 : 1.0;
            double nY = PilotControls.PILOT_Y.get();

            boolean creepEnabled = PilotControls.JOYSTICK_PILOT.getRawButton(PilotControls.DRIVING_CREEPTOGGLE);
            double mappedInputY = MapValue(Math.abs(nY), 0, 1, 0, creepEnabled ? 0.25 : 0.5);

            if(Math.abs(nY) <= 0.3) {
                mappedInputY = -0.5;
            }

            double finalFwdSpeed = Math.signum(nY) * (0.5 + mappedInputY);
            baseDriveInstance.DriveRobot(direction * 0.6, finalFwdSpeed);
        } else {
            baseDriveInstance.DriveRobot(0, 0);
        }
    }
}
