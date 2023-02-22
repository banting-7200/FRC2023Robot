package frc.robot.RobotBehaviours.CoPilotBehaviours;

import static frc.robot.Utility.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Interfaces.RobotBehaviour;

public class Claw implements RobotBehaviour{
    private final Joystick INPUT_DEVICE = new Joystick(PORT_COJOYSTICK);
    private final Solenoid CLAW_SOLENOID1 = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    private final Solenoid CLAW_SOLENOID2 = new Solenoid(PneumaticsModuleType.CTREPCM, 1);
    private final int CTRLS_CLAWTOGGLE = 1;
    private boolean open = false;

    @Override
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
    }

    @Override
    public void BehaviourPeriodic() {
        if(INPUT_DEVICE.getRawButtonPressed(CTRLS_CLAWTOGGLE)) {
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
}
