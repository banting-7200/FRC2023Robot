package frc.robot.RobotBehaviours.CoPilotBehaviours;

import static frc.robot.Utility.*;

import java.util.HashMap;
import java.util.function.Function;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.CTRE.TalonMotor;
import frc.robot.InputDevices.Input;
import frc.robot.Interfaces.RobotBehaviour;

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
    private final int CTRLS_LIFT_STARTINGPOSITION = 3;
    private final int CTRLS_LIFT_MIDLAYERPOSITION = 4;
    private final int CTRLS_LIFT_TOPLAYERPOSITION = 5;


    //TODO: Get motor positions for each state
    private final double LIFT_ABSOLUTELOWESTLIMIT = 499398; //This is rounded to two numeric values > 0 for safety
    private final double LIFT_ABSOLUTEHIGHESTLIMIT = -102867; //This is rounded to two numeric values > 0 for safety
    private final double LIFT_STARTINGPOSITION = 0;
    private final double LIFT_TOPLAYERPOSITION = 0;
    private final double LIFT_MIDLAYERPOSITION = 198265.5;

    private final HashMap<Integer, Double> LIFT_HEIGHTPOSITIONS = new HashMap<>() {{
        put(CTRLS_LIFT_STARTINGPOSITION, LIFT_ABSOLUTELOWESTLIMIT);
        put(CTRLS_LIFT_MIDLAYERPOSITION, LIFT_MIDLAYERPOSITION);
        put(CTRLS_LIFT_TOPLAYERPOSITION, LIFT_ABSOLUTEHIGHESTLIMIT);
    }};

    private double nextOverrideInput;

    @Override
    public void BehaviourInit(RobotBehaviour[] behaviours) {
        System.out.println("Init Lift...");
    }

    @Override
    public void BehaviourPeriodic() {
        double input = INPUT_DEVICE.stickY();

        if(nextOverrideInput != 0) {
            input = nextOverrideInput;
        }

        /* 
         * Some code to replace the code beneath
         * for(int bind = CTRLS_LIFT_STARTINGPOSITION; bind <= CTRLS_LIFT_TOPLAYERPOSITION; bind++) {
         *  if(INPUT_DEVICE.getBtn(bind)) {
         *   LIFT_MOTOR.getMotor().set(ControlMode.Position, LIFT_HEIGHTPOSITIONS.get(bind));
         *   break;
         *  }
         * }
         */

        if(INPUT_DEVICE.getBtn(CTRLS_LIFT_STARTINGPOSITION)) {
            LIFT_MOTOR.getMotor().set(ControlMode.Position, LIFT_HEIGHTPOSITIONS.get(CTRLS_LIFT_STARTINGPOSITION));
            return;
        }

        if(INPUT_DEVICE.getBtn(CTRLS_LIFT_TOPLAYERPOSITION)) {
            LIFT_MOTOR.getMotor().set(ControlMode.Position, LIFT_HEIGHTPOSITIONS.get(CTRLS_LIFT_TOPLAYERPOSITION));
            return;
        }

        double motorPosition = LIFT_MOTOR.getMotor().getSelectedSensorPosition();

        if(motorPosition >= LIFT_ABSOLUTELOWESTLIMIT && input > 0) {
            input = 0;
        }

        if(motorPosition <= LIFT_ABSOLUTEHIGHESTLIMIT && input < 0) {
            input = 0;
        }

        SmartDashboard.putNumber("LiftPosition", LIFT_MOTOR.getMotor().getSelectedSensorPosition());
        LIFT_MOTOR.getMotor().set(ControlMode.PercentOutput, input);

        nextOverrideInput = 0;
        input = 0;
    }

    public boolean setInput(double i) {
        nextOverrideInput = i;

        double motorPosition = LIFT_MOTOR.getMotor().getSelectedSensorPosition();
        if(motorPosition >= LIFT_ABSOLUTELOWESTLIMIT && nextOverrideInput > 0) {
            nextOverrideInput = 0;
            return false;
        }

        if(motorPosition <= LIFT_ABSOLUTEHIGHESTLIMIT && nextOverrideInput < 0) {
            nextOverrideInput = 0;
            return false;
        }

        return true;
    }

    public double getLiftPosition() {
        return LIFT_MOTOR.getMotor().getSelectedSensorPosition();
    }
}