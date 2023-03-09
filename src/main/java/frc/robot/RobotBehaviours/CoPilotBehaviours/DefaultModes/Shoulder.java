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
    private final double SHOULDER_CARRY = 0;
    
    private final double SHOULDER_LEVEL1 = 147584;
    private final double SHOULDER_LEVEL2 = 375066;
    private final double SHOULDER_LEVEL3 = 454030;

    private final HashMap<Integer, Double> SHOULDER_POSITIONSMAP = new HashMap<>() {{
        put(CoPilotControls.MACRO_PICKUP, SHOULDER_PICKUPPOS);
        put(CoPilotControls.MACRO_CARRY, SHOULDER_CARRY);
        
        put(CoPilotControls.MACRO_LEVEL1, SHOULDER_LEVEL1);
        put(CoPilotControls.MACRO_LEVEL2, SHOULDER_LEVEL2);
        put(CoPilotControls.MACRO_LEVEL3, SHOULDER_LEVEL3);
        
        put(-1, 0.0);
    }};

    @Override
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
        if(Robot.inGameMode) {
            SHOULDER_MOTOR.getMotor().setSelectedSensorPosition(SHOULDER_STARTINGPOSITION);
        }

        System.out.println("Shoulder Init...");
    }

    @Override
    public void BehaviourPeriodic() {
        if(CoPilotAutoRunner.runningMacro) {
            return;
        }
        
        if(CoPilotControls.JOYSTICK_COPILOT.getPOV() == 0) {
            SHOULDER_MOTOR.getMotor().setSelectedSensorPosition(0);
        }

        double motorPosition = SHOULDER_MOTOR.getMotor().getSelectedSensorPosition();
        double input = CoPilotControls.SHOULDER_MOVE.get();
        input = Math.abs(input) > 0.4 ? input : 0;

        SmartDashboard.putNumber("ShoulderPosition", motorPosition);
        SHOULDER_MOTOR.getMotor().set(ControlMode.PercentOutput, input);
    }

    public void killSpeed() {
        SHOULDER_MOTOR.getMotor().set(ControlMode.PercentOutput, 0);
    }

    public boolean moveShoulderToPosition(int positionBind, double moveSpeed) {
        double currPosition = SHOULDER_MOTOR.getMotor().getSelectedSensorPosition();
        double targetPosition = SHOULDER_POSITIONSMAP.get(positionBind);
        double difference = targetPosition - currPosition;
        double percentDifference = Math.abs((difference) / currPosition);

        double output = Math.signum(targetPosition - currPosition) * moveSpeed;

        if(Math.abs(difference) <= 3000) {
            output = Math.signum(targetPosition - currPosition) * moveSpeed * Clamp(percentDifference, 0, 0.7);
        }

        SHOULDER_MOTOR.getMotor().set(ControlMode.PercentOutput, output);

        if(percentDifference <= TALONFXMOVETO_PERCENTERROR) {
            killSpeed();
            return true;
        }

        return false;
    }
}