package frc.robot.Lucas_Soliman.DriveModes;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Lucas_Soliman.CTRE.TalonMotor;

public class Lift implements DriveMode {
    private Joystick controlDevice;
    private TalonMotor motor;
    public Lift(Joystick mainControl) {
        controlDevice = mainControl;
        motor = new TalonMotor(2);
    }  

    @Override
    public void DriveModeInit() {
    }

    @Override
    public void DriveModePeriodic() {
        double output = controlDevice.getY();
        output *= 0.6;

        motor.set(ControlMode.PercentOutput, output);
    }
}
