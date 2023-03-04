package frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes;

import static frc.robot.Core.Utility.*;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Interfaces.RobotBehaviour;

/*
 * Author: Lucas Soliman
 * Date Created: February 15, 2023
 * 
 * This class uses inputs to move the wrist on the robot
 */
public class Wrist implements RobotBehaviour {
    private final PWMSparkMax WRIST_MOTOR = new PWMSparkMax(MOTOR_WRISTMOTOR);

    //TODO: Get Channel ID for this digitalInput
    private final DigitalInput WRIST_COUNTER = new DigitalInput(3);
    private int wristPosition = 0;

    @Override
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
        System.out.println("Wrist Init...");
    }

    @Override
    public void BehaviourPeriodic() {
        double output = CoPilotControls.WRIST_MOVE.get();
        setWrist((Math.abs(output) > 0.5 ? output : 0) * 0.4);

        SmartDashboard.putNumber("Wrist Motor Position: ", wristPosition);
    }

    private void setWrist(double output) {
        WRIST_MOTOR.set(output);
        if(WRIST_COUNTER.get()) {
            wristPosition += Math.signum(output);
        }
    }
}
