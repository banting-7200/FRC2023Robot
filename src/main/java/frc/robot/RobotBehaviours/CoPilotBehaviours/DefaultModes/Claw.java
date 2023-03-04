package frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes;

import static frc.robot.Core.Utility.*;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Interfaces.RobotBehaviour;

public class Claw implements RobotBehaviour{
    private final Solenoid CLAW_SOLENOID1 = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    private final Solenoid CLAW_SOLENOID2 = new Solenoid(PneumaticsModuleType.CTREPCM, 1);
    private boolean open = false;

    @Override
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
        CLAW_SOLENOID1.set(true);
        CLAW_SOLENOID2.set(false);
    }

    @Override
    public void BehaviourPeriodic() {
        if(CoPilotControls.JOYSTICK_COPILOT.getRawButtonPressed(CoPilotControls.CLAW_TOGGLE)) {
            open = !open;
        }  

        if(open) {
            CLAW_SOLENOID1.set(false);
            CLAW_SOLENOID2.set(true);
        } else {
            CLAW_SOLENOID1.set(true);
            CLAW_SOLENOID2.set(false);
        }
    }

    public void setClaw(boolean open) {
        if(open) {
            CLAW_SOLENOID1.set(false);
            CLAW_SOLENOID2.set(true);
        } else {
            CLAW_SOLENOID1.set(true);
            CLAW_SOLENOID2.set(false);
        }
    }
}
