package frc.robot.Lucas_Soliman.RobotBehaviours.PilotBehaviours;

import static frc.robot.Utility.*;

import java.util.HashMap;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Lucas_Soliman.CTRE.TalonMotor;
import frc.robot.Lucas_Soliman.InputDevices.Input;
import frc.robot.Lucas_Soliman.RobotBehaviours.RobotBehaviour;
import frc.robot.Lucas_Soliman.RobotBehaviours.CoPilotBehaviours.Lift;

public class Shoulder implements RobotBehaviour {
    private final TalonMotor SHOULDER_MOTOR = new TalonMotor(1);
    private final Input INPUT_DEVICE = new Input(PORT_JOYSTICK);
    
    //TODO: Get shoulder motor positions for each state
    private final double SHOULDER_MOTOR_STARTPOS = 0;
    private final double SHOULDER_MOTOR_PICKUPPOS = 0;
    private final double SHOULDER_MOTOR_SCOREPOS = 0;

    private final int CTRLS_STARTPOSPOV = 90;
    private final int CTRLS_PICKUPPOSPOV = 180;
    private final int CTRLS_SCOREPOSPOV = 270;
    
    private final HashMap<Integer, Double> SHOULDER_POSITIONS = new HashMap<>() {{
        put(CTRLS_STARTPOSPOV, SHOULDER_MOTOR_STARTPOS);
        put(CTRLS_PICKUPPOSPOV, SHOULDER_MOTOR_PICKUPPOS);
        put(CTRLS_SCOREPOSPOV, SHOULDER_MOTOR_SCOREPOS);
    }};

    private final int SHOULDER_DOWN = 4;
    private final int SHOULDER_UP = 6;
    private Lift liftInstance;

    @Override
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
        System.out.println("Shoulder Init...");
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

        /*
        int currPOV = INPUT_DEVICE.pov();
        double targetPosition = SHOULDER_POSITIONS.getOrDefault(currPOV, -1.0);

        if(targetPosition != -1) {
            double delta = targetPosition - motorPosition;
            double output = Math.signum(delta);
            double percentDifference = Math.abs(delta) / ((targetPosition + motorPosition) / 2.0);

            output *= percentDifference;
            output *= 0.5;
            SHOULDER_MOTOR.set(ControlMode.PercentOutput, output);
            return;
        }
        */

        double motorPosition = SHOULDER_MOTOR.getMotor().getSelectedSensorPosition();
        double input = INPUT_DEVICE.getBtn(SHOULDER_UP) ? 1.0 : INPUT_DEVICE.getBtn(SHOULDER_DOWN) ? -1.0 : 0.0;
        input *= (INPUT_DEVICE.joystickInstance.getRawAxis(3) * -1 + 1) / 2.0;

        SmartDashboard.putNumber("ShoulderPosition", motorPosition);
        SHOULDER_MOTOR.getMotor().set(ControlMode.PercentOutput, input);
    }
}
