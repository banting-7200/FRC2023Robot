package frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes;

import java.util.HashMap;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.CTRE.TalonMotor;
import frc.robot.Interfaces.RobotBehaviour;
import frc.robot.Utility.CoPilotControls;

/*
 * Author: Lucas Soliman
 * Date Created: February 15, 2023
 * 
 * This class is copilot operated
 * Uses inputs to change lift of robot
 */
public class Lift implements RobotBehaviour {
    private final TalonMotor LIFT_MOTOR = new TalonMotor(2);

    // private final DigitalInput LIFT_UPPERLIMITER = new DigitalInput(0);
    // private final DigitalInput LIFT_LOWERLIMITER = new DigitalInput(1);


    //TODO: Get motor positions for each state
    private final double LIFT_ABSOLUTELOWESTLIMIT = 490000; //This is rounded to two numeric values > 0 for safety
    private final double LIFT_ABSOLUTEHIGHESTLIMIT = -102867; //This is rounded to two numeric values > 0 for safety
    private final double LIFT_STARTINGPOSITION = 0;
    private final double LIFT_TOPLAYERPOSITION = 0;
    private final double LIFT_MIDLAYERPOSITION = 198265.5;

    private final HashMap<Integer, Double> LIFT_HEIGHTPOSITIONS = new HashMap<>() {{
        put(CoPilotControls.LIFT_STARTINGPOSITION, LIFT_ABSOLUTELOWESTLIMIT);
        put(CoPilotControls.LIFT_MIDLAYERPOSITION, LIFT_MIDLAYERPOSITION);
        put(CoPilotControls.LIFT_TOPLAYERPOSITION, LIFT_ABSOLUTEHIGHESTLIMIT);
    }};

    private double nextOverrideInput;

    @Override
    public void BehaviourInit(RobotBehaviour[] behaviours) {
        System.out.println("Init Lift...");
    }

    @Override
    public void BehaviourPeriodic() {
        // SmartDashboard.putBoolean("Lift_UpperLimiter Value: ", LIFT_UPPERLIMITER.get());
        // SmartDashboard.putBoolean("Lift_LowerLimiter Value: ", LIFT_LOWERLIMITER.get());

        /*
         * Gathering input information
         */
        double input = canMoveLift(CoPilotControls.LIFT_MOVE.get());
        if(nextOverrideInput != 0) {
            input = nextOverrideInput;
        }

        /*
         * Preset Lift Binds
         */
        if(CoPilotControls.JOYSTICK_COPILOT.getRawButton(CoPilotControls.LIFT_STARTINGPOSITION)) {
            LIFT_MOTOR.getMotor().set(ControlMode.Position, LIFT_HEIGHTPOSITIONS.get(CoPilotControls.LIFT_STARTINGPOSITION));
            return;
        }

        if(CoPilotControls.JOYSTICK_COPILOT.getRawButton(CoPilotControls.LIFT_TOPLAYERPOSITION)) {
            LIFT_MOTOR.getMotor().set(ControlMode.Position, LIFT_HEIGHTPOSITIONS.get(CoPilotControls.LIFT_TOPLAYERPOSITION));
            return;
        }

        SmartDashboard.putNumber("LiftPosition", LIFT_MOTOR.getMotor().getSelectedSensorPosition());
        LIFT_MOTOR.getMotor().set(ControlMode.PercentOutput, input);

        nextOverrideInput = 0;
        input = 0;
    }

    // Returns whether or not the desired input (i) can safely be applied
    public boolean setInput(double i) {
        nextOverrideInput = canMoveLift(i);
        return nextOverrideInput != 0;
    }

    public double canMoveLift(double output) {
        double motorPosition = LIFT_MOTOR.getMotor().getSelectedSensorPosition();
        if(motorPosition >= LIFT_ABSOLUTELOWESTLIMIT && nextOverrideInput > 0) {
            return 0;
        }

        if(motorPosition <= LIFT_ABSOLUTEHIGHESTLIMIT && nextOverrideInput < 0) {
            return 0;
        }

        return output;
    }

    public double getLiftPosition() {
        return LIFT_MOTOR.getMotor().getSelectedSensorPosition();
    }
}