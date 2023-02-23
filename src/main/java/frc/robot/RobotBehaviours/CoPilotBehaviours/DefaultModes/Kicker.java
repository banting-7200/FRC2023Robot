package frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes;

import static frc.robot.Core.Utility.*;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Interfaces.RobotBehaviour;

/*
 * Author: Lucas Soliman
 * Date-Created: February 17, 2023
 * 
 * This class is responsible for managing the states of the solenoids responsible for the kicker system
 */
public class Kicker implements RobotBehaviour{
    private final Solenoid KICKER_SOLENOID1 = new Solenoid(PneumaticsModuleType.CTREPCM, 2);
    private final Solenoid KICKER_SOLENOID2 = new Solenoid(PneumaticsModuleType.CTREPCM, 3);

    @Override
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
        System.out.println("Kicker Init...");
        KICKER_SOLENOID1.set(true);
        KICKER_SOLENOID2.set(false);
    }

    @Override
    public void BehaviourPeriodic() {
        boolean buttonState = CoPilotControls.JOYSTICK_COPILOT.getRawButton(CoPilotControls.KICKER_KICK);
        KICKER_SOLENOID1.set(buttonState);
        KICKER_SOLENOID2.set(!buttonState);
    }
}
