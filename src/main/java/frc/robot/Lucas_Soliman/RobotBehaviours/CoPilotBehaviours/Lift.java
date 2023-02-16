package frc.robot.Lucas_Soliman.RobotBehaviours.CoPilotBehaviours;

import static frc.robot.Utility.*;

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
    private final TalonMotor LIFT_MOTOR = new TalonMotor(1);
    private final Input INPUT_DEVICE = new Input(PORT_COJOYSTICK);

    private final boolean LIFT_CREEP = INPUT_DEVICE.getBtn(1);
    private final double LIFT_AXIS = INPUT_DEVICE.stickY();

    @Override
    public void BehaviourInit() {
        System.out.println("Init Lift...");
    }

    @Override
    public void BehaviourPeriodic() {
        double input = LIFT_AXIS;
        input *= LIFT_CREEP ? 0.6 : 1.0;

        SmartDashboard.putString("DB/String 0", String.format("%.2f", LIFT_MOTOR.getMotor().getSelectedSensorPosition()));
        LIFT_MOTOR.getMotor().set(ControlMode.PercentOutput, input);
    }
}
