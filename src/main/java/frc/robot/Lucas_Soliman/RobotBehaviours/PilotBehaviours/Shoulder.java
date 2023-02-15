package frc.robot.Lucas_Soliman.RobotBehaviours.PilotBehaviours;

import static frc.robot.Utility.*;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.Lucas_Soliman.CTRE.TalonMotor;
import frc.robot.Lucas_Soliman.InputDevices.Input;
import frc.robot.Lucas_Soliman.RobotBehaviours.RobotBehaviour;

public class Shoulder implements RobotBehaviour {
    private final TalonMotor SHOULDER_MOTOR = new TalonMotor(2);
    private final Input INPUT_DEVICE = new Input(PORT_JOYSTICK);
    private final boolean SHOULDER_CREEP = INPUT_DEVICE.getBtn(1);
    private final boolean SHOULDER_UP = INPUT_DEVICE.getBtn(6);
    private final boolean SHOULDER_DOWN = INPUT_DEVICE.getBtn(4);

    @Override
    public void BehaviourInit() {
        System.out.println("Init Shoulder...");
    }

    @Override
    public void BehaviourPeriodic() {
        double input = SHOULDER_UP ? 1.0 : SHOULDER_DOWN ? -1.0 : 0.0;
        input *= SHOULDER_CREEP ? 0.5 : 1.0;

        SHOULDER_MOTOR.getMotor().set(ControlMode.PercentOutput, input);
    }
}
