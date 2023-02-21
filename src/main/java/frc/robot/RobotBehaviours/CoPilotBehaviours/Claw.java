package frc.robot.RobotBehaviours.CoPilotBehaviours;

import static frc.robot.Utility.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.RobotBehaviours.RobotBehaviour;

public class Claw implements RobotBehaviour{
    private final Joystick INPUT_DEVICE = new Joystick(PORT_COJOYSTICK);
    private final Solenoid CLAW_SOLENOID1 = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    private final Solenoid CLAW_SOLENOID2 = new Solenoid(PneumaticsModuleType.CTREPCM, 1);
    private final int CTRLS_OPENCLAW = 11;
    private final int CTRLS_CLOSECLAW = 12;

    @Override
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
        CLAW_SOLENOID1.set(true);
        CLAW_SOLENOID2.set(false);
    }

    @Override
    public void BehaviourPeriodic() {
        boolean buttonState = INPUT_DEVICE.getRawButtonPressed(CTRLS_OPENCLAW);
        boolean buttonState2 = INPUT_DEVICE.getRawButtonPressed(CTRLS_CLOSECLAW);

        if(buttonState) {
            CLAW_SOLENOID1.set(true);
            CLAW_SOLENOID2.set(false);
            return;
        }

        if(buttonState2) {
            CLAW_SOLENOID1.set(false);
            CLAW_SOLENOID2.set(true);
        }
    }
}
