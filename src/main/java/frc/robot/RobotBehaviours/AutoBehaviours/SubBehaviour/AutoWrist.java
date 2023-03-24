package frc.robot.RobotBehaviours.AutoBehaviours.SubBehaviour;

import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Wrist;

public class AutoWrist implements RobotAutoBehaviour{
    private Wrist wristInstance;
    private final int RUNTICKS;
    private final double POWER;
    private int ticksLeft;

    public AutoWrist(Wrist wristInstance, double activationSeconds, double rotPower) {
        this.wristInstance = wristInstance;
        RUNTICKS = (int)((activationSeconds * 1000.0) / 20.0);
        POWER = rotPower;

        ticksLeft = RUNTICKS;
    }

    @Override
    public void behaviourInit() {
        ticksLeft = RUNTICKS;
    }

    @Override
    public void behaviourPeriodic() {
        if(ticksLeft > 0) {
            wristInstance.setWrist(POWER);
            ticksLeft--;
        }

        if(ticksLeft <= 0) {
            wristInstance.setWrist(0);
        }
    }

    @Override
    public boolean isFinished() {
        return ticksLeft <= 0;
    }
}
