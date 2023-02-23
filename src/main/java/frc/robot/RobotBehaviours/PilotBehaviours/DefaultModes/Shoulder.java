package frc.robot.RobotBehaviours.PilotBehaviours.DefaultModes;

import static frc.robot.Core.Utility.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Core.CTRE.TalonMotor;
import frc.robot.Interfaces.RobotBehaviour;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Lift;

public class Shoulder implements RobotBehaviour {
    private final TalonMotor SHOULDER_MOTOR = new TalonMotor(1);

    //TODO: Get shoulder motor positions for each state
    private final double SHOULDER_MOTOR_PICKUPPOS = 0;
    private final double SHOULDER_MOTOR_SCOREPOS = 0;
    private Lift liftInstance;

    @Override
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
        SmartDashboard.putNumber("Target Shoulder Position", SHOULDER_MOTOR.getMotor().getSelectedSensorPosition());
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

        double motorPosition = SHOULDER_MOTOR.getMotor().getSelectedSensorPosition();
        double input = PilotControls.JOYSTICK_PILOT.getRawButton(CoPilotControls.SHOULDER_UP) ? 1.0 : CoPilotControls.JOYSTICK_COPILOT.getRawButton(CoPilotControls.SHOULDER_DOWN) ? -1.0 : 0.0;
        
        SmartDashboard.putNumber("ShoulderPosition", motorPosition);
        SHOULDER_MOTOR.getMotor().set(ControlMode.PercentOutput, input);

        if(input == 0) {
            double newPosition = SHOULDER_MOTOR.getMotor().getSelectedSensorPosition();
            SHOULDER_MOTOR.getMotor().set(ControlMode.Position, newPosition);
        }
    }
}
