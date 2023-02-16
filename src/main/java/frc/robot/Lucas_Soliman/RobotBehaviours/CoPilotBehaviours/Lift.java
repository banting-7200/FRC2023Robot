package frc.robot.Lucas_Soliman.RobotBehaviours.CoPilotBehaviours;

import static frc.robot.Utility.*;

import java.util.HashMap;
import java.util.HashSet;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Lucas_Soliman.CTRE.TalonMotor;
import frc.robot.Lucas_Soliman.InputDevices.Input;
import frc.robot.Lucas_Soliman.RobotBehaviours.RobotBehaviour;

/*
 * Author: Lucas Soliman
 * Date Created: February 15, 2023
 * 
 * This class is copilot operated
 * Uses inputs to change lift of robot
 */
public class Lift implements RobotBehaviour {
    private final TalonMotor LIFT_MOTOR = new TalonMotor(2);
    private final Input INPUT_DEVICE = new Input(PORT_COJOYSTICK);
    private final int CTRLS_LOWESTHEIGHTBIND = 3;
    private final int CTRLS_HIGHESTHEIGHTBIND = 5;
    private final HashMap<Integer, Double> heightMap = new HashMap<>() {
        {
            put(CTRLS_LOWESTHEIGHTBIND, 0.0);
            put(CTRLS_HIGHESTHEIGHTBIND, 1314526.0);
        }
    };

    @Override
    public void BehaviourInit(RobotBehaviour[] behaviours) {
        System.out.println("Init Lift...");
    }

    @Override
    public void BehaviourPeriodic() {
        double input = INPUT_DEVICE.stickY();
        input *= INPUT_DEVICE.getBtn(1) ? 0.6 : 1.0;

        if(INPUT_DEVICE.getBtn(CTRLS_LOWESTHEIGHTBIND)) {
            LIFT_MOTOR.getMotor().set(ControlMode.Position, heightMap.get(CTRLS_LOWESTHEIGHTBIND));
        }

        if(INPUT_DEVICE.getBtn(CTRLS_HIGHESTHEIGHTBIND)) {
            LIFT_MOTOR.getMotor().set(ControlMode.Position, heightMap.get(CTRLS_HIGHESTHEIGHTBIND));
        }

        SmartDashboard.putNumber("LiftPosition", LIFT_MOTOR.getMotor().getSelectedSensorPosition());
        LIFT_MOTOR.getMotor().set(ControlMode.PercentOutput, input);
    }
}
