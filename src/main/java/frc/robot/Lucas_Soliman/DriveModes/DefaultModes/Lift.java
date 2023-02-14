package frc.robot.Lucas_Soliman.DriveModes.DefaultModes;

import frc.robot.Lucas_Soliman.CTRE.TalonMotor;
import frc.robot.Lucas_Soliman.DriveModes.DriveMode;
import frc.robot.Lucas_Soliman.InputDevices.Input;

public class Lift implements DriveMode {
    private TalonMotor falconMotor;
    private Input inputDevice;

    public Lift(int falconCANID, int joystickPort) {
        falconMotor = new TalonMotor(falconCANID);
        inputDevice = new Input(joystickPort);
    }

    @Override
    public void DriveModeInit() {
        System.out.println("Init Lift...");
    }

    @Override
    public void DriveModePeriodic() {
    }
}
