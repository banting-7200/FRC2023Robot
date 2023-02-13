package frc.robot.Lucas_Soliman.DriveModes;

import java.util.function.IntPredicate;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.Joystick;

public class shoulder implements DriveMode{

    private Joystick input;
    private TalonFX motor;
    public shoulder(Joystick input) {
        this.input = input;
        motor = new TalonFX(1);
    }

    @Override
    public void DriveModeInit() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void DriveModePeriodic() {
        // TODO Auto-generated method stub
        double output = input.getX();
        output *= 0.5;

        motor.set(ControlMode.PercentOutput, output);
    }

}
