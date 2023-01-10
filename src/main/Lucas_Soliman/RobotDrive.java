package Lucas_Soliman;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/*
 * Author: Lucas Soliman
 * Date Created: January 9, 2023
 * 
 * The class responsible for driving/moving the robot using motor references.
 */
public class RobotDrive {
    private DifferentialDrive driveInstance;
    private float currentSpeed;
    
    public RobotDrive(int topLeft, int bottomLeft, int topRight, int bottomRight) {
        
    }

    public void TeleopTick() {

    }

    public void DriveRobot(double motorPower, double zRotation) {
        driveInstance.arcadeDrive(motorPower, zRotation);
    }
}