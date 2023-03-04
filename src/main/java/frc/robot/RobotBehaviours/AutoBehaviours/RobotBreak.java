package frc.robot.RobotBehaviours.AutoBehaviours;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class RobotBreak {
    private final Solenoid BREAK_SOLENOID = new Solenoid(PneumaticsModuleType.CTREPCM, 5);
    private long cyclesLeft;

    public RobotBreak(long cyclesBeforeToggle) {
        cyclesLeft = cyclesBeforeToggle;
        BREAK_SOLENOID.set(false);
    }

    public void robotBreakPeriodic() {
        cyclesLeft--;

        if(cyclesLeft <= 0) {
            BREAK_SOLENOID.set(true);
        }
    }
}
