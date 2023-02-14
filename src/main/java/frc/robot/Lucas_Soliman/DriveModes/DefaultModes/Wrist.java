package frc.robot.Lucas_Soliman.DriveModes.DefaultModes;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import frc.robot.Lucas_Soliman.DriveModes.DriveMode;
import frc.robot.Lucas_Soliman.InputDevices.Input;

public class Wrist implements DriveMode {
    private PWMSparkMax wristMotorController;
    private Input inputDevice;
    
    public Wrist(int pwmPort, int joystickPort) {
        wristMotorController = new PWMSparkMax(pwmPort);
        inputDevice = new Input(joystickPort);
    }

    @Override
    public void DriveModeInit() {
        System.out.println("Wrist Init...");
    }

    @Override
    public void DriveModePeriodic() {
    }
}
