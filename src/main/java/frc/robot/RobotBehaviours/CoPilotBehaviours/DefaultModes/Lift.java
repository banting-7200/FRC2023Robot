package frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes;

import java.util.HashMap;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.Core.CTRE.TalonMotor;
import frc.robot.Core.Utility.CoPilotControls;
import frc.robot.Interfaces.RobotBehaviour;
import frc.robot.RobotBehaviours.CoPilotBehaviours.CoPilotAutoRunner;

/*
 * Author: Lucas Soliman
 * Date Created: February 15, 2023
 * 
 * This class is copilot operated
 * Uses inputs to change lift of robot
 */
public class Lift implements RobotBehaviour {
    private final TalonMotor LIFT_MOTOR = new TalonMotor(2);
    private final DigitalInput LIFT_UPPERLIMITER = new DigitalInput(1);
    private final DigitalInput LIFT_LOWERLIMITER = new DigitalInput(2);
    
    /* New Positions (0 At Starting Position) (Lift)
    * PickupPos: 
    * CarryPos: 
    * Level1: 
    * Level2: 
    * Level3: 
    */

    private final double LIFT_PICKUP = 687273;
    private final double LIFT_CARRY = 356932;
    private final double LIFT_START = -974830;

    private final double LIFT_LEVEL1 = 687273;
    private final double LIFT_LEVEL2 = 250;
    private final double LIFT_LEVEL3 = 238249;

    private final HashMap<Integer, Double> LIFT_HEIGHTPOSITIONS = new HashMap<>() {{
        put(CoPilotControls.MACRO_PICKUP, LIFT_PICKUP);
        put(CoPilotControls.MACRO_CARRY, LIFT_CARRY);
        
        put(CoPilotControls.MACRO_LEVEL1, LIFT_LEVEL1);
        put(CoPilotControls.MACRO_LEVEL2, LIFT_LEVEL2);
        put(CoPilotControls.MACRO_LEVEL3, LIFT_LEVEL3);
    }};

    private double nextOverrideInput;

    @Override
    public void BehaviourInit(RobotBehaviour[] behaviours) {
        System.out.println("Init Lift...");

        if(Robot.inGameMode) {
            LIFT_MOTOR.getMotor().setSelectedSensorPosition(-974830);
        }
    }

    @Override
    public void BehaviourPeriodic() {
        if(CoPilotAutoRunner.runningMacro) {
            return;
        }

        debugSwitches();

        /*
         * Gathering input information
         */
        boolean down = CoPilotControls.JOYSTICK_COPILOT.getRawButton(CoPilotControls.LIFT_DOWN);
        boolean up = CoPilotControls.JOYSTICK_COPILOT.getRawButton(CoPilotControls.LIFT_UP);
        double input = down ? -0.8 : up ? 0.8 : 0;
        if(nextOverrideInput != 0) {
            input = nextOverrideInput;
        }

        SmartDashboard.putNumber("LiftPosition", LIFT_MOTOR.getMotor().getSelectedSensorPosition());
        moveLift(input, Math.signum(input));

        nextOverrideInput = 0;
        input = 0;
    }

    private void debugSwitches() {
        SmartDashboard.putBoolean("Lift UpperLimit State:", LIFT_UPPERLIMITER.get());
        SmartDashboard.putBoolean("Lift LowerLimit State", LIFT_LOWERLIMITER.get());
    }

    public void moveLift(double output, double direction) {
        double percentOutput = output * -direction;
        double finalOutput = canMoveLift(percentOutput);
        LIFT_MOTOR.getMotor().set(ControlMode.PercentOutput, finalOutput);
    }

    public double canMoveLift(double output) {

        //Return inverse of direction if the output is attempting to go beyond limit.
        if(!LIFT_LOWERLIMITER.get() && output < 0) {
            System.out.println("LOWER LIMIT HIT... DEBOUNCING");
            return -output;
        }

        if(!LIFT_UPPERLIMITER.get() && output > 0) {
            LIFT_MOTOR.getMotor().setSelectedSensorPosition(0);
            System.out.println("UPPER LIMIT HIT... DEBOUNCING");
            return -output;
        }

        return output;
    }

    public void killSpeed() {
        LIFT_MOTOR.getMotor().set(ControlMode.PercentOutput, 0);
    }

    public boolean moveLiftToPosition(int positionBind, double movementSpeed) {        
        if(LIFT_HEIGHTPOSITIONS.get(positionBind) != null) {
            double targetPosition = LIFT_HEIGHTPOSITIONS.get(positionBind);
            double direction = targetPosition - LIFT_MOTOR.getMotor().getSelectedSensorPosition();

            if(canMoveLift(movementSpeed * -Math.signum(direction)) != 0) {
                moveLift(movementSpeed, Math.signum(direction));
                double newMotorPosition = LIFT_MOTOR.getMotor().getSelectedSensorPosition();

                double leftError = targetPosition - 1000;
                double rightError = targetPosition + 1000;
                if(newMotorPosition >= leftError && newMotorPosition <= rightError) {
                    killSpeed();
                    return true;
                }
            }
        }

        return false;
    }

    public double getLiftPosition() {
        return LIFT_MOTOR.getMotor().getSelectedSensorPosition();
    }
}