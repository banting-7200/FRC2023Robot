package frc.robot.Lucas_Soliman.Arm;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.Lucas_Soliman.DriveModes.DriveMode;

public class Wrist implements DriveMode{
    private Counter wristMotorCounter;
    private Spark boschSeatMotor;
    
    public Wrist(int inputDeviceID, int mxpID) {
        wristMotorCounter = new Counter(new DigitalInput(mxpID));
    }

    @Override
    public void DriveModeInit() {
        System.out.println("Wrist is initialised");
    }

    @Override
    public void DriveModePeriodic() {

    }
}
