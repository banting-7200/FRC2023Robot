package frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes;

import static frc.robot.Core.Utility.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Core.CTRE.TalonMotor;
import frc.robot.Interfaces.RobotBehaviour;
import java.util.HashMap;

public class Shoulder implements RobotBehaviour {
    private final TalonMotor SHOULDER_MOTOR = new TalonMotor(1);

    //TODO: Get shoulder motor positions for each state
    private final double SHOULDER_START = -1584995.0;
    private final double SHOULDER_PICKUPPOS = -1285999.0;
    private final double SHOULDER_CARRY = -1413388.0;
    
    /*
     * Differences
     * PickuPos: 298996
     * CarryPos: 171607
     * Level1: 297100
     * Level2: 531896
     * Level3: 646900
     *  
     * New Positions (0 At Starting Position)
     * PickupPos: 
     * CarryPos: 
     * Level1: 
     * Level2: 
     * Level3: 
     * 
     * New Lift Positions)
     * PickupPos:
     * CarryPos:
     * Level1:
     * Level2:
     * Level3:
     */
    private final double SHOULDER_LEVEL1 = 271197;
    private final double SHOULDER_LEVEL2 = 518836;
    private final double SHOULDER_LEVEL3 = 634550;

    private final HashMap<Integer, Double> SHOULDER_POSITIONSMAP = new HashMap<>() {{
            put(CoPilotControls.MACRO_PICKUP, SHOULDER_PICKUPPOS);
            put(CoPilotControls.MACRO_CARRY, SHOULDER_CARRY);

            put(CoPilotControls.MACRO_LEVEL1, SHOULDER_LEVEL1);
            put(CoPilotControls.MACRO_LEVEL2, SHOULDER_LEVEL2);
            put(CoPilotControls.MACRO_LEVEL3, SHOULDER_LEVEL3);
    }};

    @Override
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
        SmartDashboard.putNumber("Target Shoulder Position", SHOULDER_MOTOR.getMotor().getSelectedSensorPosition());
        System.out.println("Shoulder Init...");
    }

    @Override
    public void BehaviourPeriodic() {
        if(CoPilotControls.JOYSTICK_COPILOT.getRawButtonPressed(10)) {
            SHOULDER_MOTOR.getMotor().setSelectedSensorPosition(0);
        }

        double motorPosition = SHOULDER_MOTOR.getMotor().getSelectedSensorPosition();
        double input = CoPilotControls.SHOULDER_MOVE.get();

        SmartDashboard.putNumber("ShoulderPosition", motorPosition);
        SHOULDER_MOTOR.getMotor().set(ControlMode.PercentOutput, input);

        if(input == 0) {
            double newPosition = SHOULDER_MOTOR.getMotor().getSelectedSensorPosition();
            SHOULDER_MOTOR.getMotor().set(ControlMode.Position, newPosition);
        }
    }

    public boolean moveShoulderToPosition(int positionBind, double moveSpeed) {
        double currPosition = SHOULDER_MOTOR.getMotor().getSelectedSensorPosition();
        double targetPosition = SHOULDER_POSITIONSMAP.get(positionBind);
        double output = Math.signum(targetPosition - currPosition) * moveSpeed;
        SHOULDER_MOTOR.getMotor().set(ControlMode.PercentOutput, output);

        double newMotorPosition = SHOULDER_MOTOR.getMotor().getSelectedSensorPosition();
        double leftError = targetPosition - 250;
        double rightError = targetPosition + 250;
        if(newMotorPosition >= leftError && newMotorPosition <= rightError) {
            return true;
        }

        return false;
    }
}
