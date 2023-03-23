package frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes;

import static frc.robot.Core.Utility.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.Core.CTRE.TalonMotor;
import frc.robot.Interfaces.RobotBehaviour;
import frc.robot.RobotBehaviours.CoPilotBehaviours.CoPilotAutoRunner;

import java.util.HashMap;

public class Shoulder implements RobotBehaviour {
    private final TalonMotor SHOULDER_MOTOR = new TalonMotor(1);
    private final double SHOULDER_STARTINGPOSITION = -175105;

    private final double SHOULDER_PICKUPPOS = 147584;
    private final double SHOULDER_MAXPOSITION = 500000; //TODO: Determine value via testing
    private final double SHOULDER_CARRY = 34516.000000;
    
    private final double SHOULDER_LEVEL1 = 147584;
    private final double SHOULDER_LEVEL2 = 375066;
    private final double SHOULDER_LEVEL3 = 454030;

    private HashMap<Integer, Double> SHOULDER_POSITIONSMAP;

    @Override
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
        if(Robot.inGameMode) {
            SHOULDER_MOTOR.getMotor().setSelectedSensorPosition(SHOULDER_STARTINGPOSITION);
        }

        SHOULDER_POSITIONSMAP = new HashMap<>() {{
            put(CoPilotControls.MACRO_PICKUP.get(), SHOULDER_PICKUPPOS);
            put(CoPilotControls.MACRO_CARRY.get(), SHOULDER_CARRY);
            
            put(CoPilotControls.MACRO_LEVEL1.get(), SHOULDER_LEVEL1);
            put(CoPilotControls.MACRO_LEVEL2.get(), SHOULDER_LEVEL2);
            put(CoPilotControls.MACRO_LEVEL3.get(), SHOULDER_LEVEL3);
            
            put(-1, 0.0);
        }};

        System.out.println("Shoulder Init...");
    }

    @Override
    public void BehaviourPeriodic() {
        if(CoPilotAutoRunner.runningMacro) {
            return;
        }
        
        if(CoPilotControls.SHOULDER_ZERO.get()) {
            SHOULDER_MOTOR.getMotor().setSelectedSensorPosition(0);
        }

        double motorPosition = SHOULDER_MOTOR.getMotor().getSelectedSensorPosition();
        double input = CoPilotControls.SHOULDER_MOVE.get();
        input = Math.abs(input) > 0.4 ? input : 0;
        input *= ARM_SPEED;

        SmartDashboard.putNumber("ShoulderPosition", motorPosition);
        SHOULDER_MOTOR.getMotor().set(ControlMode.PercentOutput, input);
    }

    public void killSpeed() {
        SHOULDER_MOTOR.getMotor().set(ControlMode.PercentOutput, 0);
    }

    public boolean moveShoulderToPosition(int positionBind, double moveSpeed) {
        double currPosition = SHOULDER_MOTOR.getMotor().getSelectedSensorPosition();
        double targetPosition = SHOULDER_POSITIONSMAP.get(positionBind);

        //double percentDifference = Math.abs((targetPosition - currPosition) / currPosition);
        double difference = (targetPosition - currPosition);


        if(difference > 0) { //Going up
            double output = difference / 30000.0;

            output = Clamp(output, -moveSpeed, moveSpeed);
            System.out.println("Shoulder Auto Speed: " + output);
            //7 degrees = ~30000 rotations
            if(difference <= 60000 && difference >= -60000) {
                output = 0;
                SHOULDER_MOTOR.getMotor().set(ControlMode.PercentOutput, 0);
                return true;
            }

            SHOULDER_MOTOR.getMotor().set(ControlMode.PercentOutput, output);
        } else if(difference < 0) { //Going down
            double output = difference / 30000.0;

            output = Clamp(output, -moveSpeed, moveSpeed);
            output *= Math.abs(difference) <= 80000 ? 0.5 : 1.0;
            output *= Math.abs(difference) <= 50000 ? 0.5 : 1.0;

            output = Math.abs(difference) <= 30000 ? 0.0 : output;
            SHOULDER_MOTOR.getMotor().set(ControlMode.PercentOutput, output);

            if(output == 0) {
                return true;
            }
        }

        return false;
    }
}