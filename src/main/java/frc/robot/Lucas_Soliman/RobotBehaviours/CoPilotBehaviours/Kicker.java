package frc.robot.Lucas_Soliman.RobotBehaviours.CoPilotBehaviours;

import static frc.robot.Utility.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Lucas_Soliman.RobotBehaviours.RobotBehaviour;

public class Kicker implements RobotBehaviour{
    private final Joystick INPUT_DEVICE = new Joystick(PORT_COJOYSTICK);
    private final Solenoid kicker_2 = new Solenoid(PneumaticsModuleType.CTREPCM, 2);
    private final Solenoid kicker_3 = new Solenoid(PneumaticsModuleType.CTREPCM, 3);

    private final int CTRLS_SOLENOIDTOGGLE = 9;

    @Override
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
        
    }

    @Override
    public void BehaviourPeriodic() {
        boolean buttonState = INPUT_DEVICE.getRawButton(CTRLS_SOLENOIDTOGGLE);
        kicker_2.set(buttonState);
        kicker_3.set(!buttonState);
    }
}
