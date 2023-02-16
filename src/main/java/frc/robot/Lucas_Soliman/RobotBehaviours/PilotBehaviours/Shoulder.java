package frc.robot.Lucas_Soliman.RobotBehaviours.PilotBehaviours;

import static frc.robot.Utility.*;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Lucas_Soliman.CTRE.TalonMotor;
import frc.robot.Lucas_Soliman.InputDevices.Input;
import frc.robot.Lucas_Soliman.RobotBehaviours.RobotBehaviour;

public class Shoulder implements RobotBehaviour {
    private final TalonMotor SHOULDER_MOTOR = new TalonMotor(2);
    private final Input INPUT_DEVICE = new Input(PORT_JOYSTICK);
    private final int SHOULDER_CREEP = 1;
    private final int SHOULDER_UP = 6;
    private final int SHOULDER_DOWN = 4;

    private double lowerLimit;
    private double upperLimit;

    @Override
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
        System.out.println("Init Shoulder...");
    }

    @Override
    public void BehaviourPeriodic() {
        double motorPosition = SHOULDER_MOTOR.getMotor().getSelectedSensorPosition();
        double input = INPUT_DEVICE.getBtn(SHOULDER_UP) ? 1.0 : INPUT_DEVICE.getBtn(SHOULDER_DOWN) ? -1.0 : 0.0;
        input *= INPUT_DEVICE.getBtn(SHOULDER_CREEP) ? 0.5 : 1.0;

        SmartDashboard.putNumber("ShoulderPosition", motorPosition);
        SHOULDER_MOTOR.getMotor().set(ControlMode.PercentOutput, input);
    }

    public void setLimits(int lowerRawSensorPosition, int upperRawSensorPosition) {
        lowerLimit = lowerRawSensorPosition;
        upperLimit = upperRawSensorPosition;
    }
}
