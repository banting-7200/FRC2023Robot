package frc.robot.Lucas_Soliman.RobotBehaviours.PilotBehaviours;

import static frc.robot.Utility.*;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Lucas_Soliman.CTRE.TalonMotor;
import frc.robot.Lucas_Soliman.InputDevices.Input;
import frc.robot.Lucas_Soliman.RobotBehaviours.RobotBehaviour;
import frc.robot.Lucas_Soliman.RobotBehaviours.CoPilotBehaviours.Lift;

public class Shoulder implements RobotBehaviour {
    private final TalonMotor SHOULDER_MOTOR = new TalonMotor(1);
    private final Input INPUT_DEVICE = new Input(PORT_JOYSTICK);
    private final int SHOULDER_CREEP = 1;
    private final int SHOULDER_UP = 6;
    private final int SHOULDER_DOWN = 4;
    private Lift liftInstance;

    @Override
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
        System.out.println("Init Shoulder...");
        for(RobotBehaviour b : defaultBehaviours) {
            if(b instanceof Lift) {
                liftInstance = (Lift)b;
            }
        }
    }

    @Override
    public void BehaviourPeriodic() {
        if(liftInstance == null) {
            System.out.println("No Lift reference stored, shoulder not running...");
            return;
        }

        double motorPosition = SHOULDER_MOTOR.getMotor().getSelectedSensorPosition();
        double input = INPUT_DEVICE.getBtn(SHOULDER_UP) ? 1.0 : INPUT_DEVICE.getBtn(SHOULDER_DOWN) ? -1.0 : 0.0;
        input *= INPUT_DEVICE.getBtn(SHOULDER_CREEP) ? 0.1 : 0.3;

        SmartDashboard.putNumber("ShoulderPosition", motorPosition);
        SHOULDER_MOTOR.getMotor().set(ControlMode.PercentOutput, input);
    }
}
