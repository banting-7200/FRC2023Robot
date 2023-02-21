package frc.robot.RobotBehaviours.PilotBehaviours.DefaultModes;

import static frc.robot.Utility.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import frc.robot.RobotBehaviours.RobotBehaviour;

/*
 * Author: Lucas Soliman
 * Date Created: February 15, 2023
 * 
 * This class uses inputs to move the wrist on the robot
 */
public class Wrist implements RobotBehaviour {
    private final PWMSparkMax WRIST_MOTOR = new PWMSparkMax(MOTOR_WRISTMOTOR);
    private final Joystick inputDevice = new Joystick(PORT_JOYSTICK);
    
    private final boolean WRIST_CLOCKWISE = inputDevice.getRawButton(3);
    private final boolean WRIST_CTRCLOCKWISE = inputDevice.getRawButton(5);

    @Override
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
        System.out.println("Wrist Init...");
    }

    @Override
    public void BehaviourPeriodic() {
        double output = WRIST_CLOCKWISE ? 1.0 : WRIST_CTRCLOCKWISE ? -1.0 : 0;
        WRIST_MOTOR.set(output);
    }
}
