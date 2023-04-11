package frc.robot.RobotBehaviours.AutoBehaviours.SubBehaviour;

import static frc.robot.Core.Utility.*;
import frc.robot.Interfaces.RobotAutoBehaviour;

/*
 * This is an autobehaviour that toggles a claw to a given state and delays for a specified amount of ticks
 */
public class AutoClaw implements RobotAutoBehaviour{
    private final boolean CLAW_STATE;
    private final int WAIT_TICKS;
    private int ticksLeft;

    public AutoClaw(boolean open, double waitSeconds) {
        CLAW_STATE = open;
        WAIT_TICKS = (int)((waitSeconds * 1000.0) / 20.0);
        ticksLeft = WAIT_TICKS;
    }

    @Override
    public void behaviourInit() {
        ticksLeft = WAIT_TICKS;
    }

    @Override
    public void behaviourPeriodic() {
        INSTANCE_CLAW.setClaw(CLAW_STATE);

        if(ticksLeft > 0) {
            ticksLeft--;
        }
    }

    @Override
    public boolean isFinished() { 
        return ticksLeft <= 0;
    }
}
