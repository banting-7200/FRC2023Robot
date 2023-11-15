package frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes;

import java.util.HashMap;
import com.ctre.phoenix.motorcontrol.ControlMode;

import static frc.robot.Core.Utility.*;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    //Previous Pickup, Level`, and Level 3: 311428
    private final double LIFT_PICKUP = 311428;
    private final double LIFT_CARRY = 155371;
    private final double LIFT_LEVEL1 = 311428;
    private final double LIFT_LEVEL2 = 311428;
    private final double LIFT_LEVEL3 = 287048;

    private final double kP = 0.25;
    private final double kI = 0.0; //TODO: Research influence of integral
    private final double kD = 0.0; //TODO: Research influence of derivative
    private HashMap<Integer, Double> LIFT_HEIGHTPOSITIONS;

    @Override
    public void BehaviourInit(RobotBehaviour[] behaviours) {
        LIFT_HEIGHTPOSITIONS = new HashMap<>() {{
            put(CoPilotControls.MACRO_PICKUP.get(), LIFT_PICKUP);
            put(CoPilotControls.MACRO_CARRY.get(), LIFT_CARRY);
            
            put(CoPilotControls.MACRO_LEVEL1.get(), LIFT_LEVEL1);
            put(CoPilotControls.MACRO_LEVEL2.get(), LIFT_LEVEL2);
            put(CoPilotControls.MACRO_LEVEL3.get(), LIFT_LEVEL3);
        }};

        System.out.println("Lift Init...");
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
        boolean down = PilotControls.JOYSTICK_PILOT1.getRawButton(PilotControls.LIFT_DOWN.get());
        boolean up = PilotControls.JOYSTICK_PILOT1.getRawButton(PilotControls.LIFT_UP.get());
        System.out.println(PilotControls.LIFT_UP.get());
        System.out.println(PilotControls.LIFT_DOWN.get());
        double input = down ? -0.8 : up ? 0.8 : 0;

        SmartDashboard.putNumber("LiftPosition", LIFT_MOTOR.getMotor().getSelectedSensorPosition());
        moveLift(LIFT_SPEED, Math.signum(input));
    }

    private void debugSwitches() {
        SmartDashboard.putBoolean("Lift UpperLimit State:", LIFT_UPPERLIMITER.get());
        SmartDashboard.putBoolean("Lift LowerLimit State", LIFT_LOWERLIMITER.get());
    }

    public boolean moveLift(double output, double direction) {
        double percentOutput = output * -direction;
        double finalOutput = canMoveLift(percentOutput);

        SmartDashboard.putNumber("Lift Output: ", finalOutput);
        LIFT_MOTOR.getMotor().set(ControlMode.PercentOutput, finalOutput);

        return finalOutput == 0 ? false : true;
    }

    public double canMoveLift(double output) {
        //Return inverse of direction if the output is attempting to go beyond limit.
        if((!LIFT_LOWERLIMITER.get()) && output > 0) {
            System.out.println("LOWER LIMIT REACHED... c44 STOPPING MOTOR");
            return 0;
        }

        if(!LIFT_UPPERLIMITER.get() && output < 0) {
            LIFT_MOTOR.getMotor().setSelectedSensorPosition(0);
            System.out.println("UPPER LIMIT REACHED... STOPPING MOTOR");
            return 0;
        }

        return output;
    }

    public void killSpeed() {
        LIFT_MOTOR.getMotor().set(ControlMode.PercentOutput, 0);
    }

    public boolean moveLiftToPosition(int positionBind, double movementSpeed) {        
        if(LIFT_HEIGHTPOSITIONS.get(positionBind) != null) {
            double targetPosition = LIFT_HEIGHTPOSITIONS.get(positionBind);
            double currentPosition = LIFT_MOTOR.getMotor().getSelectedSensorPosition();

            //This prevents the dividebyzero exception
            if(currentPosition == 0) {
                currentPosition = 1;
            }

            //Determine error and direction of motion relative to number signs, not motor output
            double error = targetPosition - currentPosition;
            double moveDir = movementSpeed * Math.signum(error);

            if(canMoveLift(moveDir) == moveDir) {
                //Calculate percentage difference and speed using the P section of PID control
                double percentDifference = Math.abs((targetPosition - currentPosition) / currentPosition);
                double speed = error * kP;

                //Check if percent difference between target and current position is negligible.
                if(percentDifference <= TALONFXMOVETO_PERCENTERROR) {
                    killSpeed();
                    return true;
                }

                //Apply speed with clamped speeds to allow for control over lift speed
                speed = Clamp(speed, -movementSpeed, movementSpeed);
                moveLift(Math.abs(speed), -Math.signum(error));
            }
        } else {
            return true;
        }

        return false;
    }

    public double getLiftPosition() {
        return LIFT_MOTOR.getMotor().getSelectedSensorPosition();
    }
}